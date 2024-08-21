package com.omar.chatappback.dto.user;

import com.omar.chatappback.entities.User;
import lombok.Builder;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Builder
public record RestSearchUser(
        UUID publicId,
        String firstName,
        String lastName,
        String email,
        String imageUrl,
        Set<RestAuthority> authorities
) {
    public static RestSearchUser from(UserResponse userResponse) {

        // Create the builder for RestSearchUser
        RestSearchUserBuilder restSearchUserBuilder = RestSearchUser.builder();

        // Set the fields based on the UserResponse
        restSearchUserBuilder.publicId(userResponse.getPublicId());

        if (userResponse.getFirstName() != null) {
            restSearchUserBuilder.firstName(userResponse.getFirstName());
        }

        if (userResponse.getLastName() != null) {
            restSearchUserBuilder.lastName(userResponse.getLastName());
        }

        if (userResponse.getEmail() != null) {
            restSearchUserBuilder.email(userResponse.getEmail());
        }

        if (userResponse.getImageUrl() != null) {
            restSearchUserBuilder.imageUrl(userResponse.getImageUrl());
        }

        if (userResponse.getAuthorities() != null) {
            Set<RestAuthority> restAuthorities = userResponse.getAuthorities().stream()
                    .map(RestAuthority::new)  // Assuming RestAuthority has a constructor that accepts a String
                    .collect(Collectors.toSet());
            restSearchUserBuilder.authorities(restAuthorities);
        }

        // Build and return the RestSearchUser instance
        return restSearchUserBuilder.build();
    }
}

