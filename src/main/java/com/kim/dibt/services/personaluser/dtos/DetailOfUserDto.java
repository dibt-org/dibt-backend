package com.kim.dibt.services.personaluser.dtos;


import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class DetailOfUserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String nationalityId;
    private String birthDate;
    private String email;
    private String username;

    public DetailOfUserDto(Long id, String firstName, String lastName, String nationalityId, String birthDate, String email, String username) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.nationalityId = nationalityId;
        this.birthDate = birthDate;
        this.email = email;
        this.username = username;
    }
}
