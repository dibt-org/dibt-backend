package com.kim.dibt.services.user;

import com.kim.dibt.core.utils.result.DataResult;
import com.kim.dibt.core.utils.result.Result;
import com.kim.dibt.security.models.RoleType;
import com.kim.dibt.security.models.User;

public interface UserService {
    DataResult<User> getUserById(long id);
    Result addRoleToUser(Long userId, RoleType roleType);
}
