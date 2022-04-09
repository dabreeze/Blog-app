package com.Kelvin.Blog.data.repository;
import com.Kelvin.Blog.data.model.Post;
import com.Kelvin.Blog.data.model.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;
import java.util.List;


import static org.assertj.core.api.Assertions.assertThat;
@SpringBootTest
@Slf4j
class PostRepositoryTest {

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CommentRepository commentRepository;

    @BeforeEach
    void setUp() {
    }
    @Test
    @Rollback(value=false)
    public void createAndSavePost(){
        User user1 = User.builder()
                .firstName("Kelvin")
                .lastName("Okoro")
                .email("kelvin@yahoo.com")
                .password("1234")
                .phoneNumber("08163091749").build();

        User user2 = User.builder()
                .firstName("John")
                .lastName("Bush")
                .email("okoro@yahoo.com")
                .password("1234")
                .phoneNumber("08175878390").build();

        User user3 = User.builder()
                .firstName("Tolani")
                .lastName("Eze")
                .email("nike@yahoo.com")
                .password("1234")
                .phoneNumber("08163565267").build();

       userRepository.save(user1);
       userRepository.save(user2);
       userRepository.save(user3);
       userRepository.save(user1);

       Post post1 = new Post("Chemistry","The study of chemistry of Life",
               List.of("www.image.com","www.css.com"),user1.getFullName());

        Post post2 = new Post("Biology Science","Bio comes from the world Life," +
                "Logos comes from the word study. Therefore Biology is the Study of Life",
                List.of("www.college.com","www.Buss.com"),user2.getFullName());

        Post post3 = new Post("Chemistry technology","Bio comes from the world Life,Logos comes from the" +
                "word study. Therefore Biology is the Study of Life",null,user3.getFullName());

       postRepository.save(post1);
       postRepository.save(post2);
       postRepository.save(post3);

       assertThat(post1.getAuthorFullName()).isEqualTo("Kelvin Okoro");
       assertThat(post2.getAuthorFullName()).isEqualTo("John Bush");
       assertThat(post3.getAuthorFullName()).isEqualTo("Tolani Eze");
    }

    @Test
    public void canFindPostById(){
       Post foundPost =  postRepository.findById(1L).orElse(null);
       assertThat(foundPost).isNotNull();
       assertThat(foundPost.getAuthorFullName()).isEqualTo("Kelvin Okoro");
    }

    @Test
    public void canDeletePostById(){
        Post foundPost = postRepository.findById(1L).orElse(null);
        assertThat(foundPost).isNotNull();
        postRepository.delete(foundPost);
        Post deletedPost = postRepository.findById(1L).orElse(null);
        assertThat(deletedPost).isNull();
    }

    @Test
    @Transactional
    public void findPostByTitles(){
        List <Post> allPost = postRepository.findByPostTitle("Chemistry technology");
        log.info("List of post by title -> {}", allPost.toString());
        assertThat(allPost.size()).isNotNull();
        assertThat(allPost.size()).isEqualTo(1);
    }

    @Test
    @Transactional
    public void findPostByAuthorFullNames(){
        List <Post> post = postRepository.findByAuthorFullName("John Bush");
        for(Post pt : post){
            log.info("List of post by title -> {}", pt.toString());
        }
        assertThat(post).isNotNull();
    }
}