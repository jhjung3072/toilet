package com.jaeho.toilet.controller;


import com.jaeho.toilet.controller.request.UserJoinRequest;
import com.jaeho.toilet.controller.request.UserLoginRequest;
import com.jaeho.toilet.controller.response.Response;
import com.jaeho.toilet.controller.response.UserJoinResponse;
import com.jaeho.toilet.controller.response.UserLoginResponse;
import com.jaeho.toilet.model.entity.User;
import com.jaeho.toilet.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/join")
    public Response<UserJoinResponse> join(@RequestBody UserJoinRequest request){
        User user = userService.join(request);
        return Response.success(UserJoinResponse.fromUser(user));
    }

    @PostMapping("/login")
    public Response<UserLoginResponse> login(@RequestBody UserLoginRequest request){
        String token = userService.login(request);
        return Response.success(new UserLoginResponse(token));
    }


}