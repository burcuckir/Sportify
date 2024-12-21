package com.sportify.userservice.mappers;

import com.sportify.userservice.entities.User;
import com.sportify.userservice.models.request.RegisterRequest;
import com.sportify.userservice.models.response.UpdatedPasswordResponse;
import com.sportify.userservice.models.response.UserDetailResponse;
import org.sportify.hashing.PasswordSecurityUtil;

import java.sql.Date;

public class UserMapper {
    public static User mapToUser(RegisterRequest registerRequest) {
        User user = new User();
        user.setPassword(PasswordSecurityUtil.hashPassword(registerRequest.getPassword()));
        user.setEmail(registerRequest.getEmail());
        user.setUsername(registerRequest.getUsername());
        user.setBirthDate(Date.valueOf(registerRequest.getBirthday()));
        user.setPhone(registerRequest.getPhoneNumber());
        return user;
    }

    public static UserDetailResponse mapToUserDetailResponse(User user) {
        UserDetailResponse userDetailResponse = new UserDetailResponse();
        userDetailResponse.setId(user.getId());
        userDetailResponse.setUsername(user.getUsername());
        userDetailResponse.setEmail(user.getEmail());
        return userDetailResponse;
    }

    public static UpdatedPasswordResponse mapToUpdatedPasswordResponse(){
        UpdatedPasswordResponse updatedPasswordResponse = new UpdatedPasswordResponse();
        updatedPasswordResponse.setMessage("Your password has been updated successfully.");
        return updatedPasswordResponse;
    }
}
