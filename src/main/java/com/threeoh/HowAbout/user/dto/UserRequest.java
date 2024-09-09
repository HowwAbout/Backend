package com.threeoh.HowAbout.user.dto;

import com.threeoh.HowAbout.user.entity.Role;
import com.threeoh.HowAbout.user.entity.User;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;

public record UserRequest(

        @Schema(description = "User ID", example = "1", required = true)
        Long id,

        @Schema(description = "User Email", example = "sunny@gmail.com", required = true)
        String email,

        @Schema(description = "User Nickname", example = "초코바", required = true)
        String nickname

) {

    public static UserRegisterRequest of(String email, String password, String nickname) {
        return UserRegisterRequest.builder()
                .email(email)
                .password(password)
                .nickname(nickname)
                .build();
    }
}
