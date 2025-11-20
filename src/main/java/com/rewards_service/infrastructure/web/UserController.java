package com.rewards_service.infrastructure.web;

import com.rewards_service.application.service.UserService;
import com.rewards_service.domain.model.UserAccount;
import com.rewards_service.dto.UserCreateRequest;
import com.rewards_service.dto.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "User Management", description = "User account management operations")
public class UserController {

    private final UserService userService;

    @PostMapping
    @Operation(summary = "Create a new user account")
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserCreateRequest request) {
        try {
            UserAccount user = userService.createUser(
                request.getExternalUserId(),
                request.getDisplayName(),
                request.getEmail(),
                request.getTier()
            );

            UserResponse response = new UserResponse(
                user.getId(),
                user.getExternalUserId(),
                user.getDisplayName(),
                user.getEmail(),
                user.getApiKey(),
                user.getTier(),
                user.getCreatedAt(),
                user.getUpdatedAt()
            );

            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{userId}")
    @Operation(summary = "Get user by ID")
    public ResponseEntity<UserResponse> getUser(@PathVariable UUID userId) {
        Optional<UserAccount> userOpt = userService.findById(userId);

        if (userOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        UserAccount user = userOpt.get();
        UserResponse response = new UserResponse(
            user.getId(),
            user.getExternalUserId(),
            user.getDisplayName(),
            user.getEmail(),
            user.getApiKey(),
            user.getTier(),
            user.getCreatedAt(),
            user.getUpdatedAt()
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/by-api-key")
    @Operation(summary = "Get current user by API key")
    public ResponseEntity<UserResponse> getCurrentUser(@RequestHeader("X-API-Key") String apiKey) {
        Optional<UserAccount> userOpt = userService.findByApiKey(apiKey);

        if (userOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        UserAccount user = userOpt.get();
        UserResponse response = new UserResponse(
            user.getId(),
            user.getExternalUserId(),
            user.getDisplayName(),
            user.getEmail(),
            user.getApiKey(),
            user.getTier(),
            user.getCreatedAt(),
            user.getUpdatedAt()
        );

        return ResponseEntity.ok(response);
    }

    @PostMapping("/{userId}/rotate-api-key")
    @Operation(summary = "Rotate API key for a user")
    public ResponseEntity<UserResponse> rotateApiKey(@PathVariable UUID userId) {
        try {
            UserAccount user = userService.rotateApiKey(userId);

            UserResponse response = new UserResponse(
                user.getId(),
                user.getExternalUserId(),
                user.getDisplayName(),
                user.getEmail(),
                user.getApiKey(),
                user.getTier(),
                user.getCreatedAt(),
                user.getUpdatedAt()
            );

            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
