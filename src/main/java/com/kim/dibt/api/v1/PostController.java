package com.kim.dibt.api.v1;

import com.kim.dibt.core.models.PageModel;
import com.kim.dibt.core.utils.result.DataResult;
import com.kim.dibt.services.post.PostService;
import com.kim.dibt.services.post.dtos.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/post")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @GetMapping()
    public ResponseEntity<DataResult<List<GetAllPostDto>>> getAll() {
        var result = postService.getAll();
        if (result.isSuccess()) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.badRequest().body(result);

    }

    @GetMapping("/page")
    public ResponseEntity<DataResult<PageModel<GetAllPostDto>>> getAll(@RequestParam int pageNumber, @RequestParam int pageSize) {
        var pageable = PageRequest.of(pageNumber, pageSize, Sort.by("createdAt").descending());
        var result = postService.getAll(pageable);
        if (result.isSuccess()) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.badRequest().body(result);

    }

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<DataResult<AddedPostDto>> add(@ModelAttribute @Valid AddPostDto addPostDto) {
        var result = postService.add(addPostDto);
        if (result.isSuccess()) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.badRequest().body(result);
    }

    @DeleteMapping
    public ResponseEntity<DataResult<DeletedPostDto>> delete(@RequestParam Long id) {
        var result = postService.delete(id);
        if (result.isSuccess()) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.badRequest().body(result);
    }

    @PutMapping
    public ResponseEntity<DataResult<UpdatedPostDto>> update(@RequestBody @Valid UpdatePostDto addPostDto, @RequestParam Long id) {
        var result = postService.update(addPostDto, id);
        if (result.isSuccess()) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.badRequest().body(result);
    }

    @GetMapping("/get-user-posts")
    public ResponseEntity<DataResult<List<GetAllPostDto>>> getUserPosts(@RequestParam Long userId) {
        var result = postService.getUserPosts(userId);
        if (result.isSuccess()) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.badRequest().body(result);
    }

}
