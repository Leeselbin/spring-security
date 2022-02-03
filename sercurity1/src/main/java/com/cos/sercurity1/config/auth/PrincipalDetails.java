package com.cos.sercurity1.config.auth;

// 시큐리티가    /login 주소 요청이 오면 낚아채서 로그인을 진행시킨다.
// 로그인 진행이 완료가 되면 시큐리티가 가지고있는 session을 만들어준다.
// (Security CpmtextHolder)라는 키값을 저장시킨다.
// 시큐리티 오브젝트에는  => Authentication 타입의 객체만 들어간다.
// Authentication 안에 User 정보가 있어야된다.
// User 오브젝트타입은 =>  UserDetails 타입 객체 여야한다.

// Security Session => Authentication => UserDetails(PrincipalDetails)


import com.cos.sercurity1.model.User;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@Data
public class PrincipalDetails implements UserDetails{

    private User user;

    public PrincipalDetails(User user) {
        super();
        this.user = user;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collet = new ArrayList<GrantedAuthority>();
        collet.add(()->{ return user.getRole();});
        return collet;
    }
}
