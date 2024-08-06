package com.example.demo.Post;

import com.example.demo.Comment.Comment;
import com.example.demo.Member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    //회원 탈퇴시 기존 글들 삭제
    List<Post> findByMember(Member member);
    long countByMember(Member member);
    //닉네임으로 게시글 찾기 (최신순 정렬)
    List<Post> findByMemberNicknameOrderByCreatedAtDesc(String nickname);

    // 모든 게시글 최신순 정렬
    @Query("SELECT p FROM Post p ORDER BY p.createdAt DESC")
    List<Post> findAllOrderByCreatedAtDesc();
}