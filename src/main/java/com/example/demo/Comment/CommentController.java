package com.example.demo.Comment;

import com.example.demo.auth.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    // 댓글 작성
    @PostMapping("/{postId}")
    public ResponseEntity<CommentDto> createComment(@RequestHeader("Authorization") String token, @PathVariable Long postId, @RequestBody CommentDto commentDto) {
        String jwtToken = token.substring(7); // "Bearer " 문자열 제거
        String loginId = jwtTokenUtil.getLoginId(jwtToken); // JWT에서 가져온 로그인 id
        CommentDto savedComment = commentService.createComment(loginId, postId, commentDto);
        return ResponseEntity.ok(savedComment);
    }

    // 댓글 수정
    @PatchMapping("/update/{commentId}")
    public ResponseEntity<CommentDto> updateComment(@RequestHeader("Authorization") String token, @PathVariable Long commentId, @RequestBody CommentDto commentDto){
        String jwtToken = token.substring(7);
        String loginId = jwtTokenUtil.getLoginId(jwtToken);
        CommentDto updatedComment = commentService.updateComment(loginId, commentId, commentDto);
        return ResponseEntity.ok(updatedComment);
    }

    // 댓글 삭제
    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@RequestHeader("Authorization") String token, @PathVariable Long commentId) {
        String jwtToken = token.substring(7);
        String loginId = jwtTokenUtil.getLoginId(jwtToken);
        commentService.deleteComment(loginId, commentId);
        return ResponseEntity.noContent().build();
    }

    // 특정 게시글의 모든 댓글 조회
    @GetMapping("/post/{postId}")
    public ResponseEntity<List<CommentDto>> getAllCommentsForPost(@PathVariable Long postId) {
        List<CommentDto> comments = commentService.getAllCommentsForPost(postId);
        return ResponseEntity.ok(comments);
    }

    // 특정 댓글 조회
    @GetMapping("/{commentId}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable Long commentId) {
        CommentDto comment = commentService.getCommentById(commentId);
        return comment != null ? ResponseEntity.ok(comment) : ResponseEntity.notFound().build();
    }
    // 닉네임으로 댓글 조회
    @GetMapping("/user/{nickname}")
    public ResponseEntity<List<CommentDto>> getCommentsByNickname(@PathVariable String nickname) {
        List<CommentDto> comments = commentService.getCommentsByNickname(nickname);
        return ResponseEntity.ok(comments);
    }
}
