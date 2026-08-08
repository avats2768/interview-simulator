package com.simulator.backend.auth;

import com.simulator.backend.auth.dto.AuthResponse;
import com.simulator.backend.auth.dto.LoginRequest;
import com.simulator.backend.auth.dto.RegisterRequest;
import com.simulator.backend.user.UserEntity;
import com.simulator.backend.email.EmailService;
import com.simulator.backend.user.UserRepository;
import com.simulator.backend.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final EmailService emailService;

    /**
     * Register New User
     */
    @Transactional
    public AuthResponse register(RegisterRequest request) {

        if (userRepository.existsByUsername(request.username())) {
            throw new RuntimeException("Username already exists.");
        }

        if (userRepository.existsByEmail(request.email())) {
            throw new RuntimeException("Email already exists.");
        }

        String verificationToken = UUID.randomUUID().toString();

        UserEntity user = UserEntity.builder()
                .username(request.username())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .role("CANDIDATE")
                .status(true)
                .emailVerified(false)
                .verificationToken(verificationToken)
                .verificationTokenExpiry(
                        LocalDateTime.now().plusHours(24)
                )
                .build();

        user = userRepository.save(user);

        // Send Verification Email
        emailService.sendVerificationEmail(
                user.getEmail(),
                user.getUsername(),
                user.getVerificationToken()
        );

        return AuthResponse.builder()
                .token(null)
                .id(user.getId())
                .uuid(user.getUuid())
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole())
                .emailVerified(false)
                .build();
    }

    /**
     * Login User
     */
    public AuthResponse login(LoginRequest request) {

        UserEntity user = userRepository
                .findByEmail(request.email())
                .orElseThrow(() ->
                        new RuntimeException("Invalid email or password.")
                );

        if (!passwordEncoder.matches(
                request.password(),
                user.getPassword())) {

            throw new RuntimeException(
                    "Invalid email or password."
            );
        }

        if (!user.getStatus()) {
            throw new RuntimeException(
                    "Your account is disabled."
            );
        }

        if (!Boolean.TRUE.equals(user.getEmailVerified())) {

            throw new RuntimeException(
                    "Please verify your email before logging in."
            );

        }

        String token = jwtService.generateToken(user);

        return AuthResponse.builder()
                .token(token)
                .id(user.getId())
                .uuid(user.getUuid())
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole())
                .emailVerified(user.getEmailVerified())
                .build();
    }

    @Transactional
    public void verifyEmail(String token) {

        UserEntity user = userRepository
                .findByVerificationToken(token)
                .orElseThrow(() ->
                        new RuntimeException("Invalid verification link.")
                );

        if (user.getVerificationTokenExpiry() == null ||
                user.getVerificationTokenExpiry().isBefore(LocalDateTime.now())) {

            throw new RuntimeException(
                    "Verification link has expired."
            );
        }

        if (Boolean.TRUE.equals(user.getEmailVerified())) {

            throw new RuntimeException(
                    "Email is already verified."
            );
        }

        user.setEmailVerified(true);

        user.setVerificationToken(null);

        user.setVerificationTokenExpiry(null);

        userRepository.save(user);

    }

    @Transactional
    public void resendVerificationEmail(String email) {

        UserEntity user = userRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found.")
                );

        if (Boolean.TRUE.equals(user.getEmailVerified())) {
            throw new RuntimeException("Email is already verified.");
        }

        String verificationToken = UUID.randomUUID().toString();

        user.setVerificationToken(verificationToken);
        user.setVerificationTokenExpiry(
                LocalDateTime.now().plusHours(24)
        );

        userRepository.save(user);

        emailService.sendVerificationEmail(
                user.getEmail(),
                user.getUsername(),
                verificationToken
        );
    }

}