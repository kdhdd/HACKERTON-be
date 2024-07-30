package com.example.demo.Post;

import com.example.demo.auth.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @PostMapping
    public ResponseEntity<Post> createPost(@RequestHeader("Authorization") String token, @RequestBody PostDto postDto) {
        // "Bearer " 문자열 제거
        String jwtToken = token.substring(7);
        String email = jwtTokenUtil.getLoginId(jwtToken); // JWT에서 가져온 로그인 id
        Post post = postService.createPost(email, postDto);
        return ResponseEntity.ok(post);
    }
}
