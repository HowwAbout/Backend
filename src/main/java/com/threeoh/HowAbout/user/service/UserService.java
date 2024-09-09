package com.threeoh.HowAbout.user.service;

import com.threeoh.HowAbout.user.dto.UserRegisterRequest;
import com.threeoh.HowAbout.user.dto.UserResponse;
import com.threeoh.HowAbout.user.entity.Role;
import com.threeoh.HowAbout.user.entity.User;
import com.threeoh.HowAbout.user.reopository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserResponse createUser(UserRegisterRequest userRegisterRequest) {
        // 이미 존재하는 이메일인지 확인
        if (userRepository.findByEmail(userRegisterRequest.email()).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }
        if (userRepository.findByNickname(userRegisterRequest.nickname()).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 닉네임입니다.");
        }

        String encodedPassword = passwordEncoder.encode(userRegisterRequest.password());
        User user = userRegisterRequest.toEntity();
        user.changePassword(encodedPassword);
        return UserResponse.from(userRepository.save(user));
    }

    public void changePassword(Long userId, String newPassword) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with user id : " + userId));
        // 비밀번호 암호화 및 변경
        String encodedPassword = passwordEncoder.encode(newPassword);
        user.changePassword(encodedPassword);

        userRepository.save(user);
    }
}