package com.Kelvin.Blog.service;

import com.Kelvin.Blog.data.dto.PostDto;
import com.Kelvin.Blog.data.model.Post;
import com.Kelvin.Blog.data.model.User;
import com.Kelvin.Blog.data.repository.PostRepository;
import com.Kelvin.Blog.service.mapper.PostMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;


import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class PostServiceImplMockTest {
    @Mock
    PostRepository postRepository;

    @Mock
    PostMapper postMapper;

    @Captor
    ArgumentCaptor<Post> postArgumentCaptor;

    @InjectMocks
    PostService postServiceImpl;

    @BeforeEach
    void setUp(){
        postServiceImpl = new PostServiceImpl();
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void savePostMockTest(){
        User user = new User();
        user.setFirstName("Kelvin");
        Post post = new Post();
        post.setPostTitle("Chemistry");
        //post.setAuthor(user);
        when(postRepository.save(post)).thenReturn(post);
        postServiceImpl.save(post);
        verify(postRepository,times(1)).save(post);
    }
    @Test
    void findPostById(){
        Post post = new Post();
        post.setPostId(1L);
        post.setPostTitle("Chemistry");
        when(postRepository.save(post)).thenReturn(post);
        postServiceImpl.save(post);
        verify(postRepository,times(1)).save(post);
        when(postRepository.findById(1L)).thenReturn(Optional.of(post));
        postServiceImpl.findPostById(1L);
        verify(postRepository,times(1)).findById(1L);
    }


    @Test
    void findPostByTitle(){
        Post post = new Post();
        post.setPostId(4L);
        post.setPostTitle("Chemistry Technology");
        Post post1 = new Post();
        post1.setPostId(5L);
        post1.setPostTitle("Chemistry Technology");
        when(postRepository.save(any(Post.class))).thenReturn(any(Post.class));
        postServiceImpl.save(post);
        postServiceImpl.save(post1);
        verify(postRepository,times(1)).save(post);
        verify(postRepository,times(1)).save(post1);
        when(postRepository.findByPostTitle("Chemistry Technology")).thenReturn(List.of(post, post1));
        postServiceImpl.findPostByTitle("Chemistry Technology");
        verify(postRepository,times(1)).findByPostTitle("Chemistry Technology");
    }

    @Test
    void updatePost(){
        User user = User.builder()
                .firstName("Kelvin")
                .lastName("Okoro")
                .email("kelvin@yahoo.com")
                .password("1234")
                .phoneNumber("08163091749").build();


        Post post = new Post();
        post.setPostId(4L);
        post.setPostTitle("Chemistry Technology");
        when(postRepository.save(any(Post.class))).thenReturn(any(Post.class));
        postServiceImpl.save(post);
        verify(postRepository,times(1)).save(post);


        PostDto postDto = new PostDto();
        postDto.setPostAuthorName(user.getFullName());
        postDto.setPostTitle("Chemistry Sociology");
        postDto.setPostBody("The sociology of science");

        when(postRepository.findById(4L)).thenReturn(Optional.of(post));


        when(postMapper.mapPostDtoToPost(postDto, post)).then(e -> {
            post.setPostTitle(postDto.getPostTitle());
            post.setPostBody(postDto.getPostBody());
            return null;
        });

        postServiceImpl.updatePost(4L,postDto);
        verify(postRepository,times(1)).findById(any());
        verify(postMapper, times(1)).mapPostDtoToPost(any(PostDto.class), any(Post.class));
        assertThat(post.getPostTitle()).isEqualTo(postDto.getPostTitle());
    }
    @Test
    void deletePost(){
        Post post = new Post();
        post.setPostId(1L);
        post.setPostTitle("Chemistry");
        post.setPostTitle("The Chemistry of Life");
        when(postRepository.save(post)).thenReturn(post);
        postServiceImpl.save(post);
        verify(postRepository,times(1)).save(post);
        when(postRepository.findById(1L)).thenReturn(Optional.of(post)).thenReturn(null);
        postServiceImpl.deletePost(1L);
        verify(postRepository,times(1)).delete(postArgumentCaptor.capture());
        Post postToDelete = postArgumentCaptor.getValue();
        assertThat(postToDelete.getPostTitle()).isEqualTo("The Chemistry of Life");

    }
    @Test
    void canSearchForPostViaAuthorFullName(){
        User user = User.builder()
                .firstName("Kelvin")
                .lastName("Tobi").build();
        Post post = new Post();
        post.setPostId(1L);
        post.setPostTitle("Chemistry");
        post.setAuthorFullName(user.getFullName());
        post.setPostTitle("The Chemistry of Life");
        when(postRepository.save(post)).thenReturn(post);
        when(postRepository.findByAuthorFullName("Kelvin Tobi")).thenReturn(List.of(post));
        List <Post> foundPosts = postServiceImpl.findPostByAuthorFullName("Kelvin Tobi");
        verify(postRepository,times(1)).findByAuthorFullName("Kelvin Tobi");
        assertEquals("Kelvin Tobi", post.getAuthorFullName());
        assertThat(foundPosts.size()).isEqualTo(1);
    }
}