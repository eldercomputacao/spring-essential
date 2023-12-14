package com.elderpereira.springessential.app.rest;

import com.elderpereira.springessential.app.request.AnimeRequest;
import com.elderpereira.springessential.app.request.PostRequest;
import com.elderpereira.springessential.app.response.AnimeResponse;
import com.elderpereira.springessential.app.response.PostResponse;
import com.elderpereira.springessential.domain.model.Post;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("posts")
@Tag(name = "PostRestController")
public interface PostRestController {

    @GetMapping(path = "{id}")
    @Operation(summary = "Finds all 'posts' for 'animeId'", responses = {
            @ApiResponse(description = "Success", responseCode = "200", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(schema =
                    @Schema(implementation = Post.class)))
            }),
            @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
    })
    ResponseEntity<List<PostResponse>> listPosts(@PathVariable long id);

    @PostMapping()
    @Operation(summary = "Save a 'post'", responses = {
            @ApiResponse(description = "Success", responseCode = "201",
                    content = @Content(schema = @Schema(implementation = Post.class))),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
    })
    ResponseEntity<PostResponse> save(@RequestBody PostRequest post);

}
