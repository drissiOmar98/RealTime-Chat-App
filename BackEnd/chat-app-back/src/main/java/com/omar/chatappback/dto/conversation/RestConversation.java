package com.omar.chatappback.dto.conversation;

import com.omar.chatappback.dto.message.RestMessage;
import com.omar.chatappback.entities.Conversation;
import lombok.Builder;
import lombok.Value;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Value
@Builder
public class RestConversation {

    UUID publicId;
    String name;
    List<RestUserForConversation> members;
    List<RestMessage> messages;

    public static RestConversation from(Conversation conversation) {
        return RestConversation.builder()
                .publicId(conversation.getPublicId())
                .name(conversation.getName())
                .members(RestUserForConversation.from(conversation.getUsers()))
                .messages(RestMessage.from(conversation.getMessages()))
                .build();
    }

    // Convert from ConversationResponse DTO
    public static RestConversation from(ConversationResponse conversationResponse) {
        return RestConversation.builder()
                .publicId(conversationResponse.getPublicId())
                .name(conversationResponse.getName())
                .members(conversationResponse.getUsers().stream()
                        .map(RestUserForConversation::from)
                        .collect(Collectors.toList()))
                .messages(
                        (conversationResponse.getMessages() == null ?
                                List.of() : // Return an empty list if messages is null
                                conversationResponse.getMessages().stream()
                                        .map(RestMessage::from)
                                        .collect(Collectors.toList()))
                )
                .build();
    }



}
