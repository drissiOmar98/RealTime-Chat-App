package com.omar.chatappback.message;



import lombok.Builder;

import java.util.UUID;

@Builder
public record MessageSendNew(MessageContent messageContent,
                             UUID conversationPublicId) {
}
