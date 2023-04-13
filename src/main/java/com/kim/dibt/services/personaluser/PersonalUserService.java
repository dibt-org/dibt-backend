package com.kim.dibt.services.personaluser;

import com.kim.dibt.core.utils.result.DataResult;
import com.kim.dibt.core.utils.result.Result;
import com.kim.dibt.services.personaluser.dtos.AddPersonalUser;
import com.kim.dibt.services.personaluser.dtos.AddedPersonalUser;
import com.kim.dibt.services.personaluser.dtos.UpdatePersonalUser;
import jakarta.servlet.http.HttpServletRequest;

public interface PersonalUserService {
    DataResult<AddedPersonalUser> addPersonalUser(AddPersonalUser addPersonalUser, HttpServletRequest request);

    Result updatePersonalUser(UpdatePersonalUser updatePersonalUser, long userId, HttpServletRequest request);
}
