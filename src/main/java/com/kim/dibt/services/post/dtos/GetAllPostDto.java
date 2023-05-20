package com.kim.dibt.services.post.dtos;


import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class GetAllPostDto {
    private Long id;
    private String title;
    private String content;
    private String username;
}
