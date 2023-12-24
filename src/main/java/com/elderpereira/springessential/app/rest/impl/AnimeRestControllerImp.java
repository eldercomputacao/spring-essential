package com.elderpereira.springessential.app.rest.impl;

import com.elderpereira.springessential.app.request.AnimeRequest;
import com.elderpereira.springessential.app.response.AnimeResponse;
import com.elderpereira.springessential.app.response.AnimeWithPostsResponse;
import com.elderpereira.springessential.app.rest.AnimeRestController;
import com.elderpereira.springessential.domain.model.Anime;
import com.elderpereira.springessential.domain.ports.AnimeServicePort;
import com.elderpereira.springessential.domain.ports.PostServicePort;
import com.elderpereira.springessential.infra.databases.postgres.entity.AnimeEntity;
import com.elderpereira.springessential.infra.databases.postgres.repository.AnimeJpaRepository;
import com.elderpereira.springessential.util.ModelMapperUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
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

    @Autowired
    private AnimeJpaRepository animeJpaRepository;

    @Autowired
    private PagedResourcesAssembler<AnimeResponse> pagedResourcesAssembler;

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

    @Override
    public ResponseEntity<PagedModel<EntityModel<AnimeResponse>>> listWithPagedModel(Integer page, Integer size,
                                                                                     String direction) {
        Direction sortDirection = direction.equalsIgnoreCase("desc") ? Direction.DESC : Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "name"));

        Page<AnimeEntity> animesEntityPage = animeJpaRepository.findAll(pageable);

        Page<AnimeResponse> animesResponsePage = animesEntityPage
                .map(animeEntity -> ModelMapperUtil.map(animeEntity, AnimeResponse.class));
        animesResponsePage.forEach(animeResponse -> animeResponse.add(linkTo(methodOn(AnimeRestControllerImp.class)
                .findById(animeResponse.getId())).withSelfRel()));

        Link link = linkTo(methodOn(AnimeRestControllerImp.class)
                .listWithPagedModel(pageable.getPageNumber(), pageable.getPageSize(), "asc")).withSelfRel();
        PagedModel<EntityModel<AnimeResponse>> pagedModel = pagedResourcesAssembler.toModel(animesResponsePage, link);

        return ResponseEntity.ok(pagedModel);
    }

    @Override
    public ResponseEntity<Page<AnimeResponse>> listWithPage(Integer page, Integer size, String direction) {
        Direction sortDirection = direction.equalsIgnoreCase("desc") ? Direction.DESC : Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "name"));

        Page<AnimeEntity> animesEntityPage = animeJpaRepository.findAll(pageable);

        Page<AnimeResponse> animesResponsesPage = animesEntityPage
                .map(animeEntity -> ModelMapperUtil.map(animeEntity, AnimeResponse.class));
        animesResponsesPage.forEach(animeResponse -> animeResponse.add(linkTo(methodOn(AnimeRestControllerImp.class)
                .findById(animeResponse.getId())).withSelfRel()));

        return ResponseEntity.ok(animesResponsesPage);
    }
}
