package com.kim.dibt.services.corporateuser;

import com.kim.dibt.core.utils.result.DataResult;
import com.kim.dibt.services.corporateuser.dtos.AddCorporateUserDto;
import com.kim.dibt.services.corporateuser.dtos.AddedCorporateUserDto;
import com.kim.dibt.services.corporateuser.dtos.GetDetailOfCorporateUser;

import java.util.List;

public interface CorporateUserService {
    DataResult<AddedCorporateUserDto> add(AddCorporateUserDto addCorporateUserDto);
    DataResult<List<GetDetailOfCorporateUser>> getByCityId(Long cityId);
}
