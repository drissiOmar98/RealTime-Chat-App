package com.omar.chatappback.mappers;


import com.omar.chatappback.dto.conversation.ConversationResponse;
import com.omar.chatappback.entities.Conversation;
import com.omar.chatappback.entities.Message;
import com.omar.chatappback.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ConversationMapper {

    public final UserMapper userMapper;

    public final MessageMapper messageMapper;


    public ConversationResponse toConversationResponse(Conversation conversation) {
        return ConversationResponse.builder()
                .publicId(conversation.getPublicId())
                .name(conversation.getName())
                .messages(
                        conversation.getMessages().stream()
                                .map(messageMapper::toMessageResponse)
                                .collect(Collectors.toSet())
                )
                .users(
                        conversation.getUsers().stream()
                                .map(userMapper::toUserResponse)
                                .collect(Collectors.toSet())
                )
                .build();
    }

    /*public  Conversation toConversation(ConversationResponse response) {
        return Conversation.builder()
                .publicId(response.getPublicId())
                .name(response.getName())
               .messages(response.getMessages()
                        .stream()
                       .map(messageMapper::toMessage)
                        .collect(Collectors.toSet()))
                .users(response.getUsers()
                        .stream()
                        .map(userMapper::toUser)
                        .collect(Collectors.toSet()))
                .build();
    }*/

   /* public Conversation toConversation(ConversationResponse response) {
        Conversation conversation = Conversation.builder()
                .publicId(response.getPublicId())
                .name(response.getName())
                .build();

        Set<Message> messages = response.getMessages()
                .stream()
                .map(messageResponse -> {
                    Message message = messageMapper.toMessage(messageResponse);
                    message.setConversation(conversation); // Set the actual conversation
                    return message;
                })
                .collect(Collectors.toSet());

        Set<User> users = response.getUsers()
                .stream()
                .map(userMapper::toUser)
                .collect(Collectors.toSet());

        conversation.setMessages(messages);
        conversation.setUsers(users);

        return conversation;
    }*/


}
