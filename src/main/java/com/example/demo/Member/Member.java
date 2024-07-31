package com.example.demo.Member;

import com.example.demo.Comment.Comment;
import com.example.demo.Post.Post;
import com.example.demo.UserRole;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nickname;
    private String loginId;
    private String password;

    private UserRole role;

    @Builder.Default
    private boolean today = false;

    @CreationTimestamp
    private LocalDateTime registerDate;

    @OneToMany(mappedBy = "member")
    @JsonManagedReference
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    @JsonBackReference
    private List<Comment> comments = new ArrayList<>();
}
