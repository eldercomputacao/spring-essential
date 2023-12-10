package com.elderpereira.springessential.infra.database.postgres.repository;

import com.elderpereira.springessential.infra.database.postgres.entity.AnimeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnimeJpaRepository extends JpaRepository<AnimeEntity, Long> {
}
