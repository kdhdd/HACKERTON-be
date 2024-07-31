package com.example.demo.Member;

import com.example.demo.Comment.CommentRepository;
import com.example.demo.Post.PostRepository;
import com.example.demo.auth.JwtTokenUtil;
import com.example.demo.request.JoinRequest;
import com.example.demo.request.LoginRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final BCryptPasswordEncoder encoder;
    private final JwtTokenUtil jwtTokenUtil;

    public boolean checkLoginIdDuplicate(String loginId) {
        return memberRepository.existsByLoginId(loginId);
    }

    public boolean checkNicknameDuplicate(String nickname) {
        return memberRepository.existsByNickname(nickname);
    }

    @Transactional
    public void join(JoinRequest req) {
        memberRepository.save(req.toEntity(encoder.encode(req.getPassword())));
    }

    @Transactional
    public void deleteMember(Long id) {
        memberRepository.deleteById(id);
    }

    public Map<String, Object> login(LoginRequest req) {
        Optional<Member> optionalMember = memberRepository.findByLoginId(req.getLoginId());

        if (optionalMember.isEmpty()) {
            return null;
        }

        Member member = optionalMember.get();
        if (encoder.matches(req.getPassword(), member.getPassword())) {
            String token = jwtTokenUtil.createToken(member.getLoginId());

            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("member", member);
            return response;
        }

        return null;
    }

    public Member getLoginUserById(Long memberId) {
        if (memberId == null) return null;

        Optional<Member> optionalMember = memberRepository.findById(memberId);
        if (optionalMember.isEmpty()) return null;

        return optionalMember.get();
    }

    public Member getLoginUserByLoginId(String loginId) {
        if (loginId == null) return null;

        Optional<Member> optionalMember = memberRepository.findByLoginId(loginId);
        if (optionalMember.isEmpty()) return null;

        return optionalMember.get();
    }

    @Transactional
    public Member updateMember(String token, MemberDto memberDto) {
        String loginId = jwtTokenUtil.getLoginId(token);
        Member member = getLoginUserByLoginId(loginId);
        if (member != null) {
            if (memberDto.getNickname() != null) {
                member.setNickname(memberDto.getNickname());
            }
            /*if (memberDto.getLoginId() != null) {
                member.setLoginId(memberDto.getLoginId());
            }*/
            if (memberDto.getPassword() != null) {
                member.setPassword(encoder.encode(memberDto.getPassword()));
            }
            if (memberDto.isToday() != member.isToday()) {
                member.setToday(memberDto.isToday());
            }
            return memberRepository.save(member);
        } else {
            throw new RuntimeException("Member not found with loginId " + loginId);
        }
    }
    //유저 개시글 갯수 세기
    public long countUserPosts(String loginId) {
        Member member = memberRepository.findByLoginId(loginId)
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));
        return postRepository.countByMember(member);
    }

    //유저 댓글 갯수 세기
    public long countUserComments(String loginId) {
        Member member = memberRepository.findByLoginId(loginId)
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));
        return commentRepository.countByMember(member);
    }
}
