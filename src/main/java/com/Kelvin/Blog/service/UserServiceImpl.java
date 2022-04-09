package com.Kelvin.Blog.service;

import com.Kelvin.Blog.data.dto.PostDto;
import com.Kelvin.Blog.data.model.Comment;
import com.Kelvin.Blog.data.model.Post;
import com.Kelvin.Blog.data.model.User;
import com.Kelvin.Blog.data.repository.CommentRepository;
import com.Kelvin.Blog.data.repository.UserRepository;
import com.Kelvin.Blog.service.mapper.PostMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;


@Service
public class UserServiceImpl implements UserService{

    UserRepository userRepository;
    PostService postServiceImpl;
    PostMapper postMapper;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PostService postServiceImpl,PostMapper postMapper) {
        this.userRepository = userRepository;
        this.postServiceImpl = postServiceImpl;
        this.postMapper= postMapper;
        //this.commentRepository = commentRepository;
    }

    @Override
    public User createAndSaveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public Post createPost(Long userId, PostDto postDto) {
        Optional<User> foundUserRepo = userRepository.findById(userId);
        if(foundUserRepo.isPresent()){
            Post post = new Post(postDto.getPostTitle(),
                    postDto.getPostBody(),postDto.getImageUrls(),
                    foundUserRepo.get().getFullName());
            User user = foundUserRepo.get();
            user.addPost(post);
            return postServiceImpl.save(post);
        }
        throw new IllegalArgumentException("This user with Id " +userId+" is not registered");
    }

    @Override
    public Post updatePost(Long userId, Long postId, PostDto toUpDatePostDto) {
        Optional <User> userInRepo = userRepository.findById(userId);
        if(userInRepo.isPresent()){
            return postServiceImpl.updatePost(postId,toUpDatePostDto);
            }
        throw new IllegalArgumentException("This user does not exist");
        }

    @Override
    public Comment makeComment(Long postId, Comment comment) {
        Post post = postServiceImpl.findPostById(postId);
        if(post!=null){
            post.addAllComment(comment);
            return commentRepository.save(comment);
        }
        return null;
    }
}
