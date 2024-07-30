package com.example.demo.Post;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostDto {
    private String title;
    private String content;

    public static PostDto fromPost(Post post) {
        PostDto postDto = new PostDto();
        postDto.title = post.getTitle();
        postDto.content = post.getContent();
        return postDto;
    }
}