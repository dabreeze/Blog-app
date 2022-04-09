package com.Kelvin.Blog.data.repository;

import com.Kelvin.Blog.data.dto.PostDto;
import com.Kelvin.Blog.data.model.Comment;
import com.Kelvin.Blog.data.model.Post;
import com.Kelvin.Blog.data.model.User;
import com.Kelvin.Blog.service.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.StatusResultMatchersExtensionsKt.isEqualTo;

@Slf4j
@SpringBootTest
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Autowired
    private CommentRepository commentRepository;

    @Test
    void searchForUserByFirstNameAndLastName(){
        List<User> users = userRepository.findUserByFirstNameAndLastName("Kelvin", "Okoro");
        for (User user: users) {
            log.info(user.toString());
        }
    }

    @Test
    void userCanBeCreatedAndSaved(){

        User user1 = User.builder()
                .firstName("Steve")
                .lastName("Jobs")
                .email("Stevejobs@yahoo.com")
                .password("123$%^")
                .phoneNumber("08163091749").build();


        User user2 = User.builder()
                .firstName("Isaac")
                .lastName("Newton")
                .email("Isaacnewton@yahoo.com")
                .password("223$%^")
                .phoneNumber("08463091749").build();


        User user3 = User.builder()
                .firstName("Bill")
                .lastName("Gate")
                .email("Billgate@yahoo.com")
                .password("423$%^")
                .phoneNumber("08153091749").build();

        User savedUser1 = userRepository.save(user1);
        User savedUser2 = userRepository.save(user2);
        User savedUser3 = userRepository.save(user3);

        assertThat(savedUser1).isNotNull();
        assertThat(savedUser2).isNotNull();
        assertThat(savedUser3).isNotNull();
    }

    @Test
    @Transactional
    @Rollback(value = false)
    void userCanCommentOnPost(){
        User user1 = User.builder()
                .firstName("Steve")
                .lastName("Jobs")
                .email("Stevejobs@yahoo.com")
                .password("123$%^")
                .posts(new ArrayList<Post>())
                .phoneNumber("08163091749").build();
        userServiceImpl.createAndSaveUser(user1);
      PostDto post = new PostDto();
        post.setPostTitle("Biochemistry");
        post.setPostAuthorName(user1.getFullName());
        post.setPostBody("Biochemistry is the study of Life and its physical environment");
        post.setImageUrls(List.of("www.biochemsitry.com","www.freshyo.com")
        );
        userServiceImpl.createPost(1L,post);
        Comment comment1 = new Comment("Okoro","The view of images are bad");
        userServiceImpl.makeComment(1L, comment1);
        assertThat(user1.getPosts().size()).isEqualTo(1);
        assertThat(user1.getPosts().get(0).getComments()).isNotEmpty();
        assertThat(user1.getPosts().get(0).getComments().get(0).getId()).isEqualTo(comment1.getId());
        ;
    }


}