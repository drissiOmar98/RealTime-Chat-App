package com.omar.chatappback.mappers;


import com.omar.chatappback.dto.conversation.ConversationResponse;
import com.omar.chatappback.entities.Conversation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ConversationMapper {

    public final UserMapper userMapper;


    public ConversationResponse toConversationResponse(Conversation conversation) {
        return ConversationResponse.builder()
                .publicId(conversation.getPublicId())
                .name(conversation.getName())
                /*.messages(
                        conversation.getMessages().stream()
                                .map(MessageMapper::toMessageResponse)
                                .collect(Collectors.toSet())
                )*/
                .users(
                        conversation.getUsers().stream()
                                .map(userMapper::toUserResponse)
                                .collect(Collectors.toSet())
                )
                .build();
    }

    public  Conversation toConversation(ConversationResponse response) {
        return Conversation.builder()
                //.id(response.getId())
                .publicId(response.getPublicId())
                .name(response.getName())
                /*.messages(response.getMessages()
                        .stream()
                        .map(MessageMapper::toEntity)
                        .collect(Collectors.toSet()))*/
                .users(response.getUsers()
                        .stream()
                        .map(userMapper::toUser)
                        .collect(Collectors.toSet()))
                .build();
    }

}
