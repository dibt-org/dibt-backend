package com.kim.dibt.security.auth;


import com.kim.dibt.core.utils.business.CustomModelMapper;
import com.kim.dibt.core.utils.constants.CoreConstants;
import com.kim.dibt.core.utils.result.DataResult;
import com.kim.dibt.core.utils.result.SuccessDataResult;
import com.kim.dibt.security.dto.RoleDto;
import com.kim.dibt.security.repo.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository repository;
    private final CustomModelMapper modelMapper;

    public DataResult<List<RoleDto>> getAll() {
        List<RoleDto> roleList = modelMapper.mapList(repository.findAll(), RoleDto.class);
        return SuccessDataResult.of(roleList, CoreConstants.LISTED);
    }

}
