package com.elderpereira.springessential.domain.ports;

import com.elderpereira.springessential.domain.model.Anime;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AnimeServicePort {
    List<Anime> listAll();

    Anime findById(long id);

    Anime save(Anime anime);

    void delete(long id);

    Anime replace(long id, Anime anime);

    void updateEpisodes(long id, int episodes);
}
