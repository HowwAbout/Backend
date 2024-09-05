package com.threeoh.HowAbout.user.dto;

import com.threeoh.HowAbout.user.entity.Role;
import com.threeoh.HowAbout.user.entity.User;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record UserLoginRequest(

        @Schema(description = "User Email", example = "glint@gmail.com", required = true)
        String email,

        @Schema(description = "User Password", example = "password", required = true)
        String password

) {

        public static UserRegisterRequest of(String email, String password) {
            return UserRegisterRequest.builder()
                    .email(email)
                    .password(password)
                    .build();
        }
}
