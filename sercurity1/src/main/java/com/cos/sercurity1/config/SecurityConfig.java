package com.cos.sercurity1.config;

//구글 로그인이 완료된 뒤의 후처리가 필요하다.
// 1.코드받기(인증) 2.엑세스토큰(권한) 3.사용자프로필 정보를 가져온다
// 4-1. 그정보를 토대로 회원가입을 자동으로진행시키기도함
// 4-2(이메일,전화번호,이름,아이디) 쇼핑몰의 경우 (집주소)같은게 필요함
// 추가적인 회원가입이 되어야한다.
import com.cos.sercurity1.config.oauth.PrincipalOauth2UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity // 스프링 시큐리티 필터가 스프링 필터체인에 등록이 된다.
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true) // 특정 주소 접근시 권한 및 인증을 위한 어노테이션 활성화
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private PrincipalOauth2UserService principalOauth2UserService;

    // 해당 메서드의 리턴되는 오브젝트를 IoC로 등록해준다.
    @Bean
    public BCryptPasswordEncoder encodePwd() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers("/user/**").authenticated()
                //.antMatchers("/admin/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
                //.antMatchers("/admin/**").access("hasRole('ROLE_ADMIN') and hasRole('ROLE_USER')")
                .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
                .anyRequest().permitAll()
                .and()
                    .formLogin()
                    .loginPage("/login")
                    .loginProcessingUrl("/loginProc")
                    .defaultSuccessUrl("/")
                .and()
                    .oauth2Login()
                    .loginPage("/login")
                    .userInfoEndpoint()
                    .userService(principalOauth2UserService);  // 구글로그인이 완료되면 (엑세스토큰+사용자프로필정보를 받는다.)
    }
}
