package com.kim.dibt.services.personaluser.dtos;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddPersonalUser {
    @NotNull
    @Size(min = 2)
    @Pattern(regexp = "^[a-zA-Z]*$", message = "Only letters are allowed")
    private String firstName;
    @NotNull
    @Size(min = 2)
    @Pattern(regexp = "^[a-zA-Z]*$", message = "Only letters are allowed")
    private String lastName;
    @NotNull
    @Size(min = 11, max = 11)
    @Pattern(regexp = "^[0-9]*$", message = "Only numbers are allowed")
    private String nationalityId;
    @NotNull
    @Pattern(regexp = "^(0[1-9]|[12][0-9]|3[01])[.](0[1-9]|1[012])[.](19|20)\\d\\d$", message = "Date format must be dd.mm.yyyy")
    private String birthDate;

}
