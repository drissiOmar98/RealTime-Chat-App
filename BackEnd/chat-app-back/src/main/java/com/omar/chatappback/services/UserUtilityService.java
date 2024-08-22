package com.omar.chatappback.services;


import com.omar.chatappback.dto.user.UserResponse;
import com.omar.chatappback.entities.User;
import com.omar.chatappback.shared.AuthenticatedUser;
import com.omar.chatappback.user.UserEmail;
import com.omar.chatappback.user.UserPublicId;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserUtilityService {

    private final UserSynchronizer userSynchronizer;

    private final UserReader userReader;

    private final UserPresence userPresence;

    @Transactional
    public User getAuthenticatedUserWithSync(Jwt oauth2User, boolean forceResync) {
        userSynchronizer.syncWithIdp(oauth2User, forceResync);
        return userReader.getByEmail(AuthenticatedUser.username().get())
                .orElseThrow();
    }

    @Transactional(readOnly = true)
    public User getAuthenticatedUser() {
        return userReader.getByEmail(AuthenticatedUser.username().get())
                .orElseThrow();
    }

    @Transactional(readOnly = true)
    public List<UserResponse> search(Pageable pageable, String query) {
        return userReader.search(pageable, query).stream().toList();
    }

    @Transactional(readOnly = true)
    public Optional<User> getUserByEmail(UserEmail userEmail) {
        return userReader.getByEmail(String.valueOf(userEmail));
    }

    @Transactional
    public void updatePresence(UUID userPublicId) {
        userPresence.updatePresence(userPublicId);
    }

    @Transactional(readOnly = true)
    public Optional<Instant> getLastSeen(UUID userPublicId) {
        return userPresence.getLastSeenByPublicId(userPublicId);
    }









}
