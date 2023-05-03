package com.kim.dibt.adapters;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.kim.dibt.core.utils.result.DataResult;
import com.kim.dibt.core.utils.result.ErrorDataResult;
import com.kim.dibt.core.utils.result.SuccessDataResult;
import com.kim.dibt.services.ServiceMessages;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.Collections;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CloudinaryAdapterManager implements MediaUploadAdapterService {
    private final Cloudinary cloudinary;

    @Override
    public DataResult<Map<?, ?>> uploadImage(MultipartFile image) {
        Map<?, ?> upload = upload(image);
        if (upload.isEmpty()) {
            return new ErrorDataResult<>(ServiceMessages.UPLOAD_FAILED);
        }
        return new SuccessDataResult<>(upload, ServiceMessages.UPLOAD_SUCCESS);
    }


    private Map<?, ?> upload(MultipartFile file) {
        try {
            return cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.emptyMap();
    }


}

