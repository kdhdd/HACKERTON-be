//package com.example.demo.Heart;
//
//import com.example.demo.Member.Member;
//import com.example.demo.Post.Post;
//import jakarta.persistence.*;
//import lombok.Getter;
//import lombok.Setter;
//
//@Entity
//@Getter
//@Setter
//@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"member_id", "post_id"})})
//public class Heart {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @ManyToOne
//    @JoinColumn(name = "member_id", nullable = false)
//    private Member member;
//
//    @ManyToOne
//    @JoinColumn(name = "post_id", nullable = false)
//    private Post post;
//
//    public Heart() {
//    }
//
//    public Heart(Member member, Post post) {
//        this.member = member;
//        this.post = post;
//    }
//}
