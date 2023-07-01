package com.kim.dibt.services.corporateuser;

import com.kim.dibt.core.utils.result.DataResult;
import com.kim.dibt.services.corporateuser.dtos.*;

import java.util.List;

public interface CorporateUserService {
    DataResult<AddedCorporateUserDto> add(AddCorporateUserDto addCorporateUserDto);
    DataResult<List<GetDetailOfCorporateUser>> getByCityId(Long cityId);
    DataResult<GetDetailOfCorporateUser> getByUsername(String username);
    DataResult<List<MapDto>> getMap();
}
