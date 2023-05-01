package com.kim.dibt.adapters;

import com.kim.dibt.core.utils.result.DataResult;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;


public interface MediaUploadAdapterService {

    DataResult<Map<?, ?>> uploadImage(MultipartFile image);

}
