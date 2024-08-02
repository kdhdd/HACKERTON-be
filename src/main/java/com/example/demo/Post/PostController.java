package com.example.demo.Post;

import com.example.demo.auth.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    //게시글 작성
    @PostMapping
    public ResponseEntity<Post> createPost(@RequestHeader("Authorization") String token, @RequestBody PostDto postDto) {
        // "Bearer " 문자열 제거
        String jwtToken = token.substring(7);
        String email = jwtTokenUtil.getLoginId(jwtToken); // JWT에서 가져온 로그인 id
        Post post = postService.createPost(email, postDto);
        return ResponseEntity.ok(post);
    }
    // 게시글 수정
    @PatchMapping("/update/{id}")
    public ResponseEntity<Post> updatePost(@RequestHeader("Authorization") String token, @PathVariable Long id, @RequestBody PostDto postDto) {
        String jwtToken = token.substring(7);
        String email = jwtTokenUtil.getLoginId(jwtToken);
        // 게시글 업데이트
        Post updatedPost = postService.updatePost(email, id, postDto);
        return ResponseEntity.ok(updatedPost);
    }

    // 게시글 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@RequestHeader("Authorization") String token, @PathVariable Long id) {
        String jwtToken = token.substring(7);
        String email = jwtTokenUtil.getLoginId(jwtToken);
        // 게시글 삭제
        postService.deletePost(email, id);
        return ResponseEntity.noContent().build();
    }

    // 모든 게시글 조회
    @GetMapping
    public ResponseEntity<List<Post>> getAllPosts() {
        List<Post> posts = postService.getAllPosts();
        return ResponseEntity.ok(posts);
    }

    // 특정 게시글 조회
    @GetMapping("/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable Long id) {
        // PostService를 통해 특정 ID의 게시글 조회
        Post post = postService.getPostById(id);
        // 조회된 게시글이 있으면 OK 응답과 함께 게시글 반환, 없으면 404 Not Found 반환
        return post != null ? ResponseEntity.ok(post) : ResponseEntity.notFound().build();
    }
    // 닉네임으로 게시글 조회
    @GetMapping("/user/{nickname}")
    public ResponseEntity<List<PostDto>> getPostsByNickname(@PathVariable String nickname) {
        List<PostDto> posts = postService.getPostsByNickname(nickname);
        return ResponseEntity.ok(posts);
    }
}
