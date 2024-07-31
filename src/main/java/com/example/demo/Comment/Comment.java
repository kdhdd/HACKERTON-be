package com.example.demo.Comment;

import com.example.demo.Member.Member;
import com.example.demo.Post.Post;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    @JsonBackReference
    private Post post;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    @JsonBackReference
    private Member member;

    @Column(nullable = false)
    private String content;

    private LocalDateTime createdAt;

    public Comment() {
    }

    public Comment(Post post, Member member, String content) {
        this.post = post;
        this.member = member;
        this.content = content;
        this.createdAt = LocalDateTime.now();
    }
}
