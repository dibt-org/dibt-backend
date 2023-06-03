package com.kim.dibt.services.comment.dtos;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetAllCommentDto {
    private Long id;
    private String content;
    private String createdDate;
    private String username;
    private String userImage;
    private Long userId;
    private Long likes;

}
