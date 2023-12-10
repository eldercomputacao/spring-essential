package com.elderpereira.springessential.controller;

import com.elderpereira.springessential.dto.AnimeDTO;
import com.elderpereira.springessential.model.Anime;
import com.elderpereira.springessential.service.AnimeService;
import com.elderpereira.springessential.util.ModelMapperUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("animes")
@Log4j2
@RequiredArgsConstructor
public class AnimeController {
    private final AnimeService animeService;

    @GetMapping
    public ResponseEntity<List<Anime>> list() {
        return ResponseEntity.ok(animeService.listAll());
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Anime> findById(@PathVariable long id) {
        return ResponseEntity.ok(animeService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Anime> save(@RequestBody AnimeDTO anime) {
        return new ResponseEntity<>(animeService.save(ModelMapperUtil.map(anime, Anime.class)), HttpStatus.CREATED);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<Anime> replace(@PathVariable long id, @RequestBody AnimeDTO anime) {
        return new ResponseEntity<>(animeService.replace(id, ModelMapperUtil.map(anime, Anime.class)), HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        animeService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
