package com.example.ooo.backend.config;

import com.example.ooo.backend.service.FetchService;
import com.example.ooo.backend.util.Constants;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
@AllArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final FetchService fetchService;
    private final DataSource dataSource;

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.formLogin()
                .loginPage("/")
                .failureUrl("/?error=true")
                .defaultSuccessUrl("/default", true);

        http.logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .clearAuthentication(true)
                .invalidateHttpSession(true);

        http.authorizeRequests()
                .antMatchers("/default")
                .authenticated()
                .antMatchers("/admin/**")
                .hasRole(Constants.ADMIN)
                .antMatchers("/user/**")
                .hasRole(Constants.USER);

        http.authorizeRequests()
                .antMatchers("/")
                .anonymous()
                .anyRequest()
                .permitAll();
        http.csrf().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        auth.inMemoryAuthentication()
                .withUser(fetchService.getUser(authentication.getName()).getLogin())
                .password(fetchService.getUser(authentication.getName()).getPassword())
                .roles(fetchService.getUser(authentication.getName()).getRole().getName());




        String fetchAdminsQuery = "select login, password, enabled"
                + " from users"
                + " where login = ?";

        String fetchRolesQuery = "select u.login, r.name" +
                " from users u, roles r" +
                " where r.id = u.role_id and u.login = ?";


    }
}
