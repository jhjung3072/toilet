package com.jaeho.toilet.security;


import com.jaeho.toilet.model.entity.User;
import com.jaeho.toilet.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
         User existingUser=userRepository.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException("user not found"));
         return new org.springframework.security.core.userdetails.User(existingUser.getUsername(),  existingUser.getPassword(), new ArrayList<>());
    }
}
