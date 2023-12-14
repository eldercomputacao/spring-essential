package com.elderpereira.springessential.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    private String id;
    private Long animeId;
    private String title;
    private List<Comment> comments;
}
