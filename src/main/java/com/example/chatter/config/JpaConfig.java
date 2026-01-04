package com.example.chatter.config;

import com.example.chatter.model.User;
import com.example.chatter.repository.UserRepository;
import com.example.chatter.security.CustomUserDetails;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class JpaConfig {

    @Bean
    public AuditorAware<User> auditorProvider(UserRepository userRepository) {
        return () -> {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null || !authentication.isAuthenticated()
                    || authentication.getPrincipal().equals("anonymousUser")) {
                return Optional.empty();
            }

            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            // We fetch the user from the repository to ensure it's managed/fresh,
            // although using the one from userDetails might work if we just need the ID.
            // But to be safe with JPA relationships, let's fetch it or use a proxy if we
            // had one.
            // Since we have the email, let's look it up.
            return userRepository.findByEmail(userDetails.getEmail());
        };
    }
}
