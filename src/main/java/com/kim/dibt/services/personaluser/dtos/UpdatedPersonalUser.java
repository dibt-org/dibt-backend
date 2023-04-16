package com.kim.dibt.services.personaluser.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdatedPersonalUser {
    private String firstName;
    private String lastName;
    private String address;
    private String nationalityId;
    private String email;
    private String username;
}
