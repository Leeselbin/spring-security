package com.cos.jwtserver.config;

import com.cos.jwtserver.config.jwt.jwtAuthenticationFilter;
import com.cos.jwtserver.config.jwt.jwtAuthorizationFilter;
import com.cos.jwtserver.filter.MyFilter1;
import com.cos.jwtserver.filter.MyFilter3;
import com.cos.jwtserver.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;
import org.springframework.web.filter.CorsFilter;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    private final CorsFilter corsFilter;

    private final UserRepository userRepository;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilterBefore(new MyFilter3(), SecurityContextPersistenceFilter.class);
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) //세션을 사용하지않겟다.
        .and()
                .addFilter(corsFilter) // 필터만든거 걸어줌
                .formLogin().disable()
                .httpBasic().disable() // 매번 아이디 비번 날려줘서 보안떄문에 disable() 한다
                .addFilter(new jwtAuthenticationFilter(authenticationManager()))  //AuthenticationManager를 던져줘야한다.
                .addFilter(new jwtAuthorizationFilter(authenticationManager(), userRepository))
                .authorizeRequests()
                .antMatchers("/api/v1/user/**")
                .access("hasRole('ROLE_USER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
                .antMatchers("/api/v1/manager/**")
                .access("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
                .antMatchers("/api/v1/admin/**")
                .access("hasRole('ROLE_ADMIN')")
                .anyRequest().permitAll();

    }
}
