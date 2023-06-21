package com.kim.dibt.services.media.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
public class AddedMediaDto {
    private Long id;
    private String mediaUrl;
    private String mediaType;
}
