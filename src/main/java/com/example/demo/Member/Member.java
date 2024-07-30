package com.example.demo.Member;

import com.example.demo.UserRole;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nickname;
    private String loginId;
    private String password;

    private UserRole role;

    @Builder.Default
    private boolean today = false;

    @CreationTimestamp
    private LocalDateTime registerDate;
}
