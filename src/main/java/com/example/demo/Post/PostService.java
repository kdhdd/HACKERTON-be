package com.example.demo.Post;

import com.example.demo.Member.Member;
import com.example.demo.Member.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private MemberRepository memberRepository;

    // 게시글 작성
    public Post createPost(String loginId, PostDto postDto) {
        Member member = memberRepository.findByLoginId(loginId)
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));
        // 하루에 한 번만 글 작성할 수 있도록 제한
        if (member.getLastPostDate() != null && member.getLastPostDate().isEqual(LocalDate.now())) {
            throw new IllegalStateException("You can only post once a day");
        }

        Post post = new Post(member, postDto.getTitle(), postDto.getContent());
        member.setLastPostDate(LocalDate.now());
        memberRepository.save(member);

        return postRepository.save(post);
    }
    // 게시글 수정
    public Post updatePost(String loginId, Long postId, PostDto postDto) {
        Member member = memberRepository.findByLoginId(loginId)
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));

        // 작성자 본인 확인
        if (!post.getMember().equals(member)) {
            throw new IllegalStateException("You can only edit your own posts");
        }

        // 게시글 내용 수정
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        return postRepository.save(post);
    }
    // 게시글 삭제
    public void deletePost(String loginId, Long postId) {
        Member member = memberRepository.findByLoginId(loginId)
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));

        // 작성자 본인 확인
        if (!post.getMember().equals(member)) {
            throw new IllegalStateException("You can only delete your own posts");
        }

        // 게시글 삭제
        postRepository.delete(post);
    }
    // 모든 게시글 조회
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    // 특정 게시글 조회
    public Post getPostById(Long postId) {
        return postRepository.findById(postId).orElse(null);
    }
}