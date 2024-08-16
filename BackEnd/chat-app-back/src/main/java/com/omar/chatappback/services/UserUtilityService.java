package com.omar.chatappback.services;


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

@Service
@RequiredArgsConstructor
public class UserUtilityService {

    private final UserSynchronizer userSynchronizer;

    private final UserReader userReader;

    private final UserPresence userPresence;

    @Transactional
    public User getAuthenticatedUserWithSync(Jwt oauth2User, boolean forceResync) {
        userSynchronizer.syncWithIdp(oauth2User, forceResync);
        return userReader.getByEmail(String.valueOf(new UserEmail(AuthenticatedUser.username().get())))
                .orElseThrow();
    }

    @Transactional(readOnly = true)
    public User getAuthenticatedUser() {
        return userReader.getByEmail(String.valueOf(new UserEmail(AuthenticatedUser.username().get())))
                .orElseThrow();
    }

    @Transactional(readOnly = true)
    public List<User> search(Pageable pageable, String query) {
        return userReader.search(pageable, query).stream().toList();
    }

    @Transactional(readOnly = true)
    public Optional<User> getUserByEmail(UserEmail userEmail) {
        return userReader.getByEmail(String.valueOf(userEmail));
    }

    @Transactional
    public void updatePresence(UserPublicId userPublicId) {
        userPresence.updatePresence(userPublicId);
    }

    @Transactional(readOnly = true)
    public Optional<Instant> getLastSeen(UserPublicId userPublicId) {
        return userPresence.getLastSeenByPublicId(userPublicId);
    }









}
