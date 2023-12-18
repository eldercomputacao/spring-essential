package com.elderpereira.springessential.infra.databases.postgres.repository;

import com.elderpereira.springessential.infra.databases.postgres.entity.AnimeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AnimeJpaRepository extends JpaRepository<AnimeEntity, Long> {

    @Modifying
    @Query("UPDATE AnimeEntity a SET a.episodes = :episodes WHERE a.id = :id")
    void updateEpisodes(@Param("id") Long id, @Param("episodes") Integer episodes);
}
