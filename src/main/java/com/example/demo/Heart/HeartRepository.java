package com.example.demo.Heart;

import com.example.demo.Member.Member;
import com.example.demo.Post.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HeartRepository extends JpaRepository<Heart, Long> {
    Optional<Heart> findByMemberAndPost(Member member, Post post);
    boolean existsByMemberAndPost(Member member, Post post);
    long countByPost(Post post);
}
