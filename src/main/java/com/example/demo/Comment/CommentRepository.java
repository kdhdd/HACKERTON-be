package com.example.demo.Comment;

import com.example.demo.Member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    long countByMember(Member member);
    List<Comment> findByMemberNickname(String nickname);
}
