package com.elderpereira.springessential.app.response;

import com.elderpereira.springessential.domain.model.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AnimeWithPostsResponse implements Serializable {
    private Long id;
    private String name;
    private int episodes;
    private List<Post> posts;

    @Data
    public static class Post implements Serializable {
        private String title;
        private List<Comment> comments;
    }
}
