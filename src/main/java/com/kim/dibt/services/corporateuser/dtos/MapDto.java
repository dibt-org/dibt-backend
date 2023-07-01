package com.kim.dibt.services.corporateuser.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MapDto {
    private Long id;
    private Long cityId;
    private Long complaintCount;
    private String color;
}
