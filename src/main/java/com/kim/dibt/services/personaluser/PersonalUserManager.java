package com.kim.dibt.services.personaluser;

import com.kim.dibt.core.utils.business.BusinessRule;
import com.kim.dibt.core.utils.business.CustomModelMapper;
import com.kim.dibt.core.utils.result.*;
import com.kim.dibt.models.PersonalUser;
import com.kim.dibt.repo.PersonalUserRepo;
import com.kim.dibt.security.config.JwtService;
import com.kim.dibt.services.ServiceMessages;
import com.kim.dibt.services.personaluser.dtos.AddPersonalUser;
import com.kim.dibt.services.personaluser.dtos.AddedPersonalUser;
import com.kim.dibt.services.personaluser.dtos.UpdatePersonalUser;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
@Slf4j
public class PersonalUserManager implements PersonalUserService {
    private final CustomModelMapper modelMapper;
    private final PersonalUserRepo personalUserRepo;
    private final JwtService jwtService;

    @Override
    public DataResult<AddedPersonalUser> addPersonalUser(AddPersonalUser addPersonalUser, HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        PersonalUser personalUser = findUserByToken(token);
        if (personalUser == null) {
            return ErrorDataResult.of(null, ServiceMessages.USER_NOT_FOUND);
        }
        var result = BusinessRule.run(checkNationalIdExists(addPersonalUser.getNationalityId(), personalUser.getUsername()));
        if (result != null) {
            return ErrorDataResult.of(null, result.getMessage());
        }
        modelMapper.ofStrict().map(addPersonalUser, personalUser);
        PersonalUser savedPersonalUser = personalUserRepo.save(personalUser);
        AddedPersonalUser addedPersonalUser = modelMapper.of().map(savedPersonalUser, AddedPersonalUser.class);
        return SuccessDataResult.of(addedPersonalUser, ServiceMessages.PERSONAL_USER_ADDED);

    }

    @Override
    public Result updatePersonalUser(
            UpdatePersonalUser updatePersonalUser,
            long userId,
            HttpServletRequest request
    ) {
        String token = request.getHeader("Authorization");
        String username = jwtService.extractUsername(token);
        Optional<PersonalUser> personalUser = personalUserRepo.findByUsername(username);
        if (personalUser.isEmpty() || personalUser.get().getId() != userId) {
            return ErrorDataResult.of(null, ServiceMessages.USER_NOT_FOUND);
        }
        return null;


    }

    // check if user exists by national id but not by user username
    private Result checkNationalIdExists(String nationalId, String username) {
        Optional<PersonalUser> byNationalityId = personalUserRepo.findByNationalityId(nationalId);
        if (byNationalityId.isPresent()) {
            if (byNationalityId.get().getUsername().equals(username)) {
                return SuccessResult.of();
            }
            return ErrorResult.of(ServiceMessages.NATIONAL_ID_ALREADY_TAKEN);
        }
        return SuccessResult.of();
    }
    private PersonalUser findUserByToken(String token) {
        token = token.substring(7);
        String username = jwtService.extractUsername(token);
        if (username == null) {
            return null;
        }
        Optional<PersonalUser> personalUser = personalUserRepo.findByUsername(username);
        return personalUser.orElse(null);
    }


}
