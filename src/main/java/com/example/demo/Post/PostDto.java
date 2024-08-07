package com.example.demo.Post;

import lombok.Getter;
import lombok.Setter;

//@Getter
//@Setter
//public class PostDto {
//    private Long id; // 게시글 ID
////    private String title;
//    private String content;
//    private String memberNickname; // 회원 닉네임
//
//    public static PostDto fromPost(Post post) {
//        PostDto postDto = new PostDto();
//        postDto.setId(post.getId());
//        postDto.content = post.getContent();
//        postDto.setMemberNickname(post.getMember().getNickname());
//        return postDto;
//    }
//}
@Getter
@Setter
public class PostDto {
    private Long id;
    private String content;
    private String memberNickname; // 회원 닉네임
    private int commentCount;

    public static PostDto fromPost(Post post) {
        PostDto postDto = new PostDto();
        postDto.setId(post.getId());
        postDto.setContent(post.getContent());
        postDto.setMemberNickname(post.getMember().getNickname()); // 닉네임 설정
        return postDto;
    }
}
