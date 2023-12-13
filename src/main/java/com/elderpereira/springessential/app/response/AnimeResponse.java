package com.elderpereira.springessential.app.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AnimeResponse extends RepresentationModel<AnimeResponse> implements Serializable {
    private Long id;
    private String name;
    private int episodes;
}
