package com.kim.dibt.services.post;

import com.kim.dibt.core.models.PageModel;
import com.kim.dibt.core.utils.result.DataResult;
import com.kim.dibt.core.utils.result.Result;
import com.kim.dibt.models.Post;
import com.kim.dibt.services.post.dtos.*;
import org.springframework.data.domain.Pageable;


import java.util.List;

public interface PostService {
    DataResult<List<GetAllPostDto>> getAll();
    DataResult<PageModel<GetAllPostDto>> getAll(Pageable pageable);

    DataResult<AddedPostDto> add(AddPostDto addPostDto);

    DataResult<DeletedPostDto> delete(Long id);

    DataResult<UpdatedPostDto> update(UpdatePostDto updatePostDto, Long id);

    DataResult<Post> findById(Long id);

    Result isExist(Long id);

    DataResult<List<GetAllPostDto>> getUserPosts(Long userId);
}
