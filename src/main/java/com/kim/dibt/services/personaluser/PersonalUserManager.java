package com.kim.dibt.services.personaluser;

import com.kim.dibt.core.utils.business.BusinessRule;
import com.kim.dibt.core.utils.business.CustomModelMapper;
import com.kim.dibt.core.utils.result.*;
import com.kim.dibt.mernis.Mernis;
import com.kim.dibt.models.PersonalUser;
import com.kim.dibt.repo.PersonalUserRepo;
import com.kim.dibt.security.config.JwtService;
import com.kim.dibt.security.models.User;
import com.kim.dibt.security.repo.UserRepository;
import com.kim.dibt.services.ServiceMessages;
import com.kim.dibt.services.personaluser.dtos.*;
import com.kim.dibt.services.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
@Slf4j
public class PersonalUserManager implements PersonalUserService {
    private final CustomModelMapper modelMapper;
    private final PersonalUserRepo personalUserRepo;
    private final JwtService jwtService;
    private final UserService userService;
    private final UserRepository userRepository;

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
        boolean isRealPerson;
        try {
            isRealPerson = Mernis.verify(
                    addPersonalUser.getNationalityId(),
                    addPersonalUser.getFirstName(),
                    addPersonalUser.getLastName(),
                    extractYear(addPersonalUser.getBirthDate()));
        } catch (Exception e) {
            return ErrorDataResult.of(null, ServiceMessages.PERSONAL_USER_NOT_VALID);
        }
        if (Boolean.FALSE.equals(isRealPerson)) {
            return ErrorDataResult.of(null, ServiceMessages.PERSONAL_USER_NOT_VALID);
        }
        User byUsername = userService.findByUsername(personalUser.getUsername()).getData();
        byUsername.setIsVerified(true);
        userRepository.save(byUsername);
        modelMapper.ofStrict().map(addPersonalUser, personalUser);
        PersonalUser savedPersonalUser = personalUserRepo.save(personalUser);
        AddedPersonalUser addedPersonalUser = modelMapper.ofStandard().map(savedPersonalUser, AddedPersonalUser.class);
        return SuccessDataResult.of(addedPersonalUser, ServiceMessages.PERSONAL_USER_ADDED);

    }

    // extract year from birth date ex 01.01.1990 -> 1990
    private int extractYear(String birthDate) {
        String[] split = birthDate.split("\\.");
        return Integer.parseInt(split[2]);
    }

    @Override
    public Result updatePersonalUser(
            UpdatePersonalUser updatePersonalUser,
            long userId,
            HttpServletRequest request
    ) {
        String token = request.getHeader("Authorization").substring(7);
        String username = jwtService.extractUsername(token);
        Optional<PersonalUser> personalUser = personalUserRepo.findByUsername(username);
        if (personalUser.isEmpty() || personalUser.get().getId() != userId) {
            return ErrorDataResult.of(null, ServiceMessages.USER_NOT_FOUND);
        }
        modelMapper.ofStrict().map(updatePersonalUser, personalUser.get());
        log.debug("personalUser: {}", personalUser.get());
        PersonalUser user = personalUserRepo.save(personalUser.get());
        UpdatedPersonalUser updatedPersonalUser = modelMapper.ofStandard().map(user, UpdatedPersonalUser.class);
        return SuccessDataResult.of(updatedPersonalUser, ServiceMessages.PERSONAL_USER_UPDATED);


    }

    @Override
    public DataResult<DetailOfUserDto> getDetailOfUser() {
        DetailOfUserDto detailOfUser = personalUserRepo.getDetailOfUser(username());
        if (detailOfUser == null) {
            return ErrorDataResult.of(null, ServiceMessages.USER_NOT_FOUND);
        }
        return SuccessDataResult.of(detailOfUser, ServiceMessages.USER_FOUND);
    }

    @Override
    public DataResult<DetailOfUserDto> getDetailOfUser(String username) {
        DetailOfUserDto detailOfUser = personalUserRepo.getDetailOfUser(username);
        if (detailOfUser == null) {
            return ErrorDataResult.of(null, ServiceMessages.USER_NOT_FOUND);
        }
        detailOfUser.setEmail(null);
        detailOfUser.setBirthDate(null);
        detailOfUser.setNationalityId(null);
        return SuccessDataResult.of(detailOfUser, ServiceMessages.USER_FOUND);
    }

    @Override
    public Result isVerifiedUser() {
        User byUsername = userRepository.isVerified(username());
        log.info("byUsername: {}", byUsername);
        if (byUsername == null) {
            return ErrorResult.of(ServiceMessages.USER_NOT_FOUND);
        }
        if (Boolean.TRUE.equals(byUsername.getIsVerified())) {
            return SuccessResult.of();
        }
        return ErrorResult.of(ServiceMessages.USER_NOT_VERIFIED);
    }

    private String username() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        log.debug("username: {}", username);
        return username;
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
