package com.jaeho.toilet.service;


import com.jaeho.toilet.controller.request.UserJoinRequest;
import com.jaeho.toilet.controller.request.UserLoginRequest;
import com.jaeho.toilet.exception.ErrorCode;
import com.jaeho.toilet.exception.ToiletApplicationException;
import com.jaeho.toilet.model.UserRole;
import com.jaeho.toilet.model.entity.User;
import com.jaeho.toilet.repository.UserRepository;
import com.jaeho.toilet.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtTokenUtil jwtTokenUtil;

    public User join(UserJoinRequest userJoinRequest){

        userRepository.findByUsername(userJoinRequest.getUsername()).ifPresent(it -> {
            throw new ToiletApplicationException(ErrorCode.DUPLICATED_USER_NAME,String.format("%s is duplicated",userJoinRequest.getUsername()));
        });

        User newUser = new User();
        newUser.setUsername(userJoinRequest.getUsername());
        newUser.setPassword(passwordEncoder.encode(userJoinRequest.getPassword()));
        newUser.setRole(UserRole.USER);
        return userRepository.save(newUser);
    }

    public String login(UserLoginRequest request){
        User user = userRepository.findByUsername(request.getUsername()).orElseThrow(() -> new ToiletApplicationException(ErrorCode.USER_NOT_FOUND,String.format("%s not founded",request.getUsername())));

        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())){
            throw new ToiletApplicationException(ErrorCode.INVALID_PASSWORD);
        }

        return jwtTokenUtil.generateToken(user.getUsername());
    }
}
