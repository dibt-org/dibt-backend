package com.kim.dibt.services.mention;

import com.kim.dibt.core.utils.result.DataResult;
import com.kim.dibt.models.Post;
import com.kim.dibt.services.mention.dtos.AddMentionDto;

import java.util.List;

public interface MentionService {
    DataResult<List<AddMentionDto>> addAll(List<String> mentions, Post post);

}
