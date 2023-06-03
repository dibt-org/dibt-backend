package com.kim.dibt.services.user;

import com.kim.dibt.core.utils.result.DataResult;
import com.kim.dibt.core.utils.result.Result;
import com.kim.dibt.security.models.RoleType;
import com.kim.dibt.security.models.User;
import com.kim.dibt.services.user.dtos.UpdateAboutUserDto;
import com.kim.dibt.services.user.dtos.UpdateEmailUserDto;
import java.util.function.Function;

public interface UserService {
    DataResult<User> getUserById(long id);
    Result addRoleToUser(Long userId, RoleType roleType);

    DataResult<User> findByUsername(String username);
    DataResult<UpdateEmailUserDto> update(UpdateEmailUserDto updateEmailUserDto);
    DataResult<UpdateAboutUserDto> update(UpdateAboutUserDto updateAboutUserDto);

    public <T> DataResult<T> updateUserField(Function<User, T> fieldUpdater, String successMessage);

}
