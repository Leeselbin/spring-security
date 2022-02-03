package com.cos.sercurity1.repository;

import com.cos.sercurity1.model.User;
import org.springframework.data.jpa.repository.JpaRepository;


//CRUD 함수를 JpaRepository가 들고 있다.
// @Repository라는 어노테이션이 없어도 IoC된다.


public interface UserRepository extends JpaRepository<User, Integer> {


    // findBy규칙  -> Username 은 문법이다
    // select * from user where username = ?(username) 이게 호출된다.
    public User findByUsername(String username);

}
