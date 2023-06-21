package com.kim.dibt.api.v1;


import com.kim.dibt.core.utils.result.DataResult;
import com.kim.dibt.services.comment.CommentService;
import com.kim.dibt.services.comment.dtos.AddCommentDto;
import com.kim.dibt.services.comment.dtos.AddedCommentDto;
import com.kim.dibt.services.comment.dtos.DeletedCommentDto;
import com.kim.dibt.services.comment.dtos.GetAllCommentDto;;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/comment")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @GetMapping()
    public ResponseEntity<DataResult<List<GetAllCommentDto>>> getAllComment(@RequestParam Long postId) {
        var result = commentService.getAllComment(postId);
        if (result.isSuccess()) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.badRequest().body(result);
    }

    @PostMapping()
    public ResponseEntity<DataResult<AddedCommentDto>> addComment(@RequestBody AddCommentDto addCommentDto) {
        DataResult<AddedCommentDto> result = this.commentService.addComment(addCommentDto);
        if (result.isSuccess()) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.badRequest().body(result);
    }

    @DeleteMapping()
    public ResponseEntity<DataResult<DeletedCommentDto>> delete(@RequestParam Long id) {
        var result = commentService.delete(id);
        if (result.isSuccess()) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.badRequest().body(result);
    }


}
