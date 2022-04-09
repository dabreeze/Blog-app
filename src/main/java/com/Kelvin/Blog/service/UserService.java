package com.Kelvin.Blog.service;

import com.Kelvin.Blog.data.dto.PostDto;
import com.Kelvin.Blog.data.model.Comment;
import com.Kelvin.Blog.data.model.Post;
import com.Kelvin.Blog.data.model.User;
import org.springframework.stereotype.Service;


@Service
public interface UserService {
    User createAndSaveUser(User user);

    Post createPost(Long userId, PostDto postDto);

    Post updatePost(Long userId, Long postId, PostDto toUpDatePostDto);

    Comment makeComment(Long postId, Comment comment);
}
