package com.zerobase.oriticket.domain.members.service;

import com.zerobase.oriticket.domain.members.constants.RoleType;
import com.zerobase.oriticket.domain.members.dto.user.UserRequest;
import com.zerobase.oriticket.domain.members.entity.User;
import com.zerobase.oriticket.domain.members.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

//    @Autowired
//    private BCryptPasswordEncoder encoder;


    @Transactional(readOnly = true)
    public Boolean findUser(String userEmail) {

        return userRepository.existsByEmail(userEmail);
    }

    @Transactional
    public User registerUser(UserRequest userRQ) {

        return User.builder()
                .name(userRQ.getName())
                .phoneNum(userRQ.getPhoneNum())
                .build();

        userRepository.save(User);

    }
//
//    @Transactional
//    public void 회원수정(User user) {
//        // 수정시에는 영속성 컨텍스트 User 오브젝트를 영속화시키고, 영속화된 User 오브젝트를 수정
//        // select를 해서 User오브젝트를 DB로 부터 가져오는 이유는 영속화
//        // 영속화된 오브젝트를 변경하면 자동으로 DB에 update문 날려줌
//        User persistance = userRepository.findById(user.getId()).orElseThrow(() -> {
//            return new IllegalArgumentException("회원 찾기 실패");
//        });
//
//        // Validate 체크 => oauth 필드에 값이 없으면 수정 가능
//        if (persistance.getOauth() == null || persistance.getOauth().equals("")) {
//            String rawPassword = user.getPassword();
//            String encPassword = encoder.encode(rawPassword);
//            persistance.setPassword(encPassword);
//            persistance.setEmail(user.getEmail());
//        }
//
//        // 회원수정 함수 종료시 = 서비스 종료 = 트랜잭션 종료 = commit 이 자동
//        // 영속화된 persistance 객체의 변화가 감지되면 더티체킹이 되어 update문을 날려줌
//    }
}
