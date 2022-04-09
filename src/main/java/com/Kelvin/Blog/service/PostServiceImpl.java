package com.Kelvin.Blog.service;

import com.Kelvin.Blog.data.dto.PostDto;
import com.Kelvin.Blog.data.model.Post;
import com.Kelvin.Blog.data.repository.PostRepository;
import com.Kelvin.Blog.data.repository.UserRepository;
import com.Kelvin.Blog.service.mapper.PostMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;


    @Override
    public Post save(Post post) {
        return postRepository.save(post);
    }

    @Autowired
    PostMapper postMapper;

    @Override
    public Post findPostById(Long postId) {
        Optional<Post> foundPost = postRepository.findById(postId);
        if (foundPost.isPresent()) {
            return foundPost.get();
        } else throw new NullPointerException("This post with " + postId + " Id, does not exist or not found ");
    }

    @Override
    public List<Post> findPostByTitle(String postTitle) {
        List<Post> allPost = postRepository.findByPostTitle(postTitle);
        if (allPost != null) {
            return allPost;
        } else throw new NullPointerException("This post with " + postTitle + "does not exist or is  not found");
    }

    @Override
    public Post updatePost(Long postId, PostDto postDto) {
        if (postDto == null) {
            throw new NullPointerException("This post can't be null");
        }
        Optional<Post> foundPostInRepo = postRepository.findById(postId);

        if (foundPostInRepo.isPresent()) {
            Post existingPost = foundPostInRepo.get();
            postMapper.mapPostDtoToPost(postDto, existingPost);
            return postRepository.save(existingPost);
        } else {
            throw new IllegalArgumentException("Post does not exist");
        }

    }

    @Override
    public void deletePost(Long postId) {
        Optional<Post> foundPostInRepo = postRepository.findById(postId);
        if (foundPostInRepo.isPresent()) {
            Post foundPost = foundPostInRepo.get();
            postRepository.delete(foundPost);
        } else throw new NullPointerException("Post id with " + postId + " is not found");
    }

    @Override
    public List<Post> findPostByAuthorFullName(String authorFullName) {
        List <Post> posts = postRepository.findByAuthorFullName(authorFullName);
        for(Post post:posts){
            if(post.getAuthorFullName().equals(authorFullName)){
                return List.of(post);
            }
        }
        throw new NullPointerException("This post by "+authorFullName+" does not exist");
    }

}