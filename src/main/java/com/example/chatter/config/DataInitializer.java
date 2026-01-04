package com.example.chatter.config;

import com.example.chatter.model.User;
import com.example.chatter.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initData(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (userRepository.findByEmail("bill@localhost").isEmpty()) {
                userRepository.save(
                        new User("Bill Gates", "bill@localhost", passwordEncoder.encode("password"), "ROLE_USER"));
            }
            if (userRepository.findByEmail("linus@localhost").isEmpty()) {
                userRepository.save(new User("Linus Torvalds", "linus@localhost", passwordEncoder.encode("password"),
                        "ROLE_ADMIN"));
            }
        };
    }
}
