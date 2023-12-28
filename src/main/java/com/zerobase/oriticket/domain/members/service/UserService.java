package com.zerobase.oriticket.domain.members.service;

import com.zerobase.oriticket.domain.members.dto.user.UserRequest;
import com.zerobase.oriticket.domain.members.entity.UserEntity;
import com.zerobase.oriticket.domain.members.repository.UserRepository;
import jakarta.persistence.EntityManager;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EntityManager entityManager;

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
        boolean email = userRepository.existsByEmail(userEmail);
        if (!email) {
            throw new RuntimeException("존재하지 않는 회원입니다.");
        }
        System.out.println("가입되어있는 유저 입니다.");
        UserEntity
                .builder()
                .email(userEmail)
                .build();
    }

    public UserEntity updateUser(UserRequest userRequest) {
        UserEntity byId = userRepository.findById(userRequest.getId());
        if (byId == null) {
            throw new RuntimeException("존재하지 않는 정보 입니다.");
        }
        if (userRequest.getNickname() != null) {
            byId.setNickname(userRequest.getNickname());
        }
        userRepository.save(byId);
        return byId;
    }

    public UserEntity checkUser(long id) {
        UserEntity byId = userRepository.findById(id);
        if (byId == null) {
            throw new RuntimeException("존재하지 않는 정보 입니다.");
        }
        return byId;
    }

    public UserEntity deleteUser(long id) {
        UserEntity byId = userRepository.findById(id);
        if (byId == null) {
            throw new RuntimeException("존재하지 않는 정보 입니다.");
        }
        userRepository.delete(byId);
        return byId;
    }
}
