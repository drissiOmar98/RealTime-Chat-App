package com.omar.chatappback.dto.conversation;


import com.omar.chatappback.dto.message.MessageResponse;
import com.omar.chatappback.dto.user.UserResponse;
import lombok.*;

import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ConversationResponse {

    private UUID publicId;
    private String name;
    private Set<MessageResponse> messages;
    private Set<UserResponse> users;

}
