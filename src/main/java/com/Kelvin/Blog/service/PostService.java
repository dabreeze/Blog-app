package com.Kelvin.Blog.service;

import com.Kelvin.Blog.data.dto.PostDto;
import com.Kelvin.Blog.data.model.Post;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public interface PostService {
    Post save(Post post);

    Post findPostById(Long postId);

    List<Post> findPostByTitle(String postTitle);

    Post updatePost(Long postId, PostDto postDto);

    void deletePost(Long postId);

    List<Post> findPostByAuthorFullName(String authorFullName);
}
