package com.kim.dibt.services.corporateuser.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class AddedCorporateUserDto {
    private Long id;
    private String name;
    private String website;
    private String phone;
    private String address;
    private String email;
    private String username;
    private String password;
    private String about;
}
