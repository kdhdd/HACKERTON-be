package com.example.demo.Post;

import com.example.demo.Member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    long countByMember(Member member);

    //닉네임으로 게시글 찾기
    List<Post> findByMemberNickname(String nickname);
}