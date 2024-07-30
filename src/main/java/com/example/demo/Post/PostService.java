package com.example.demo.Post;

import com.example.demo.Member.Member;
import com.example.demo.Member.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private MemberRepository memberRepository;

    public Post createPost(String loginId, PostDto postDto) {
        Member member = memberRepository.findByLoginId(loginId)
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));

        Post post = new Post(member, postDto.getTitle(), postDto.getContent());
        return postRepository.save(post);
    }
}