package com.elderpereira.springessential.infra.databases.mongo.repository;

import com.elderpereira.springessential.domain.exceptions.DatabaseOperationsException;
import com.elderpereira.springessential.domain.model.Post;
import com.elderpereira.springessential.domain.ports.PostRepositoryPort;
import com.elderpereira.springessential.infra.databases.mongo.document.PostDocument;
import com.elderpereira.springessential.util.ModelMapperUtil;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class PostRepositoryMongoDB implements PostRepositoryPort {
    private static final Logger LOGGER = Logger.getLogger(PostRepositoryMongoDB.class);

    @Autowired
    private PostMongoRepository postMongoRepository;

    @Override
    public List<Post> findPostsByAnimeId(long animeId) {
        LOGGER.info(String.format("find post to anime: %d", animeId));
        return Optional.of(postMongoRepository.findByAnimeId(animeId))
                .orElse(Collections.emptyList())
                .stream()
                .map(postDocument -> ModelMapperUtil.map(postDocument, Post.class))
                .collect(Collectors.toList());
    }

    @Override
    public Post savePost(Post post) {
        try {
            var document = ModelMapperUtil.map(post, PostDocument.class);
            var postSaved = postMongoRepository.save(document);
            LOGGER.info(String.format("Post saved: %s", postSaved));
            return ModelMapperUtil.map(postSaved, Post.class);
        } catch (Exception ex) {
            LOGGER.error(String.format("Error to save the post: %s", post.getTitle()), ex);
            throw new DatabaseOperationsException(ex.getMessage());
        }
    }
}
