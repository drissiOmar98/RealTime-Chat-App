package com.omar.chatappback.services;

import com.omar.chatappback.dto.user.UserResponse;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserPresence {

    private final UserService userService;
    private final UserReader userReader;

    public UserPresence(UserService userService, UserReader userReader) {
        this.userService = userService;
        this.userReader = userReader;
    }

    public void updatePresence(UUID userPublicId) {
        userService.updateLastSeenByPublicId(userPublicId, Instant.now());
    }

    public Optional<Instant> getLastSeenByPublicId(UUID userPublicId) {
        Optional<UserResponse> byPublicId = userReader.getByPublicId(userPublicId);
        return byPublicId.map(UserResponse::getLastSeen);
    }
}
