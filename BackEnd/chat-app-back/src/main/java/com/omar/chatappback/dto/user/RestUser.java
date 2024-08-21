package com.omar.chatappback.dto.user;

import com.omar.chatappback.entities.User;
import lombok.Builder;


import java.util.Set;
import java.util.UUID;

@Builder
public record RestUser(UUID publicId,
                       String firstName,
                       String lastName,
                       String email,
                       String imageUrl,
                       Set<RestAuthority> authorities) {

    public static RestUser from(User user) {
        RestUser.RestUserBuilder restUserBuilder = RestUser.builder();

        if(user.getImageUrl() != null) {
            restUserBuilder.imageUrl(user.getImageUrl());
        }

        return restUserBuilder
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .publicId(user.getPublicId())
                .authorities(RestAuthority.fromSet(user.getAuthorities()))
                .build();
    }
}

