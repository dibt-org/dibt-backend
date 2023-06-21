package com.kim.dibt.services.media;

import com.kim.dibt.adapters.MediaUploadAdapterService;
import com.kim.dibt.core.utils.result.DataResult;
import com.kim.dibt.repo.MediaRepository;
import com.kim.dibt.services.media.dtos.AddedMediaDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;


@Service
@RequiredArgsConstructor
public class MediaManager implements MediaService {
    private final MediaUploadAdapterService mediaUploadAdapterService;
    private final MediaRepository mediaRepository;

    @Override
    public DataResult<List<AddedMediaDto>> addMedia(List<MultipartFile> media) {
        return null;
    }


    private boolean checkFile(MultipartFile file) {
        return file != null && !file.isEmpty();
    }

}
