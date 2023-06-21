package com.kim.dibt.services.post.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AddedPostDto {
    private Long id;
    private String title;
    private String content;
    List<String> mediaUrls;
    List<String> mentions;
}

