package com.kim.dibt.services.post.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AddedPostDto {
    private Long id;
    private String title;
    private String content;
}

