package com.kim.dibt.repo;

import com.kim.dibt.models.CorprateUser;
import com.kim.dibt.models.PersonalUser;
import com.kim.dibt.services.personaluser.dtos.DetailOfUserDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CorporateUserRepository extends JpaRepository<CorprateUser, Long> {

    List<CorprateUser> findAllByCityId(Long cityId);
}
