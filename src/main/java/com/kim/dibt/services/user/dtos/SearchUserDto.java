package com.kim.dibt.services.user.dtos;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class SearchUserDto {
    private Long id;
    private String username;
    private String type;
    private String logo;
}
