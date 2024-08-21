package com.omar.chatappback.dto.conversation;

import com.omar.chatappback.dto.message.RestMessage;
import com.omar.chatappback.message.ConversationName;
import com.omar.chatappback.user.UserPublicId;
import lombok.Builder;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Builder
public record RestConversationToCreate(Set<UUID> members, String name) {

    public static ConversationToCreate toConversationToCreate(
            RestConversationToCreate restConversationToCreate) {

        Set<UUID> userPublicIds = restConversationToCreate.members;

        // Convert String name to ConversationName
        ConversationName conversationName = new ConversationName(restConversationToCreate.name);

        // Create and return ConversationToCreate
        return new ConversationToCreate(userPublicIds, conversationName);
    }

}
