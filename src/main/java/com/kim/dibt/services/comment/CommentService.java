package com.kim.dibt.services.comment;

import com.kim.dibt.core.utils.result.DataResult;
import com.kim.dibt.core.utils.result.SuccessDataResult;
import com.kim.dibt.services.comment.dtos.AddCommentDto;
import com.kim.dibt.services.comment.dtos.AddedCommentDto;
import com.kim.dibt.services.comment.dtos.DeletedCommentDto;
import com.kim.dibt.services.comment.dtos.GetAllCommentDto;
import com.kim.dibt.services.post.dtos.DeletedPostDto;

import java.util.List;

public interface CommentService {

    DataResult<List<GetAllCommentDto>> getAllComment(Long postId);

    DataResult<AddedCommentDto> addComment(AddCommentDto addCommentDto);

    DataResult<DeletedCommentDto> delete(Long id);
}
