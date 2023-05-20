package com.kim.dibt.services.post;

import com.kim.dibt.core.utils.result.DataResult;
import com.kim.dibt.services.post.dtos.*;


import java.util.List;

public interface PostService {
    DataResult<List<GetAllPostDto>> getAll();

    DataResult<AddedPostDto> add(AddPostDto addPostDto);

    DataResult<DeletedPostDto> delete(Long id);

    DataResult<UpdatedPostDto> update(UpdatePostDto updatePostDto, Long id);
}
