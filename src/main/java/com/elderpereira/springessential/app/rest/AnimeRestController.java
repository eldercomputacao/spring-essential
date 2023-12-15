package com.elderpereira.springessential.app.rest;

import com.elderpereira.springessential.app.request.AnimeRequest;
import com.elderpereira.springessential.app.response.AnimeResponse;
import com.elderpereira.springessential.app.response.AnimeWithPostsResponse;
import com.elderpereira.springessential.domain.model.Post;
import com.elderpereira.springessential.util.MediaTypeYmal;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("animes")
@Tag(name = "AnimeRestController")
public interface AnimeRestController {

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE,
            MediaTypeYmal.APPLICATION_YAML_VALUE})
    @Operation(summary = "Finds all 'animes'", responses = {
            @ApiResponse(description = "Success", responseCode = "200", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(schema =
                    @Schema(implementation = AnimeResponse.class)))
            }),
            @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
    })
    ResponseEntity<List<AnimeResponse>> list();


    @GetMapping(path = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE, MediaTypeYmal.APPLICATION_YAML_VALUE})
    @Operation(summary = "Find 'anime' by id", responses = {
            @ApiResponse(description = "Success", responseCode = "200", content = @Content(schema =
            @Schema(implementation = AnimeResponse.class))),
            @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
    })
    ResponseEntity<AnimeResponse> findById(@PathVariable long id);

    @PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE,
            MediaTypeYmal.APPLICATION_YAML_VALUE},
                 consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE,
            MediaTypeYmal.APPLICATION_YAML_VALUE})
    @Operation(summary = "Save a 'anime'", responses = {
            @ApiResponse(description = "Success", responseCode = "201",
                    content = @Content(schema = @Schema(implementation = AnimeResponse.class))),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
    })
    ResponseEntity<AnimeResponse> save(@Valid @RequestBody AnimeRequest animeRequest);

    @PutMapping(path = "/{id}",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE,
                    MediaTypeYmal.APPLICATION_YAML_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE,
                    MediaTypeYmal.APPLICATION_YAML_VALUE})
    @Operation(summary = "Update 'anime'", responses = {
            @ApiResponse(description = "Success", responseCode = "200",
                    content = @Content(schema = @Schema(implementation = AnimeResponse.class))),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
    })
    ResponseEntity<AnimeResponse> replace(@PathVariable long id, @Valid @RequestBody AnimeRequest animeRequest);

    @DeleteMapping(path = "/{id}")
    @Operation(summary = "Delete a 'anime'", responses = {
            @ApiResponse(description = "Success", responseCode = "204", content = @Content),
            @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
    })
    ResponseEntity<Void> delete(@PathVariable long id);

    @GetMapping(path = "{id}/posts", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE,
            MediaTypeYmal.APPLICATION_YAML_VALUE})
    @Operation(summary = "Finds all 'posts' for 'anime'", responses = {
            @ApiResponse(description = "Success", responseCode = "200", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(schema =
                    @Schema(implementation = AnimeWithPostsResponse.class)))
            }),
            @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
    })
    ResponseEntity<AnimeWithPostsResponse> animeWithPosts(@PathVariable long id);
}
