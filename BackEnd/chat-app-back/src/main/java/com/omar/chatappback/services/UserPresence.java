package com.omar.chatappback.services;

import com.omar.chatappback.entities.User;
import com.omar.chatappback.user.UserPublicId;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
public class UserPresence {

    private final UserService userService;
    private final UserReader userReader;

    public UserPresence(UserService userService, UserReader userReader) {
        this.userService = userService;
        this.userReader = userReader;
    }

    public void updatePresence(UserPublicId userPublicId) {
        userService.updateLastSeenByPublicId(userPublicId, Instant.now());
    }

    public Optional<Instant> getLastSeenByPublicId(UserPublicId userPublicId) {
        Optional<User> byPublicId = userReader.getByPublicId(userPublicId);
        return byPublicId.map(User::getLastSeen);
    }
}
