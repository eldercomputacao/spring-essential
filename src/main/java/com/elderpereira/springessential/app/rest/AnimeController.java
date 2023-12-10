package com.elderpereira.springessential.app.rest;

import com.elderpereira.springessential.app.request.AnimeRequest;
import com.elderpereira.springessential.domain.model.Anime;
import com.elderpereira.springessential.domain.ports.AnimeServicePort;
import com.elderpereira.springessential.util.ModelMapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("animes")
public class AnimeController {

    @Autowired
    private AnimeServicePort animeService;

    @GetMapping
    public ResponseEntity<List<Anime>> list() {
        return ResponseEntity.ok(animeService.listAll());
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Anime> findById(@PathVariable long id) {
        return ResponseEntity.ok(animeService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Anime> save(@RequestBody AnimeRequest anime) {
        return new ResponseEntity<>(animeService.save(ModelMapperUtil.map(anime, Anime.class)), HttpStatus.CREATED);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<Anime> replace(@PathVariable long id, @RequestBody AnimeRequest anime) {
        return new ResponseEntity<>(animeService.replace(id, ModelMapperUtil.map(anime, Anime.class)), HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        animeService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
