package com.jaeho.toilet.controller.response;


import com.jaeho.toilet.model.UserRole;
import com.jaeho.toilet.model.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserJoinResponse {

    private Long id;
    private String userName;
    private UserRole role;

    public static UserJoinResponse fromUser(User user){
        return new UserJoinResponse(
                user.getUserId(),
                user.getUsername(),
                user.getRole()
        );
    }
}
