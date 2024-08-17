package com.omar.chatappback.mappers;


import com.omar.chatappback.entities.Authority;
import com.omar.chatappback.entities.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserMapper {

    public  User fromTokenAttributes(Map<String, Object> attributes, List<String> rolesFromAccessToken) {
        // Extracting attributes
        String sub = String.valueOf(attributes.get("sub"));

        String username = getAttribute(attributes, "preferred_username");
        if (username != null) {
            username = username.toLowerCase();
        }

        // Extract fields with appropriate default values
        String firstName = extractFirstname(attributes);
        String lastName = extractLastName(attributes);
        String email = extractEmail(attributes, sub, username);
        String imageUrl = extractImageUrl(attributes);

        // Creating Authority set directly
        Set<Authority> authorities = rolesFromAccessToken
                .stream()
                .map(Authority::new) // Directly create Authority instances
                .collect(Collectors.toSet());

        // Creating User instance directly
        return new User(
                firstName,
                lastName,
                email,
                imageUrl,
                authorities,
                UUID.randomUUID(), // Generate a new UUID for publicId
                null // Set lastSeen to null or a default value
        );
    }

    private static String getAttribute(Map<String, Object> attributes, String key) {
        return attributes.containsKey(key) ? attributes.get(key).toString() : null;
    }

    private static String extractFirstname(Map<String, Object> attributes) {
        String givenName = getAttribute(attributes, "given_name");
        String nickname = getAttribute(attributes, "nickname");
        return givenName != null ? givenName : nickname;
    }

    private static String extractLastName(Map<String, Object> attributes) {
        return getAttribute(attributes, "family_name");
    }

    private static String extractEmail(Map<String, Object> attributes, String sub, String username) {
        String email = getAttribute(attributes, "email");
        if (email != null) {
            return email;
        } else if (sub.contains("|") && username != null && username.contains("@")) {
            return username;
        } else {
            return sub;
        }
    }

    private static String extractImageUrl(Map<String, Object> attributes) {
        return getAttribute(attributes, "image_url");
    }

}
