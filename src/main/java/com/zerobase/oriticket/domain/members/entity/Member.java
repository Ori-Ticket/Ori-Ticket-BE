package com.zerobase.oriticket.domain.members.entity;

import com.zerobase.oriticket.domain.members.constants.MemberStatus;
import com.zerobase.oriticket.domain.members.constants.UserRole;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long membersId;

    @Column(length = 30)
    private String email;

    @Column(nullable = false, length = 30)
    private String name;

    @Column(nullable = false, length = 30)
    private String nickname;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime birthDate;

    @Column(nullable = false, length = 30)
    private String phoneNum;

    @Column(nullable = false, length = 10)
    @Enumerated(EnumType.STRING)
    private UserRole roles = UserRole.MEMBER;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private MemberStatus status;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime registeredAt;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime modifiedAt;

}
