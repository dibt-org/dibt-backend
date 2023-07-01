package com.kim.dibt.services.corporateuser.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetDetailOfCorporateUserForMapDto {
    private Long id;
    private Long CityId;
    private Long complaintCount;

}
