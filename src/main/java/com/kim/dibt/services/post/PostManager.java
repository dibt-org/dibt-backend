package com.kim.dibt.services.post;

import com.kim.dibt.adapters.MediaUploadAdapterService;
import com.kim.dibt.core.models.PageModel;
import com.kim.dibt.core.utils.business.BusinessRule;
import com.kim.dibt.core.utils.business.CustomModelMapper;
import com.kim.dibt.core.utils.result.*;
import com.kim.dibt.models.Media;
import com.kim.dibt.models.Post;
import com.kim.dibt.repo.MediaRepository;
import com.kim.dibt.repo.PostRepository;
import com.kim.dibt.security.models.User;
import com.kim.dibt.services.ServiceMessages;
import com.kim.dibt.services.mention.MentionService;
import com.kim.dibt.services.mention.dtos.AddMentionDto;
import com.kim.dibt.services.post.convertor.GetAllPostDtoPageConvertor;
import com.kim.dibt.services.post.dtos.*;
import com.kim.dibt.services.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostManager implements PostService {
    private final PostRepository postRepository;
    private final UserService userService;
    private final CustomModelMapper modelMapper;
    private final GetAllPostDtoPageConvertor getAllPostDtoPageConvertor;
    private final MentionService mentionService;
    private final MediaUploadAdapterService mediaUploadAdapterService;
    private final MediaRepository mediaRepository;

    @Override
    public DataResult<List<GetAllPostDto>> getAll() {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdDate");
        List<Post> all = postRepository.findAll(sort);
        List<GetAllPostDto> getAllPostDtos = new ArrayList<>();
        all.forEach(post -> {
            GetAllPostDto getAllPostDto = modelMapper.ofStandard().map(post, GetAllPostDto.class);
            getAllPostDto.setUsername(post.getUser().getUsername());
            getAllPostDto.setCreatedAtMessage(getCreatedAtMessage(post.getCreatedDate()));
            getAllPostDto.setMediaUrls(post.getMedias().stream().map(Media::getUrl).toList());
            getAllPostDto.setMentions(post.getMentions().stream().map(mention -> mention.getUser().getUsername()).toList());
            getAllPostDtos.add(getAllPostDto);
        });
        return SuccessDataResult.of(getAllPostDtos, ServiceMessages.POST_LISTED);


    }

    private static String getCreatedAtMessage(Date createdAt) {
        LocalDateTime createdAtDateTime = LocalDateTime.ofInstant(createdAt.toInstant(), java.time.ZoneId.systemDefault());
        LocalDateTime now = LocalDateTime.now(); // Şu anki zaman

        Duration duration = Duration.between(createdAtDateTime, now); // İki tarih arasındaki farkı hesapla

        if (duration.toMinutes() < 60) {
            long minutes = duration.toMinutes();
            return minutes + " dakika önce";
        } else if (duration.toHours() < 24) {
            long hours = duration.toHours();
            return hours + " saat önce";
        } else {
            long days = duration.toDays();
            return days + " gün önce";
        }
    }

    @Override
    public DataResult<PageModel<GetAllPostDto>> getAll(Pageable pageable) {
        var page = postRepository.findAll(pageable);
        PageModel<GetAllPostDto> apply = getAllPostDtoPageConvertor.apply(page);
        return SuccessDataResult.of(apply, ServiceMessages.POST_LISTED);
    }

    @Override
    public DataResult<AddedPostDto> add(AddPostDto addPostDto) {

        var post = modelMapper.ofStandard().map(addPostDto, Post.class);
        post.setMentions(null);
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        DataResult<User> byUsername = userService.findByUsername(username);
        if (!byUsername.isSuccess()) {
            return ErrorDataResult.of(null, byUsername.getMessage());
        }
        if (Boolean.FALSE.equals(byUsername.getData().getIsVerified()))
            return ErrorDataResult.of(null, ServiceMessages.USER_NOT_VERIFIED);
        post.setUser(byUsername.getData());
        Post savedPost = postRepository.save(post);
        DataResult<List<AddMentionDto>> addMentionsResult = mentionService.addAll(addPostDto.getMentions(), savedPost);
        if (!addMentionsResult.isSuccess()) {
            return ErrorDataResult.of(null, addMentionsResult.getMessage());
        }
        List<String> mediaUrls = new ArrayList<>();
        if (addPostDto.getImages() != null) {
            addPostDto.getImages().forEach(image -> {
                if (isFileValid(image).isSuccess()) {
                    DataResult<Map<?, ?>> uploadImage = mediaUploadAdapterService.uploadImage(image);
                    if (uploadImage.isSuccess()) {
                        Object url = uploadImage.getData().get("url");
                        if (url != null) {
                            Media media = new Media();
                            media.setPost(savedPost);
                            media.setUrl(url.toString());
                            media.setType(image.getContentType());
                            Media save = mediaRepository.save(media);
                            mediaUrls.add(save.getUrl());
                        }
                    }
                }
            });
        }

        AddedPostDto addedPostDto = modelMapper.ofStandard().map(savedPost, AddedPostDto.class);
        addedPostDto.setMediaUrls(mediaUrls);
        List<String> mentions = new ArrayList<>();
        addMentionsResult.getData().forEach(mention -> mentions.add(mention.getUsername()));
        addedPostDto.setMentions(mentions);
        return SuccessDataResult.of(addedPostDto, ServiceMessages.POST_ADDED);
    }

    // check if file is not null and type is image or video
    private Result isFileValid(MultipartFile file) {
        if (file == null) {
            return new ErrorResult(ServiceMessages.FILE_IS_NULL);
        }
        String contentType = file.getContentType();
        if (contentType == null) {
            return new ErrorResult(ServiceMessages.FILE_IS_NULL);
        }
        if (!contentType.startsWith("image") && !contentType.startsWith("video")) {
            return new ErrorResult(ServiceMessages.FILE_TYPE_NOT_SUPPORTED);
        }
        return SuccessResult.of();
    }


    @Override
    public DataResult<DeletedPostDto> delete(Long id) {
        Result run = BusinessRule.run(isPostExist(id), isUserHasPost(id));
        if (run != null) {
            return ErrorDataResult.of(null, run.getMessage());
        }

        Post post = postRepository.findById(id).orElse(null);
        postRepository.deleteById(id);
        if (post != null && post.getMentions() != null)
            post.getMentions().forEach(mention -> mentionService.deleteById(mention.getId()));
        var deletedPostDto = modelMapper.ofStandard().map(post, DeletedPostDto.class);
        return SuccessDataResult.of(deletedPostDto, ServiceMessages.POST_DELETED);

    }

    @Override
    public DataResult<UpdatedPostDto> update(UpdatePostDto updatePostDto, Long id) {
        Result run = BusinessRule.run(isPostExist(id), isUserHasPost(id));
        if (run != null) {
            return ErrorDataResult.of(null, run.getMessage());
        }

        Post post = postRepository.findById(id).orElse(null);
        if (post == null) {
            return ErrorDataResult.of(null, ServiceMessages.POST_NOT_FOUND);
        }
        post.setTitle(updatePostDto.getTitle());
        post.setContent(updatePostDto.getContent());
        Post savedPost = postRepository.save(post);
        var updatedPostDto = modelMapper.ofStandard().map(savedPost, UpdatedPostDto.class);
        return SuccessDataResult.of(updatedPostDto, ServiceMessages.POST_UPDATED);
    }

    @Override
    public DataResult<Post> findById(Long id) {
        Optional<Post> byId = this.postRepository.findById(id);
        if (byId.isEmpty()) {
            return ErrorDataResult.of(null, ServiceMessages.POST_NOT_FOUND);
        }
        return SuccessDataResult.of(byId.get(), ServiceMessages.POST_FOUND);
    }

    @Override
    public Result isExist(Long id) {
        if (this.postRepository.existsById(id)) {
            return SuccessResult.of(ServiceMessages.POST_EXIST);
        }
        return ErrorResult.of(ServiceMessages.POST_NOT_FOUND);
    }

    @Override
    public DataResult<List<GetAllPostDto>> getUserPosts(Long userId) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdDate");
        List<Post> posts = postRepository.findAllByUserId(userId, sort);
        List<GetAllPostDto> getAllPostDtos = new ArrayList<>();
        posts.forEach(post -> {
            GetAllPostDto getAllPostDto = modelMapper.ofStandard().map(post, GetAllPostDto.class);
            getAllPostDto.setUsername(post.getUser().getUsername());
            getAllPostDto.setCreatedAtMessage(getCreatedAtMessage(post.getCreatedDate()));
            getAllPostDto.setMediaUrls(post.getMedias().stream().map(Media::getUrl).toList());
            getAllPostDto.setMentions(post.getMentions().stream().map(mention -> mention.getUser().getUsername()).toList());
            getAllPostDtos.add(getAllPostDto);
        });
        return SuccessDataResult.of(getAllPostDtos, ServiceMessages.POST_LISTED);
    }

    private Result isPostExist(Long id) {
        if (postRepository.findById(id).isPresent()) {
            return SuccessDataResult.of(null);
        }
        return ErrorDataResult.of(null, ServiceMessages.POST_NOT_FOUND);
    }

    private Result isUserHasPost(Long id) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Post post = postRepository.findById(id).orElse(null);
        if (post == null) {
            return ErrorDataResult.of(null, ServiceMessages.POST_NOT_FOUND);
        }
        if (!post.getUser().getUsername().equals(username)) {
            return ErrorDataResult.of(null, ServiceMessages.POST_NOT_FOUND);
        }
        return SuccessDataResult.of(null);
    }
}
