package com.example.demo.Comment;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDto {
    private Long id;
    private String comment;
    private String nickname;

    public static CommentDto fromComment(Comment comment) {
        CommentDto commentDto = new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setComment(comment.getContent());
        commentDto.setNickname(comment.getMember().getNickname());
        return commentDto;
    }
}