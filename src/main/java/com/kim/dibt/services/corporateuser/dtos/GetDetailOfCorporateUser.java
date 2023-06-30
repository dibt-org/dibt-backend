package com.kim.dibt.services.corporateuser.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetDetailOfCorporateUser {
    private Long id;
    private String name;
    private String website;
    private String email;
    private String phone;
    private String address;
    private Long complaintCount;
    private String color;
    private String logo;
}
