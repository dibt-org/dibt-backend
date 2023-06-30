package com.kim.dibt.repo;

import com.kim.dibt.models.PersonalUser;
import com.kim.dibt.services.personaluser.dtos.DetailOfUserDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PersonalUserRepo extends JpaRepository<PersonalUser, Long> {

    Optional<PersonalUser> findByUsername(String username);

    Optional<PersonalUser> findByNationalityId(String nationalityId);

    @Query(
            value = """
                    select new com.kim.dibt.services.personaluser.dtos.DetailOfUserDto(
                        p.id,
                        p.firstName,
                        p.lastName,
                        p.nationalityId,
                        p.birthDate,
                        p.email,
                        p.username,
                        p.about
                    ) from PersonalUser p where p.username = ?1
                    """
    )
    DetailOfUserDto getDetailOfUser(String username);


}
