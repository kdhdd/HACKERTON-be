package com.example.demo.Post;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostDto {
    private Long id; // 게시글 ID
    private String title;
    private String content;

    public static PostDto fromPost(Post post) {
        PostDto postDto = new PostDto();
        postDto.setId(post.getId());
        postDto.title = post.getTitle();
        postDto.content = post.getContent();
        return postDto;
    }
}