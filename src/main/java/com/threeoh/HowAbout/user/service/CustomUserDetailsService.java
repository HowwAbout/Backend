package com.threeoh.HowAbout.user.service;

import com.threeoh.HowAbout.user.entity.User;
import com.threeoh.HowAbout.user.reopository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email) // username 대신 email 변수명 사용
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        return new CustomUserDetails(user);
    }
}