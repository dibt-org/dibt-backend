package com.kim.dibt.security.repo;

import com.kim.dibt.models.PersonalUser;
import com.kim.dibt.security.models.Role;
import com.kim.dibt.security.models.RoleType;
import com.kim.dibt.security.models.User;
import com.kim.dibt.services.user.dtos.SearchUserDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);


    @Query("SELECT u.username FROM User u WHERE u.username LIKE %?1%")
    List<String> findUsernameByQuery(String query);


    @Query("SELECT u FROM User u WHERE u.username = ?1")
    User isVerified(String username);


    @Query("""
        select new com.kim.dibt.services.user.dtos.SearchUserDto(u.id, u.username, case when pu.id is not null then 'PERSONAL_USER' else 'CORPORATE_USER' end, case when pu.id is not null then 'NO_LOGO' else cu.logo end) 
        from User u 
        left join PersonalUser pu on pu.id = u.id
        left join CorprateUser cu on cu.id = u.id
        where u.username ilike %:query%
        or ((pu.firstName ilike %:query% or pu.lastName ilike %:query%)
        or (cu.name ilike %:query% or cu.website ilike %:query%))
        """)
    List<SearchUserDto> search(@Param("query") String query);


       /*
      @Query("SELECT new com.kim.dibt.models.SearchUserDto(u.id, u.username, CASE WHEN u INSTANCE OF com.kim.dibt.security.models.CorprateUser THEN 'Corporate' ELSE 'Personal' END) " +
           "FROM User u LEFT JOIN u.city c " +
           "WHERE u.username LIKE %:query% OR " +
           "(u INSTANCE OF com.kim.dibt.security.models.CorprateUser AND u.website LIKE %:query%) OR " +
           "(u INSTANCE OF com.kim.dibt.models.PersonalUser AND (u.firstName LIKE %:query% OR u.lastName LIKE %:query%))")
     */

}
