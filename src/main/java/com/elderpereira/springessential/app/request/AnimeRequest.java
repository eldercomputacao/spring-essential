package com.elderpereira.springessential.app.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AnimeRequest implements Serializable {
    @NotBlank(message = "The field 'name' must not be empty")
    @Size(min = 4, max = 100, message = "The field 'name' must have between 4 and 100 characters")
    private String name;
    @Min(value = 1, message = "The field 'episodes' must have be min 1")
    private int episodes;
}
