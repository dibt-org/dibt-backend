package com.kim.dibt.services.post.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class AddPostDto {
    @Size(min = 2, max = 100)
    @NotNull
    private String title;
    @Size(min = 10, max = 1000)
    @NotNull
    private String content;
}
