package com.elderpereira.springessential.infra.databases.mongo.document;

import com.elderpereira.springessential.domain.model.Comment;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Document("posts")
public class PostDocument implements Serializable {
    @Id
    private String id;
    private Long animeId;
    private String title;
    private List<Comment> comments;
}
