package com.kim.dibt.adapters;

import com.cloudinary.Cloudinary;
import com.kim.dibt.cloudinaryApi.CloudinaryApi;
import com.kim.dibt.core.utils.result.DataResult;
import com.kim.dibt.core.utils.result.ErrorDataResult;
import com.kim.dibt.core.utils.result.SuccessDataResult;
import com.kim.dibt.services.ServiceMessages;
import org.jboss.logging.Messages;
import org.springframework.web.multipart.MultipartFile;


import java.util.Map;

public class CloudinaryAdapterManager implements ICloudinaryAdapterService {
    private Cloudinary cloudinary;
    private CloudinaryApi cloudinaryApi;

    public CloudinaryAdapterManager(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
        this.cloudinaryApi = new CloudinaryApi();
    }

    @Override
    public DataResult<Map> uploadImage(MultipartFile image) {
        var result = cloudinaryApi.uploadImage(image, cloudinary);
        if (result != null || result.isEmpty())
            return SuccessDataResult.of(result, ServiceMessages.IMAGE_UPLOAD_SUCCESS);
        return ErrorDataResult.of(result, ServiceMessages.IMAGE_UPLOAD_ERROR);
    }


}

