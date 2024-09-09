package com.threeoh.HowAbout.user.dto;

import com.threeoh.HowAbout.user.entity.Role;
import com.threeoh.HowAbout.user.entity.User;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record UserRegisterRequest(

        @Schema(description = "User Email", example = "glint@gmail.com", required = true)
        String email,

        @Schema(description = "User Password", example = "password", required = true)
        String password,

        @Schema(description = "User Nickname", example = "초코바", required = true)
        String nickname,

        @Schema(description = "User Role", example = "USER", required = true)
        Role role

) {
        public User toEntity() {
                return User.createUser(email, password, nickname, role);
        }

        public static UserRegisterRequest of(String email, String password, String nickname, Role role) {
                return UserRegisterRequest.builder()
                        .email(email)
                        .password(password)
                        .nickname(nickname)
                        .role(role)
                        .build();
        }
}
