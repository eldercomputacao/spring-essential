package com.elderpereira.springessential.domain.service;

import com.elderpereira.springessential.domain.model.Post;
import com.elderpereira.springessential.domain.ports.PostRepositoryPort;
import com.elderpereira.springessential.domain.ports.PostServicePort;

import java.util.List;

public class PostService implements PostServicePort {

    private PostRepositoryPort postRepositoryPort;

    public PostService(PostRepositoryPort postRepositoryPort) {
        this.postRepositoryPort = postRepositoryPort;
    }

    @Override
    public List<Post> findPostsByAnimeId(long animeId) {
        return postRepositoryPort.findPostsByAnimeId(animeId);
    }

    @Override
    public Post savePost(Post post) {
        return postRepositoryPort.savePost(post);
    }
}
