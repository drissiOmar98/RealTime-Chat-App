package com.omar.chatappback.dto.user;


import lombok.*;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {

    private  String lastName;
    private  String firstName;
    private  String email;
    private  String imageUrl;
    private  UUID publicId;
    private  Instant lastSeen;
    private  Instant createdDate;
    private  Set<String> authorities;
}
