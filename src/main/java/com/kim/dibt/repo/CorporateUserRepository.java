package com.kim.dibt.repo;

import com.kim.dibt.models.CorprateUser;
import com.kim.dibt.models.PersonalUser;
import com.kim.dibt.services.corporateuser.dtos.GetDetailOfCorporateUser;
import com.kim.dibt.services.corporateuser.dtos.GetDetailOfCorporateUserForMapDto;
import com.kim.dibt.services.personaluser.dtos.DetailOfUserDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CorporateUserRepository extends JpaRepository<CorprateUser, Long> {

    List<CorprateUser> findAllByCityId(Long cityId);
    @Query(value = """
        select new com.kim.dibt.services.corporateuser.dtos.GetDetailOfCorporateUser(
            u.id,
            u.name,
            u.website,
            u.email,
            u.phone,
            u.address,
            count(m.user.id) as complaintCount,
            'red',
            u.logo,
            u.username,
            u.about,
            'CORPORATE'
            )
        from CorprateUser u
        left join Mention m on u.id = m.user.id
        where u.username = :username
        group by u.id, u.name, u.website, u.email, u.phone, u.address, u.logo, u.username, u.about
        """)
    GetDetailOfCorporateUser findByUsername(String username);

    @Query(value = """
        select new com.kim.dibt.services.corporateuser.dtos.GetDetailOfCorporateUserForMapDto(
            u.id,
            c.id,
            count(m.user.id) as complaintCount
            )
        from CorprateUser u
        left join Mention m on u.id = m.user.id
        join City c on u.city.id = c.id
        group by u.id, c.id
        """)

    List<GetDetailOfCorporateUserForMapDto> getAllDetailOfCorporate();


    @Query(value= """
        select count(m.user.id)
        from CorprateUser u 
        left join Mention m on u.id = m.user.id
""")
    Long countAl();

}
