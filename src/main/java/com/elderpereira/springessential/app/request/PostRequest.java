package com.elderpereira.springessential.app.request;

import com.elderpereira.springessential.domain.model.Comment;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
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
public class PostRequest implements Serializable {
    @Min(value = 1, message = "The field 'animeId' must have be min 1")
    private Long animeId;
    @NotBlank(message = "The field 'title' must not be empty")
    private String title;
    @Min(value = 1, message = "The field 'comments' must have be min 1")
    private List<Comment> comments;
}
