package com.zerobase.oriticket.user.entity;

import com.zerobase.oriticket.user.constants.UserRole;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "members")
@AllArgsConstructor
@NoArgsConstructor
public class Members {

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


    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private UserRole roles;


    private String status;


    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime registeredAt;


    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime modifiedAt;


}
