package com.elderpereira.springessential.domain.service;

import com.elderpereira.springessential.domain.model.Anime;
import com.elderpereira.springessential.domain.ports.AnimeRepositoryPort;
import com.elderpereira.springessential.domain.ports.AnimeServicePort;

import java.util.List;

public class AnimeService implements AnimeServicePort {

    private AnimeRepositoryPort animeRepository;

    public AnimeService(AnimeRepositoryPort animeRepository) {
        this.animeRepository = animeRepository;
    }

    @Override
    public List<Anime> listAll() {
        return animeRepository.listAll();
    }

    @Override
    public Anime findById(long id) {
        return animeRepository.findById(id);
    }

    @Override
    public Anime save(Anime anime) {
        return animeRepository.save(anime);
    }

    @Override
    public void delete(long id) {
        animeRepository.delete(id);
    }

    @Override
    public Anime replace(long id, Anime anime) {
        return animeRepository.replace(id, anime);
    }

    @Override
    public void updateEpisodes(long id, int episodes) {
        animeRepository.updateEpisodes(id, episodes);
    }
}
