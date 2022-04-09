package com.Kelvin.Blog.data.repository;

import com.Kelvin.Blog.data.model.Comment;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class CommentRepositoryTest {
    @Autowired
    CommentRepository commentRepository;

    @Test
    void createComment(){
        Comment comment = new Comment("Kelvin","Excellent Post,but you can improve in your write");
        commentRepository.save(comment);

    }
    @Test
    void canDeleteComment(){
//        Comment comment2 = new Comment("Okoro","The view of images are bad");
//        Comment comment3 = new Comment("Tolu","Biology is beyond your definition");
//        Comment comment4 = new Comment("Shola","The Expectation of Life is much. I like your Writeup");
//        commentRepository.saveAll(List.of(comment2,comment3,comment4));
    }
}