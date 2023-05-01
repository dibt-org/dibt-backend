package com.kim.dibt.models;

import com.kim.dibt.security.models.User;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "personal_users")
@RequiredArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@PrimaryKeyJoinColumn(name = "user_id", referencedColumnName = "id")
public class PersonalUser extends User {
    private String firstName;
    private String lastName;
    private String nationalityId;
    private String birthDate;

}
