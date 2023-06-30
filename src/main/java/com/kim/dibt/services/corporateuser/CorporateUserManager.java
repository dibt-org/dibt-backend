package com.kim.dibt.services.corporateuser;

import com.kim.dibt.core.utils.business.CustomModelMapper;
import com.kim.dibt.core.utils.result.DataResult;
import com.kim.dibt.core.utils.result.SuccessDataResult;
import com.kim.dibt.models.CorprateUser;
import com.kim.dibt.repo.CorporateUserRepository;
import com.kim.dibt.services.corporateuser.dtos.AddCorporateUserDto;
import com.kim.dibt.services.corporateuser.dtos.AddedCorporateUserDto;
import kotlin.jvm.internal.SerializedIr;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CorporateUserManager implements CorporateUserService {
    private final CorporateUserRepository corporateUserRepository;
    private final CustomModelMapper modelMapper;

    @Override
    public DataResult<AddedCorporateUserDto> add(AddCorporateUserDto addCorporateUserDto) {
        CorprateUser corprateUser = modelMapper.ofStrict().map(addCorporateUserDto, CorprateUser.class);
        CorprateUser save = corporateUserRepository.save(corprateUser);
        AddedCorporateUserDto addedCorporateUserDto = modelMapper.ofStrict().map(save, AddedCorporateUserDto.class);
        return SuccessDataResult.of(addedCorporateUserDto);
    }
}
