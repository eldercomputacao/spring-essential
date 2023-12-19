package com.elderpereira.springessential.infra.databases.postgres.repository;

import com.elderpereira.springessential.domain.exceptions.NotFoundException;
import com.elderpereira.springessential.domain.model.Anime;
import com.elderpereira.springessential.domain.ports.AnimeRepositoryPort;
import com.elderpereira.springessential.infra.databases.postgres.entity.AnimeEntity;
import com.elderpereira.springessential.util.ModelMapperUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AnimeRepositoryPostgres implements AnimeRepositoryPort {

    @Autowired
    private AnimeJpaRepository animeJpaRepository;

    @Override
    public List<Anime> listAll() {
        return ModelMapperUtil.mapAll(animeJpaRepository.findAll(), Anime.class);
    }

    @Override
    public Anime findById(long id) {
        return animeJpaRepository.findById(id)
                .map(anime -> ModelMapperUtil.map(anime, Anime.class))
                .orElseThrow(() -> new NotFoundException("Anime not Found"));
    }

    @Override
    public Anime save(Anime anime) {
        var animeEntity = animeJpaRepository.save(ModelMapperUtil.map(anime, AnimeEntity.class));
        return ModelMapperUtil.map(animeEntity, Anime.class);
    }

    @Override
    public void delete(long id) {
        Anime animeFound = findById(id);
        animeJpaRepository.delete(ModelMapperUtil.map(animeFound, AnimeEntity.class));
    }

    @Override
    public Anime replace(long id, Anime anime) {
        Anime animeSaved = findById(id);
        animeSaved.setName(anime.getName());
        animeSaved.setEpisodes(anime.getEpisodes());
        return save(animeSaved);
    }

    @Transactional
    @Override
    public void updateEpisodes(long id, int episodes) {
        animeJpaRepository.updateEpisodes(id, episodes);
    }
}
