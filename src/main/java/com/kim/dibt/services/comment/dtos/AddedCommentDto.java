package com.kim.dibt.services.comment.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AddedCommentDto {
    private Long id;
    private String content;
    private String username;
    private String createdDate;
}
