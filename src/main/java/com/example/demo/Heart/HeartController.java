package com.example.demo.Heart;


import com.example.demo.auth.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/hearts")
@RequiredArgsConstructor
public class HeartController {
    private final HeartService heartService;
    private final JwtTokenUtil jwtTokenUtil;

    // 하트 추가
    @PostMapping("/{postId}")
    public ResponseEntity<Heart> addHeart(@RequestHeader("Authorization") String token, @PathVariable Long postId) {
        String jwtToken = token.substring(7); // "Bearer " 문자열 제거
        String loginId = jwtTokenUtil.getLoginId(jwtToken);
        heartService.addHeart(loginId, postId);
        return ResponseEntity.ok().build();
    }

    // 하트 삭제
    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> removeHeart(@RequestHeader("Authorization") String token, @PathVariable Long postId) {
        String jwtToken = token.substring(7); // "Bearer " 문자열 제거
        String loginId = jwtTokenUtil.getLoginId(jwtToken);
        heartService.removeHeart(loginId, postId);
        return ResponseEntity.noContent().build();
    }

    // 특정 게시글의 하트 개수 조회
    @GetMapping("/{postId}/count")
    public ResponseEntity<Long> countHeartsByPost(@PathVariable Long postId) {
        long count = heartService.countHeartsByPost(postId);
        return ResponseEntity.ok(count);
    }
}
