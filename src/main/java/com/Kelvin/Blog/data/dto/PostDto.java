package com.Kelvin.Blog.data.dto;

import lombok.Data;

import java.util.List;

@Data
public class PostDto {
    private String postTitle;
    private String postBody;
    private String postAuthorName;
    private List<String> imageUrls;


}
