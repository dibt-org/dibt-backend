package com.kim.dibt.services.post.dtos;


import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class GetAllPostDto {
    private Long id;
    private String title;
    private String content;
    private String username;
    private List<String> mediaUrls;
    private List<String> mentions;
    private String createdAtMessage;
}
