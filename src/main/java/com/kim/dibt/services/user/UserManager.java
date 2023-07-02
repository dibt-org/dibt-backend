package com.kim.dibt.services.user;

import com.kim.dibt.core.utils.business.BusinessRule;
import com.kim.dibt.core.utils.business.CustomModelMapper;
import com.kim.dibt.core.utils.constants.CoreConstants;
import com.kim.dibt.core.utils.result.*;
import com.kim.dibt.security.models.RoleType;
import com.kim.dibt.security.models.User;
import com.kim.dibt.security.repo.RoleRepository;
import com.kim.dibt.security.repo.UserRepository;
import com.kim.dibt.services.ServiceMessages;
import com.kim.dibt.services.user.dtos.SearchUserDto;
import com.kim.dibt.services.user.dtos.UpdateAboutUserDto;
import com.kim.dibt.services.user.dtos.UpdateEmailUserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class UserManager implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository repository;
    private final CustomModelMapper modelMapper;

    @Override
    public DataResult<User> getUserById(long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return ErrorDataResult.of(null, ServiceMessages.USER_NOT_FOUND);
        }
        return SuccessDataResult.of(user, ServiceMessages.USER_FOUND);
    }

    @Override
    public Result addRoleToUser(Long userId, RoleType roleType) {
        var result = BusinessRule.run(
                checkRoleExists(roleType),
                checkUserExists(userId)
        );
        if (result != null) {
            return result;
        }
        if (checkUserExistsByIdAndRoleName(userId, roleType)) {
            return ErrorResult.of(CoreConstants.USER_ALREADY_HAS_ROLE);
        }
        User user = userRepository.findById(userId).orElse(null);
        var role = repository.findByRoleName(roleType).orElse(null);
        assert user != null;
        user.getRoles().add(role);
        userRepository.save(user);
        return SuccessResult.of(ServiceMessages.ROLE_ADDED_TO_USER);
    }

    @Override
    public DataResult<User> findByUsername(String username) {
        User user = userRepository.findByUsername(username).orElse(null);
        if (user == null) {
            return ErrorDataResult.of(null, ServiceMessages.USER_NOT_FOUND);
        }
        return SuccessDataResult.of(user, ServiceMessages.USER_FOUND);
    }

    private Result checkRoleExists(RoleType roleType) {
        return repository.existsByRoleName(roleType) ? SuccessResult.of() : ErrorResult.of(CoreConstants.ROLE_NOT_FOUND);
    }

    private Result checkUserExists(Long userId) {
        return repository.existsById(userId) ? SuccessResult.of() : ErrorResult.of(CoreConstants.USER_NOT_FOUND);
    }

    private Result checkUserExistByUsername() {
        Optional<User> byUsername = this.userRepository.findByUsername(username());
        if (byUsername.isEmpty())
            return ErrorResult.of(ServiceMessages.USER_NOT_FOUND);
        return SuccessResult.of();
    }

    private boolean checkUserExistsByIdAndRoleName(Long userId, RoleType roleType) {
        var user = userRepository.findById(userId).orElse(null);
        assert user != null;
        for (var role : user.getRoles()) {
            if (role.getRoleName().equals(roleType)) {
                return true;
            }
        }
        return false;
    }

    private String username() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    @Override
    public <T> DataResult<T> updateUserField(Function<User, T> fieldUpdater, String successMessage) {
        Result run = BusinessRule.run(checkUserExistByUsername());
        if (run != null) {
            return ErrorDataResult.of(null, run.getMessage());
        }
        User user = userRepository.findByUsername(username()).orElse(null);
        if (user == null) {
            return ErrorDataResult.of(null, ServiceMessages.USER_NOT_FOUND);
        }
        T updatedField = fieldUpdater.apply(user);
        userRepository.save(user);
        return SuccessDataResult.of(updatedField, successMessage);
    }

    @Override
    public DataResult<List<String>> query(String query) {
        return SuccessDataResult.of(this.userRepository.findUsernameByQuery(query), ServiceMessages.QUERY_SUCCESS);
    }

    @Override
    public DataResult<List<SearchUserDto>> search(String query) {
        return SuccessDataResult.of(this.userRepository.search(query), ServiceMessages.QUERY_SUCCESS);
    }


    @Override
    public DataResult<UpdateEmailUserDto> update(UpdateEmailUserDto updateEmailUserDto) {
        return updateUserField(
                user -> {
                    user.setEmail(updateEmailUserDto.getEmail());
                    return modelMapper.ofStandard().map(userRepository.save(user), UpdateEmailUserDto.class);
                },
                ServiceMessages.UPDATE_EMAIL
        );
    }

    @Override
    public DataResult<UpdateAboutUserDto> update(UpdateAboutUserDto updateAboutUserDto) {
        return updateUserField(user -> {
            user.setAbout(updateAboutUserDto.getAbout());
            return modelMapper.ofStandard().map(userRepository.save(user), UpdateAboutUserDto.class);
        }, ServiceMessages.UPDATE_ABOUT);
    }


}
