package com.omar.chatappback.services;

import com.omar.chatappback.entities.User;
import com.omar.chatappback.mappers.UserMapper;
import com.omar.chatappback.shared.AuthenticatedUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserSynchronizer {

    private final UserService userService;

    private final UserMapper userMapper;

    private static final String UPDATE_AT_KEY = "updated_at";

    public void syncWithIdp(Jwt jwtToken, boolean forceResync) {
        Map<String, Object> attributes = jwtToken.getClaims();
        List<String> rolesFromToken = AuthenticatedUser.extractRolesFromToken(jwtToken);
        User user = userMapper.fromTokenAttributes(attributes, rolesFromToken);
        Optional<User> existingUser = userService.getOneByEmail(user.getEmail());
        if (existingUser.isPresent()) {
            if (attributes.get(UPDATE_AT_KEY) != null) {
                Instant lastModifiedDate = existingUser.orElseThrow().getLastModifiedDate();
                Instant idpModifiedDate;
                if (attributes.get(UPDATE_AT_KEY) instanceof Instant instant) {
                    idpModifiedDate = instant;
                } else {
                    idpModifiedDate = Instant.ofEpochSecond((Integer) attributes.get(UPDATE_AT_KEY));
                }
                if (idpModifiedDate.isAfter(lastModifiedDate) || forceResync) {
                    updateUser(user, existingUser.get());
                }
            }
        } else {
            user.initFieldForSignup();
            userService.save(user);
        }

    }

    private void updateUser(User user, User existingUser) {
        existingUser.updateFromUser(user);
        userService.save(existingUser);
    }




}
