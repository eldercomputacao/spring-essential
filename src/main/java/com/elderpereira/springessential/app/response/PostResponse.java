package com.elderpereira.springessential.app.response;

import com.elderpereira.springessential.domain.model.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostResponse implements Serializable {
    private String id;
    private Long animeId;
    private String title;
    private List<Comment> comments;
}
