package com.omar.chatappback.dto.conversation;


import com.omar.chatappback.dto.user.UserResponse;
import com.omar.chatappback.entities.User;
import lombok.Builder;
import lombok.Value;

import java.time.Instant;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Value
@Builder
public class RestUserForConversation {

    String lastName;
    String firstName;
    UUID publicId;
    String imageUrl;
    Instant lastSeen;


    public static RestUserForConversation from(User user) {
        return RestUserForConversation.builder()
                .lastName(user.getLastName())
                .firstName(user.getFirstName())
                .publicId(user.getPublicId())
                .imageUrl(user.getImageUrl())
                .lastSeen(user.getLastSeen())
                .build();
    }

    public static List<RestUserForConversation> from(Set<User> users) {
        return users.stream()
                .map(RestUserForConversation::from)
                .toList();
    }

    public static RestUserForConversation from(UserResponse userResponse) {
        return RestUserForConversation.builder()
                .publicId(userResponse.getPublicId())
                .firstName(userResponse.getFirstName())
                .lastName(userResponse.getLastName())
                .imageUrl(userResponse.getImageUrl())
                .lastSeen(userResponse.getLastSeen())
                .build();
    }
}
