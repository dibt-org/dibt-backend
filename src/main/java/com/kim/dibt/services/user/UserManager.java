package com.kim.dibt.services.user;

import com.kim.dibt.core.utils.business.CustomModelMapper;
import com.kim.dibt.core.utils.constants.CoreConstants;
import com.kim.dibt.core.utils.result.DataResult;
import com.kim.dibt.core.utils.result.ErrorDataResult;
import com.kim.dibt.core.utils.result.SuccessDataResult;
import com.kim.dibt.security.models.User;
import com.kim.dibt.security.repo.UserRepository;
import com.kim.dibt.services.ServiceMessages;
import com.kim.dibt.services.user.dtos.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserManager implements UserService {
    private final UserRepository userRepository;
    private final CustomModelMapper modelMapper;

    @Override
    public DataResult<User> getUserById(long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return ErrorDataResult.of(null, ServiceMessages.USER_NOT_FOUND);
        }
        return SuccessDataResult.of(user, ServiceMessages.USER_FOUND);
    }
}
