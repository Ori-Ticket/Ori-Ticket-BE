//package com.zerobase.oriticket.domain.members.auth;
//
//import com.zerobase.oriticket.domain.members.entity.User;
//import com.zerobase.oriticket.domain.members.repository.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//@Service
//public class PrincipalDetailService implements UserDetailsService {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        User principal = userRepository.findByUsername(username).orElseThrow(() -> {
//            return new UsernameNotFoundException("해당 사용자를 찾을 수 없습니다. : " + username);
//        });
//        return new PrincipalDetail(principal); // 시큐리티의 세션에 유저 정보가 저장이 됨.
//    }
//}
