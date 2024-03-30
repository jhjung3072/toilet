package com.jaeho.toilet.controller;


import com.jaeho.toilet.controller.request.UserJoinRequest;
import com.jaeho.toilet.controller.request.UserLoginRequest;
import com.jaeho.toilet.controller.response.Response;
import com.jaeho.toilet.controller.response.UserJoinResponse;
import com.jaeho.toilet.controller.response.UserLoginResponse;
import com.jaeho.toilet.model.entity.User;
import com.jaeho.toilet.security.CustomUserDetailService;
import com.jaeho.toilet.service.UserService;
import com.jaeho.toilet.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final CustomUserDetailService userDetailService;

    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @PostMapping("/join")
    public Response<UserJoinResponse> join(@RequestBody UserJoinRequest request){
        User user = userService.join(request);
        return Response.success(UserJoinResponse.fromUser(user));
    }

    @PostMapping("/login")
    public ResponseEntity<UserLoginResponse> login(@RequestBody UserLoginRequest request) throws Exception {
        authenticate(request.getUsername(), request.getPassword());

        final UserDetails userDetails = userDetailService.loadUserByUsername(request.getUsername());
        String roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .reduce((a, b) -> a + ", " + b)
                .orElse("No Roles");
        logger.info("User Roles: {}", roles);
        final String token = jwtTokenUtil.generateToken(request.getUsername());

        return new ResponseEntity<UserLoginResponse>(new UserLoginResponse(token), HttpStatus.OK);
    }

    private void authenticate(String username, String password) throws Exception {
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        }catch (DisabledException e){
            throw new Exception("User disabled");
        }catch (BadCredentialsException e){
            throw new Exception("Bad credentials");
        }
    }

    @GetMapping("/test")
    public String test(){
        logger.info("User Roles: {}", "asdljksadkjsdakjklsd");
        return "sdkdsgjfklsdflks";
    }

}