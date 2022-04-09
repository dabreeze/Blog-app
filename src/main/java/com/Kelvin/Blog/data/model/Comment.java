package com.Kelvin.Blog.data.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import javax.persistence.*;
import java.util.Date;


@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(nullable = false,length=500)
    private String body;

    @Column(nullable = false)
    private String creatorName;

    @ManyToOne
    private Post post;

    @CreationTimestamp
    private Date dateOfComment;


    public Comment(String name,String commentBody){
        this.creatorName = name;
        this.body=commentBody;
    }

}
