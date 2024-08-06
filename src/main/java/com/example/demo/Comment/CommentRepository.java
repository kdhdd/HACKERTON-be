package com.example.demo.Comment;

import com.example.demo.Member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    //회원탈퇴시 기존 댓글 삭제
    List<Comment> findByMember(Member member);
    long countByMember(Member member);
    List<Comment> findByMemberNickname(String nickname);
    int countByPostId(long postId);
    // 닉네임으로 댓글 조회 (최신순 정렬)
    List<Comment> findByMemberNicknameOrderByCreatedAtDesc(String nickname);
}
