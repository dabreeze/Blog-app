package com.Kelvin.Blog.data.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;


    @Column(nullable = false,
            length=100)
    private String postTitle;

    @Column(nullable=false,
            length = 1000)
    private String postBody;

    @ElementCollection
    private List<String> imageUrls;

    @CreationTimestamp
    private LocalDateTime dateCreated;

    @Column(nullable= false, length=100)
    private String authorFullName;

    @OneToMany
    private List<Comment> comments;

    public Post(String postTitle, String postBody, List<String> imageUrls, String authorFullName) {
        this.postTitle = postTitle;
        this.postBody = postBody;
        this.imageUrls = imageUrls;
        this.authorFullName = authorFullName;
        this.comments=new ArrayList<Comment>();
    }

    public Post(String postTitle, String postBody, String authorFullName) {
        this.postTitle = postTitle;
        this.postBody = postBody;
        this.authorFullName = authorFullName;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void addAllComment(Comment comment){
        getComments().add(comment);
    }
}
