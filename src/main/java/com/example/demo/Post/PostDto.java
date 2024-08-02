package com.example.demo.Post;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostDto {
    private Long id; // 게시글 ID
    private String content;
    private String memberNickname; // 회원 닉네임
    private int hearts;

    public static PostDto fromPost(Post post) {
        PostDto postDto = new PostDto();
        postDto.setId(post.getId());
        postDto.setMemberNickname(post.getMember().getNickname());
        postDto.setContent(post.getContent());
        postDto.setHearts(post.getHearts());
        return postDto;
    }
}