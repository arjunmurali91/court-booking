package com.xyz.booking.courtbooking.controller;


import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@Tag(name = "Auth")
public class AuthController {

    @PostMapping("/signup")
    public ResponseEntity<?> signup() {
        return ResponseEntity.ok(
            Map.of("message", "Signup successful. Please validate your email.")
        );
    }

    @PostMapping("/validate-email")
    public ResponseEntity<?> validateEmail() {
        return ResponseEntity.ok(
            Map.of("message", "Email validated successfully.")
        );
    }

    @PostMapping("/resend-validation")
    public ResponseEntity<?> resendValidation() {
        return ResponseEntity.ok(
            Map.of("message", "Validation email resent.")
        );
    }

    @PostMapping("/login")
    public ResponseEntity<?> login() {
        return ResponseEntity.ok(
            Map.of(
                "accessToken", "dummy-access-token",
                "refreshToken", "dummy-refresh-token"
            )
        );
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh() {
        return ResponseEntity.ok(
            Map.of("accessToken", "new-dummy-access-token")
        );
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword() {
        return ResponseEntity.ok(
            Map.of("message", "Password reset link sent to email.")
        );
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword() {
        return ResponseEntity.ok(
            Map.of("message", "Password reset successful.")
        );
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        return ResponseEntity.ok(
            Map.of("message", "Logged out successfully.")
        );
    }
}
