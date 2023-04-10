package com.kim.dibt.models;

import com.kim.dibt.security.models.User;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "personal_users")
@RequiredArgsConstructor
@PrimaryKeyJoinColumn(referencedColumnName = "id", name = "user_id")
public class PersonalUser extends User {
    private String firstName;
    private String lastName;


}
