package com.omar.chatappback.mappers;


import com.omar.chatappback.dto.message.MessageResponse;

import com.omar.chatappback.entities.Conversation;
import com.omar.chatappback.entities.Message;
import com.omar.chatappback.entities.MessageContentBinary;
import com.omar.chatappback.entities.User;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MessageMapper {




    public MessageResponse toMessageResponse(Message message) {
        return MessageResponse.builder()
                .publicId(message.getPublicId())
                .sendDate(message.getSendTime())
                .textContent(message.getText())
                .type(message.getType())
                .state(message.getSendState())
                .conversationId(message.getConversation().getPublicId())
                .senderId(message.getSender().getPublicId())
                .mediaContent(Optional.ofNullable(message.getContentBinary())
                        .map(MessageContentBinary::getFile)
                        .orElse(null))
                .mimeType(Optional.ofNullable(message.getContentBinary())
                        .map(MessageContentBinary::getFileContentType)
                        .orElse(null))
                .build();
    }




    public Message toMessage(MessageResponse messageResponse) {
        // Create default or placeholder User and Conversation
        User defaultUser = new User(); // Initialize with default values if needed
        defaultUser.setPublicId(messageResponse.getSenderId());

        Conversation defaultConversation = new Conversation(); // Initialize with default values if needed
        defaultConversation.setPublicId(messageResponse.getConversationId());

        return Message.builder()
                .publicId(messageResponse.getPublicId())
                .sendTime(messageResponse.getSendDate())
                .text(messageResponse.getTextContent())
                .type(messageResponse.getType())
                .sendState(messageResponse.getState())
                .sender(defaultUser)
                .conversation(defaultConversation)
                .contentBinary(messageResponse.getMediaContent() != null
                        ? MessageContentBinary.builder()
                        .file(messageResponse.getMediaContent())
                        .fileContentType(messageResponse.getMimeType())
                        .build()
                        : null)
                .build();
    }



}
