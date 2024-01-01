package com.zerobase.oriticket.domain.members.service;

import com.zerobase.oriticket.domain.members.dto.user.UserRequest;
import com.zerobase.oriticket.domain.members.entity.Member;
import com.zerobase.oriticket.domain.members.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class UserService {

    @Autowired
    private UserRepository userRepository;
    private UserRequest userRequest;

    @Transactional
    public void updateUserByEmail(UserRequest userRequest) {
        if (userRepository.existsByEmail(userRequest.getEmail())) {
            throw new RuntimeException("이미 가입되어 있는 회원입니다.");
        }
        Member entityKakao = userRequest.toEntityKakao(userRequest);
        userRepository.save(entityKakao);
    }

    @Transactional(readOnly = true)
    public boolean findByEmail(String userEmail) {
        return userRepository.existsByEmail(userEmail);
    }

    public Member updateUser(UserRequest userRequest) {
        Member byId = userRepository.findByMemberId(userRequest.getId());
        if (byId == null) {
            throw new RuntimeException("존재하지 않는 정보 입니다.");
        }
        if (userRequest.getNickname() != null) {
            byId.setNickname(userRequest.getNickname());
        }
        userRepository.save(byId);
        return byId;
    }

    public Member checkUser(long id) {
        Member byId = userRepository.findByMemberId(id);
        if (byId == null) {
            throw new RuntimeException("존재하지 않는 정보 입니다.");
        }
        return byId;
    }

    public Member deleteUser(long id) {
        Member byId = userRepository.findByMemberId(id);
        if (byId == null) {
            throw new RuntimeException("존재하지 않는 정보 입니다.");
        }
        userRepository.delete(byId);
        return byId;
    }
}
