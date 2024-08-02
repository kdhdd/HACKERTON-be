//package com.example.demo.Scheduler;
//
//import com.example.demo.Member.Member;
//import com.example.demo.Member.MemberService;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//@RequiredArgsConstructor
//@Slf4j
//public class RankingScheduler {
//
//    private final MemberService memberService;
//
//    // 매일 밤 10시에 상위 3명의 회원을 조회하고 로그에 출력
//    @Scheduled(cron = "0 0 22 * * ?") // 매일 밤 10시에 실행
//    public void updateTopMembers() {
//        List<Member> topMembers = memberService.getTop3MembersByHearts();
//        log.info("Top 3 members by hearts: ");
//        topMembers.forEach(member -> log.info("Member: {} - Total Hearts: {}", member.getNickname(), memberService.getTotalHeartsForMember(member)));
//    }
//}
