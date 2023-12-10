package com.elderpereira.springessential.domain.ports;

import com.elderpereira.springessential.domain.model.Anime;

import java.util.List;

public interface AnimeRepositoryPort {
    List<Anime> listAll();

    Anime findById(long id);

    Anime save(Anime anime);

    void delete(long id);

    Anime replace(long id, Anime anime);
}
