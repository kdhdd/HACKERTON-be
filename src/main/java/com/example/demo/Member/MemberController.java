package com.example.demo.Member;

import com.example.demo.request.JoinRequest;
import com.example.demo.request.LoginRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private static final Logger logger = LoggerFactory.getLogger(MemberController.class);

    @PostMapping("/join")
    public ResponseEntity<String> join(@RequestBody JoinRequest joinRequest) {
        if (memberService.checkLoginIdDuplicate(joinRequest.getLoginId())) {
            return ResponseEntity.badRequest().body("로그인 아이디가 중복됩니다.");
        }
        if (memberService.checkNicknameDuplicate(joinRequest.getNickname())) {
            return ResponseEntity.badRequest().body("닉네임이 중복됩니다.");
        }
        if (!joinRequest.getPassword().equals(joinRequest.getPasswordCheck())) {
            return ResponseEntity.badRequest().body("비밀번호가 일치하지 않습니다.");
        }

        memberService.join(joinRequest);
        return ResponseEntity.ok("회원가입 성공");
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody LoginRequest loginRequest) {
        Map<String, Object> response = memberService.login(loginRequest);

        if (response == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "로그인 아이디 또는 비밀번호가 틀렸습니다."));
        }

        return ResponseEntity.ok(response);
    }

    @GetMapping("/me")
    public ResponseEntity<String> getMyUserInfo(Authentication authentication) {
        String loginId = authentication.getName();
        Member loginUser = memberService.getLoginUserByLoginId(loginId);
        if (loginUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인된 사용자가 아닙니다.");
        }
        String userInfo = String.format("loginId : %s\nnickname : %s\nrole : %s",
                loginUser.getLoginId(), loginUser.getNickname(), loginUser.getRole().name());
        return ResponseEntity.ok(userInfo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMember(@PathVariable Long id) {
        memberService.deleteMember(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/update")
    public ResponseEntity<MemberDto> updateMember(@RequestHeader(value = "Authorization", required = false) String token, @RequestBody MemberDto memberDto) {
        if (token == null || !token.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        try {
            token = token.substring(7); // Remove "Bearer " prefix
            Member updatedMember = memberService.updateMember(token, memberDto);
            return ResponseEntity.ok(MemberDto.from(updatedMember));
        } catch (RuntimeException e) {
            logger.error("Error updating member", e);
            return ResponseEntity.badRequest().body(null);
        }
    }
    @GetMapping("/me/posts/count")
    public ResponseEntity<Long> countMyPosts(Authentication authentication) {
        String loginId = authentication.getName();
        long count = memberService.countUserPosts(loginId);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/me/comments/count")
    public ResponseEntity<Long> countMyComments(Authentication authentication) {
        String loginId = authentication.getName();
        long count = memberService.countUserComments(loginId);
        return ResponseEntity.ok(count);
    }
}
