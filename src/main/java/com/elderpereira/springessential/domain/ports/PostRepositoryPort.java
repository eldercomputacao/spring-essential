package com.elderpereira.springessential.domain.ports;

import com.elderpereira.springessential.domain.model.Post;

import java.util.List;

public interface PostRepositoryPort {
    List<Post> findPostsByAnimeId(long animeId);
    Post savePost(Post post);
}
