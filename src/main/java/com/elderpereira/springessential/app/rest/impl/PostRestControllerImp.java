package com.elderpereira.springessential.app.rest.impl;

import com.elderpereira.springessential.app.request.PostRequest;
import com.elderpereira.springessential.app.response.PostResponse;
import com.elderpereira.springessential.app.rest.PostRestController;
import com.elderpereira.springessential.domain.model.Post;
import com.elderpereira.springessential.domain.ports.PostServicePort;
import com.elderpereira.springessential.util.ModelMapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PostRestControllerImp implements PostRestController {

    @Autowired
    private PostServicePort postService;


    @Override
    public ResponseEntity<List<PostResponse>> listPosts(long id) {
        var posts = postService.findPostsByAnimeId(id);
        var  responses = ModelMapperUtil.mapAll(posts, PostResponse.class);
        return ResponseEntity.ok(responses);
    }

    @Override
    public ResponseEntity<PostResponse> save(PostRequest postRequest) {
        var post = ModelMapperUtil.map(postRequest, Post.class);
        var postSaved = postService.savePost(post);
        var response = ModelMapperUtil.map(postSaved, PostResponse.class);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
