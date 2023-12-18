package com.elderpereira.springessential.app.rest.impl;

import com.elderpereira.springessential.app.request.AnimeRequest;
import com.elderpereira.springessential.app.response.AnimeResponse;
import com.elderpereira.springessential.app.response.AnimeWithPostsResponse;
import com.elderpereira.springessential.app.response.PostResponse;
import com.elderpereira.springessential.app.rest.AnimeRestController;
import com.elderpereira.springessential.domain.model.Anime;
import com.elderpereira.springessential.domain.ports.AnimeServicePort;
import com.elderpereira.springessential.domain.ports.PostServicePort;
import com.elderpereira.springessential.util.ModelMapperUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class AnimeRestControllerImp implements AnimeRestController {

    @Autowired
    private AnimeServicePort animeService;

    @Autowired
    private PostServicePort postService;

    @Override
    public ResponseEntity<List<AnimeResponse>> list() {
        var animes = animeService.listAll();
        var animesResponse = ModelMapperUtil.mapAll(animes, AnimeResponse.class);
        animesResponse.forEach(animeResponse -> animeResponse.add(linkTo(methodOn(AnimeRestControllerImp.class)
                .findById(animeResponse.getId())).withSelfRel()));
        return ResponseEntity.ok(animesResponse);
    }

    @Override
    public ResponseEntity<AnimeResponse> findById(@PathVariable long id) {
        var anime = animeService.findById(id);
        var animeResponse = ModelMapperUtil.map(anime, AnimeResponse.class);
        animeResponse.add(linkTo(methodOn(AnimeRestControllerImp.class).findById(animeResponse.getId())).withSelfRel());
        return ResponseEntity.ok(animeResponse);
    }

    @Override
    public ResponseEntity<AnimeResponse> save(@Valid @RequestBody AnimeRequest animeRequest) {
        var anime = animeService.save(ModelMapperUtil.map(animeRequest, Anime.class));
        var animeResponse = ModelMapperUtil.map(anime, AnimeResponse.class);
        animeResponse.add(linkTo(methodOn(AnimeRestControllerImp.class).findById(animeResponse.getId())).withSelfRel());
        return new ResponseEntity<>(animeResponse, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<AnimeResponse> replace(@PathVariable long id, @Valid @RequestBody AnimeRequest animeRequest) {
        var anime = animeService.replace(id, ModelMapperUtil.map(animeRequest, Anime.class));
        var animeResponse = ModelMapperUtil.map(anime, AnimeResponse.class);
        animeResponse.add(linkTo(methodOn(AnimeRestControllerImp.class).findById(animeResponse.getId())).withSelfRel());
        return new ResponseEntity<>(animeResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> updateEpisodes(long id, int episodes) {
        animeService.updateEpisodes(id, episodes);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<Void> delete(@PathVariable long id) {
        animeService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<AnimeWithPostsResponse> animeWithPosts(long id) {
        var anime = animeService.findById(id);
        var posts = postService.findPostsByAnimeId(anime.getId());
        var animeWithPosts = AnimeWithPostsResponse.builder()
                .id(anime.getId())
                .name(anime.getName())
                .episodes(anime.getEpisodes())
                .posts(ModelMapperUtil.mapAll(posts, AnimeWithPostsResponse.Post.class))
                .build();
        return ResponseEntity.ok(animeWithPosts);
    }
}
