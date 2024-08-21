package com.omar.chatappback.dto.message;


import com.omar.chatappback.message.MessageSendState;
import com.omar.chatappback.message.MessageType;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageResponse {


    private UUID publicId;
    private Instant sendDate;
    private String textContent;
    private MessageType type;
    private MessageSendState state;
    private UUID conversationId;
    private UUID senderId;
    private byte[] mediaContent;
    private String mimeType;
}
