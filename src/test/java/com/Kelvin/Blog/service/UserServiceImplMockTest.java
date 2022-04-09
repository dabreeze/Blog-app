package com.Kelvin.Blog.service;

import com.Kelvin.Blog.data.dto.PostDto;
import com.Kelvin.Blog.data.model.Comment;
import com.Kelvin.Blog.data.model.Post;
import com.Kelvin.Blog.data.model.User;
import com.Kelvin.Blog.data.repository.PostRepository;
import com.Kelvin.Blog.data.repository.UserRepository;
import com.Kelvin.Blog.service.mapper.PostMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;


class UserServiceImplMockTest {

    @Mock
    UserRepository userRepository;

    @Mock
    PostRepository postRepository;

    @Mock
    PostMapper postMapper;

    @InjectMocks
    UserServiceImpl userServiceImpl;
    @InjectMocks
    PostServiceImpl postServiceImpl;

    @BeforeEach
    void setup(){
        postServiceImpl = new PostServiceImpl();
        userServiceImpl = new UserServiceImpl(userRepository, postServiceImpl, postMapper);
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void canCreateAndSaveUser(){
        User user1 = User.builder()
                .firstName("Steve")
                .lastName("Jobs")
                .email("Stevejobs@yahoo.com")
                .password("123$%^")
                .phoneNumber("08163091749").build();

        when(userRepository.save(user1)).thenReturn(user1);
        userServiceImpl.createAndSaveUser(user1);
        verify(userRepository, times(1)).save(user1);
    }
    @Test
    void userCanCreatePost(){
        User user1 = User.builder()
                .userId(1L)
                .firstName("Steve")
                .lastName("Jobs")
                .email("Stevejobs@yahoo.com")
                .password("123$%^")
                .posts(new ArrayList<Post>())
                .phoneNumber("08163091749").build();

        PostDto postDto = new PostDto();
        postDto.setPostTitle("Chemistry Sociology");
        postDto.setPostBody("The sociology of science");
        postDto.setPostAuthorName(user1.getFullName());
        postDto.setImageUrls(List.of("www.java.com","www.divine.com"));


        Post post1 = new Post(postDto.getPostTitle(),postDto.getPostBody(),
                postDto.getImageUrls(),postDto.getPostAuthorName());


        when(userRepository.save(user1)).thenReturn(user1);
        userServiceImpl.createAndSaveUser(user1);
        verify(userRepository, times(1)).save(user1);

        when(userRepository.findById(user1.getUserId())).thenReturn(Optional.of(user1));
        when(postRepository.save(post1)).thenReturn(post1);
        userServiceImpl.createPost(user1.getUserId(),postDto);
        verify(userRepository,times(1)).findById(user1.getUserId());
        verify(postRepository,times(1)).save(post1);
    }

    @Test
    void userCanUpdatePostViaPostId(){
        User user1 = User.builder()
                .userId(1L)
                .firstName("Kelvin")
                .lastName("Okoro")
                .email("kelvin@yahoo.com")
                .password("1234")
                .phoneNumber("08163091749")
                .posts(new ArrayList<Post>()).build();

        PostDto toUpDatePostDto = new PostDto();
        toUpDatePostDto.setPostTitle("Chemistry Sociology");
        toUpDatePostDto.setPostBody("The sociology of science");
        toUpDatePostDto.setImageUrls(List.of("www.java.com","www.divine.com"));

        PostDto userPostDto = new PostDto();
        userPostDto.setPostTitle("Biology");
        userPostDto.setPostBody("Biology is the study of Life and its Environment");
        userPostDto.setPostAuthorName(user1.getFullName());
        userPostDto.setImageUrls(List.of("www.javelin.com","www.globalview.com"));

        Post post = new Post();
        post.setPostId(1L);
        post.setPostTitle(userPostDto.getPostTitle());
        post.setPostBody(userPostDto.getPostBody());
        post.setAuthorFullName(user1.getFullName());
        post.setImageUrls(userPostDto.getImageUrls());


        when(userRepository.save(user1)).thenReturn(user1);
        userServiceImpl.createAndSaveUser(user1);
        verify(userRepository, times(1)).save(user1);

        when(userRepository.findById(user1.getUserId())).thenReturn(Optional.of(user1));
        when(postRepository.save(post)).thenReturn(post);


        userServiceImpl.createPost(user1.getUserId(),userPostDto);
        verify(userRepository,times(1)).findById(user1.getUserId());
        verify(postRepository,times(1)).save(any(Post.class));

        when(userRepository.findById(1L)).thenReturn(Optional.of(user1));
        when(postRepository.findById(1L)).thenReturn(Optional.of(post));
        when(postMapper.mapPostDtoToPost(toUpDatePostDto,post)).then(e -> {
            post.setPostTitle(toUpDatePostDto.getPostTitle());
            post.setPostBody(toUpDatePostDto.getPostBody());
            return null;
        });
        userServiceImpl.updatePost(1L,1L,toUpDatePostDto);
        verify(postRepository,times(1)).findById(any());
        verify(postMapper, times(1)).mapPostDtoToPost(any(PostDto.class), any(Post.class));
        assertThat(post.getPostTitle()).isEqualTo(toUpDatePostDto.getPostTitle());
    }
    @Test
    void userCanCommentOnPost(){


    }

}