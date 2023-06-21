package com.kim.dibt.services.media;

import com.kim.dibt.core.utils.result.DataResult;
import com.kim.dibt.services.media.dtos.AddedMediaDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MediaService {
    DataResult<List<AddedMediaDto>> addMedia(List<MultipartFile> media);
}

