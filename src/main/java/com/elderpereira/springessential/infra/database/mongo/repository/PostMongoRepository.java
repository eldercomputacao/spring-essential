package com.elderpereira.springessential.infra.database.mongo.repository;

import com.elderpereira.springessential.infra.database.mongo.document.PostDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostMongoRepository extends MongoRepository<PostDocument, String> {
    @Query("{animeId: ?0}")
    List<PostDocument> findByAnimeId(long animeId);
}
