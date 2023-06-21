package com.kim.dibt.services.mention.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
public class AddMentionDto {
    private Long id;
    private String username;
    private Long postId;
    private Long userId;
}
