package com.kim.dibt.services.personaluser.dtos;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdatePersonalUser {
    @Size(min = 2)
    @Pattern(regexp = "^[a-zA-Z]*$", message = "Only letters are allowed")
    private String firstName;
    @Size(min = 2)
    @Pattern(regexp = "^[a-zA-Z]*$", message = "Only letters are allowed")
    private String lastName;
    @Size(min = 11, max = 11)
    @Pattern(regexp = "^[0-9]*$", message = "Only numbers are allowed")
    private String nationalityId;
    @Pattern(regexp = "^(0[1-9]|[12][0-9]|3[01])[.](0[1-9]|1[012])[.](19|20)\\d\\d$", message = "Date format must be dd.mm.yyyy")
    private String birthDate;
}
