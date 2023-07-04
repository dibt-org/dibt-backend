package com.kim.dibt.services.comment;

import com.kim.dibt.core.utils.business.BusinessRule;
import com.kim.dibt.core.utils.business.CustomModelMapper;
import com.kim.dibt.core.utils.result.*;
import com.kim.dibt.models.Comment;
import com.kim.dibt.models.Post;
import com.kim.dibt.repo.CommentRepository;
import com.kim.dibt.security.models.User;
import com.kim.dibt.services.ServiceMessages;
import com.kim.dibt.services.comment.dtos.AddCommentDto;
import com.kim.dibt.services.comment.dtos.AddedCommentDto;
import com.kim.dibt.services.comment.dtos.DeletedCommentDto;
import com.kim.dibt.services.comment.dtos.GetAllCommentDto;
import com.kim.dibt.services.post.PostService;
import com.kim.dibt.services.post.dtos.DeletedPostDto;
import com.kim.dibt.services.user.UserService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Getter
@Setter
public class CommentManager implements CommentService {
    private final CommentRepository commentRepository;
    private final CustomModelMapper modelMapper;
    private final PostService postService;
    private final UserService userService;

    @Override
    public DataResult<List<GetAllCommentDto>> getAllComment(Long postId) {
        //TODO: Post var mı kontrolü yapılacak
        List<Comment> all = commentRepository.findAllByPostId(postId);
        List<GetAllCommentDto> getAllCommentDtos = new ArrayList<>();

        all.forEach(comment -> {
            GetAllCommentDto getAllCommentDto = modelMapper.ofStandard().map(comment, GetAllCommentDto.class);
            getAllCommentDto.setUsername(comment.getUser().getUsername());
            getAllCommentDto.setCreatedDate(comment.getCreatedDate().toString());
            getAllCommentDto.setUserId(comment.getUser().getId());
            getAllCommentDtos.add(getAllCommentDto);
        });

        return SuccessDataResult.of(getAllCommentDtos, ServiceMessages.COMMENT_LISTED);


    }

    @Override
    public DataResult<AddedCommentDto> addComment(AddCommentDto addCommentDto) {
        Comment comment = modelMapper.ofStandard().map(addCommentDto, Comment.class);
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Result run = BusinessRule.run(
                ifPostExists(addCommentDto.getPostId()),
                ifUserExists(username)
        );
        if (run != null) {
            return ErrorDataResult.of(null, run.getMessage());
        }
        Post post = this.postService.findById(addCommentDto.getPostId()).getData();

        User user = this.userService.findByUsername(username).getData();

        comment.setPost(post);
        comment.setUser(user);

        Comment save = this.commentRepository.save(comment);
        AddedCommentDto addedCommentDto = modelMapper.ofStandard().map(save, AddedCommentDto.class);
        addedCommentDto.setUsername(username);
        addedCommentDto.setCreatedDate(save.getCreatedDate() != null ? save.getCreatedDate().toString() : "");
        return SuccessDataResult.of(addedCommentDto, ServiceMessages.COMMENT_ADDED);
    }

    @Override
    public DataResult<DeletedCommentDto> delete(Long id) {
        Result run = BusinessRule.run(isUserHasComment(id), isCommentExist(id));
        if (run != null) {
            return ErrorDataResult.of(null, run.getMessage());
        }
        Comment comment = commentRepository.findById(id).orElse(null);
        commentRepository.deleteById(id);
        var deletedCommentDto = modelMapper.ofStandard().map(comment, DeletedCommentDto.class);
        return SuccessDataResult.of(deletedCommentDto, ServiceMessages.COMMENT_DELETED);
    }

    private Result ifUserExists(String username) {
        DataResult<User> byUsername = this.userService.findByUsername(username);
        if (!byUsername.isSuccess()) {
            return ErrorResult.of(byUsername.getMessage());
        }
        return SuccessResult.of();
    }

    private Result ifPostExists(Long postId) {
        return this.postService.isExist(postId);
    }

    private Result isUserHasComment(Long id) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Comment comment = this.commentRepository.findById(id).orElse(null);
        if (comment == null) {
            return ErrorDataResult.of(null, ServiceMessages.COMMENT_NOT_FOUND);
        }
        if (!comment.getUser().getUsername().equals(username)) {
            return ErrorDataResult.of(null, ServiceMessages.USER_HAS_NO_COMMENT);
        }
        return SuccessDataResult.of(null);
    }

    private Result isCommentExist(Long id) {
        if (commentRepository.findById(id).isPresent()) {
            return SuccessDataResult.of(null);
        }
        return ErrorDataResult.of(null, ServiceMessages.COMMENT_NOT_FOUND);
    }

}
