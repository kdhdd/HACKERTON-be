package com.example.demo.Member;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberDto {
    //private Long id;
    private String nickname;
    //private String loginId;
    private String password;
    private boolean today;

    public static MemberDto from(Member member) {
        MemberDto memberDto = new MemberDto();
        //memberDto.setId(member.getId());
        memberDto.setPassword(member.getPassword());
        memberDto.setNickname(member.getNickname());
        //memberDto.setLoginId(member.getLoginId());
        memberDto.setToday(member.isToday());
        return memberDto;
    }
}
