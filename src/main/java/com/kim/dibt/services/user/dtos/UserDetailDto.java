package com.kim.dibt.services.user.dtos;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode
public class UserDetailDto {
    private Long id;
    private String firstName;
    private String lastName;

}
