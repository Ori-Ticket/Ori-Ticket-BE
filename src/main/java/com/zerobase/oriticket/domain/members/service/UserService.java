package com.zerobase.oriticket.domain.members.service;

import com.zerobase.oriticket.domain.members.dto.user.UserRequest;
import com.zerobase.oriticket.domain.members.entity.Member;
import com.zerobase.oriticket.domain.members.repository.UserRepository;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public void updateUserByEmail(UserRequest userKakao, UserRequest userRequest) {
        if (!Objects.equals(userKakao.getEmail(), userRequest.getEmail())) {
            throw new RuntimeException("카카오 토큰인증이 없는 회원입니다. 새로고침 후 다시 진행해주시길 바랍니다.");
        }
        userKakao.setName(userRequest.getName());
        userKakao.setBirthDate(userRequest.getBirthDate());
        userKakao.setPhoneNum(userRequest.getPhoneNum());
        userRepository.save(userKakao.toEntityKakao());
    }

    @Transactional(readOnly = true)
    public void findByEmail(String userEmail) {
        System.out.println(userEmail+"1");
        boolean email = userRepository.existsByEmail(userEmail);
        System.out.println(userEmail+"2");
        if (!email) {
            throw new RuntimeException("존재하지 않는 회원입니다.");
        }
        System.out.println("가입되어있는 유저 입니다.");
                Member
                .builder()
                .email(userEmail)
                .build();
    }

    public Member updateUser(UserRequest userRequest) {
        Member byId = userRepository.findByMembersId(userRequest.getId());
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
        Member byId = userRepository.findByMembersId(id);
        if (byId == null) {
            throw new RuntimeException("존재하지 않는 정보 입니다.");
        }
        return byId;
    }

    public Member deleteUser(long id) {
        Member byId = userRepository.findByMembersId(id);
        if (byId == null) {
            throw new RuntimeException("존재하지 않는 정보 입니다.");
        }
        userRepository.delete(byId);
        return byId;
    }
}
