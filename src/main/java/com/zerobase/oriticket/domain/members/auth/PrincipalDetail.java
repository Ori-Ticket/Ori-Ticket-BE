//package com.zerobase.oriticket.domain.members.auth;
//
//import com.zerobase.oriticket.domain.members.entity.User;
//import java.util.ArrayList;
//import java.util.Collection;
//import lombok.Data;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//
//@Data
//public class PrincipalDetail implements UserDetails {
//    private User user;
//
//    public PrincipalDetail(User user) {
//        this.user = user;
//    }
//
//    @Override
//    public String getPassword() {
//        return user.getPassword();
//    }
//
//    @Override
//    public String getUsername() {
//        return user.getUsername();
//    }
//
//    // (false: 계정만료)
//    @Override
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//
//    // (false: 계정잠김)
//    @Override
//    public boolean isAccountNonLocked() {
//        return true;
//    }
//
//    // (false: 비밀번호만료)
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }
//
//    // (false: 계정비활성화)
//    @Override
//    public boolean isEnabled() {
//        return true;
//    }
//
//    // 계정권한 리턴
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//
//        Collection<GrantedAuthority> collectors = new ArrayList<>();
//        collectors.add(() -> {
//            return "ROLE_" + user.getRole();
//        });
//
//        return collectors;
//    }
//
//}
