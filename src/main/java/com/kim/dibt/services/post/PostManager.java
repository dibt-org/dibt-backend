package com.kim.dibt.services.post;

import com.kim.dibt.core.models.PageModel;
import com.kim.dibt.core.utils.business.BusinessRule;
import com.kim.dibt.core.utils.business.CustomModelMapper;
import com.kim.dibt.core.utils.result.*;
import com.kim.dibt.models.Post;
import com.kim.dibt.repo.PostRepository;
import com.kim.dibt.security.models.User;
import com.kim.dibt.services.ServiceMessages;
import com.kim.dibt.services.post.convertor.GetAllPostDtoPageConvertor;
import com.kim.dibt.services.post.dtos.*;
import com.kim.dibt.services.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostManager implements PostService {
    private final PostRepository postRepository;
    private final UserService userService;
    private final CustomModelMapper modelMapper;
    private final GetAllPostDtoPageConvertor getAllPostDtoPageConvertor;

    @Override
    public DataResult<List<GetAllPostDto>> getAll() {
        List<Post> all = postRepository.findAll();
        List<GetAllPostDto> getAllPostDtos = new ArrayList<>();
        all.forEach(post -> {
            GetAllPostDto getAllPostDto = modelMapper.ofStandard().map(post, GetAllPostDto.class);
            getAllPostDto.setUsername(post.getUser().getUsername());
            getAllPostDtos.add(getAllPostDto);
        });
        return SuccessDataResult.of(getAllPostDtos, ServiceMessages.POST_LISTED);


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
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        DataResult<User> byUsername = userService.findByUsername(username);
        if (!byUsername.isSuccess()) {
            return ErrorDataResult.of(null, byUsername.getMessage());
        }
        post.setUser(byUsername.getData());
        Post savedPos = postRepository.save(post);
        AddedPostDto addedPostDto = modelMapper.ofStandard().map(savedPos, AddedPostDto.class);
        return SuccessDataResult.of(addedPostDto, ServiceMessages.POST_ADDED);

    }

    @Override
    public DataResult<DeletedPostDto> delete(Long id) {
        Result run = BusinessRule.run(isPostExist(id), isUserHasPost(id));
        if (run != null) {
            return ErrorDataResult.of(null, run.getMessage());
        }

        Post post = postRepository.findById(id).orElse(null);
        postRepository.deleteById(id);
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
