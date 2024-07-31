package com.example.demo.Heart;

import com.example.demo.Member.Member;
import com.example.demo.Member.MemberRepository;
import com.example.demo.Post.Post;
import com.example.demo.Post.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HeartService {
    private final HeartRepository heartRepository;
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;

    // 하트 추가
    public void addHeart(String loginId, Long postId) {
        Member member = memberRepository.findByLoginId(loginId)
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));

        // 유저가 이미 하트를 눌렀는지 확인
        if (heartRepository.existsByMemberAndPost(member, post)) {
            throw new IllegalStateException("You can only like a post once");
        }

        Heart heart = new Heart();
        heart.setMember(member);
        heart.setPost(post);
        heartRepository.save(heart);
    }

    // 하트 삭제
    public void removeHeart(String loginId, Long postId) {
        Member member = memberRepository.findByLoginId(loginId)
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));

        Heart heart = heartRepository.findByMemberAndPost(member, post)
                .orElseThrow(() -> new IllegalArgumentException("Heart not found"));

        heartRepository.delete(heart);
    }

    // 특정 게시글의 하트 개수 조회
    public long countHeartsByPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));
        return heartRepository.countByPost(post);
    }
}
