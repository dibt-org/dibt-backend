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
    @Size(min = 2)
    private String firstName;
    @Size(min = 2)
//    @Pattern(regexp = "^([a-zA-ZiİçÇşŞğĞÜüÖö]*\\s*)*$\n", message = "soyisim sadece harflerden oluşabilir")
    private String lastName;
    @Size(min = 11, max = 11)
    @Pattern(regexp = "^[0-9]*$", message = "Tc kimlik numarası sadece rakamlardan oluşabilir")
    private String nationalityId;
    @Pattern(regexp = "^(0[1-9]|[12][0-9]|3[01])[.](0[1-9]|1[012])[.](19|20)\\d\\d$", message = "Doğum tarihi gün.ay.yıl formatında olmalıdır")
    private String birthDate;

}
