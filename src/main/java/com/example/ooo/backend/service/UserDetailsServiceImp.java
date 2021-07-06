package com.example.ooo.backend.service;

import com.example.ooo.backend.model.User;
import com.example.ooo.backend.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.NoSuchElementException;


public class UserDetailsServiceImp implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, InternalAuthenticationServiceException, NoSuchElementException {

        if(userRepo.findByLogin(username).isPresent()){
            User user = userRepo.findByLogin(username).get();
            UserBuilder builder;
                builder = org.springframework.security.core.userdetails.User.withUsername(username);
                builder.password(user.getPassword());
                builder.roles(user.getRole().getName());
                return builder.build();
        }

        throw new UsernameNotFoundException("User not found.");

    }

}