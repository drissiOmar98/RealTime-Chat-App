package com.omar.chatappback.mappers;


import com.omar.chatappback.dto.message.MessageResponse;

import com.omar.chatappback.entities.Conversation;
import com.omar.chatappback.entities.Message;
import com.omar.chatappback.entities.MessageContentBinary;
import com.omar.chatappback.entities.User;

import com.omar.chatappback.message.MessageType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MessageMapper {




    /*public MessageResponse toMessageResponse(Message message) {
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
    }*/

    public MessageResponse toMessageResponse(Message message) {
        MessageResponse.MessageResponseBuilder messageResponseBuilder = MessageResponse.builder()
                .publicId(message.getPublicId())
                .conversationId(message.getConversation().getPublicId())
                .state(message.getSendState())
                .sendDate(message.getSendTime())
                .senderId(message.getSender().getPublicId());

        if (message.getType().equals(MessageType.TEXT)) {
            // If the message is of type TEXT, map the text content and set type to TEXT
            messageResponseBuilder.textContent(message.getText())
                    .type(MessageType.TEXT);
        } else {
            // If the message contains media, map the media content and its MIME type
            if (message.getContentBinary() != null) {
                messageResponseBuilder.mediaContent(message.getContentBinary().getFile())
                        .mimeType(message.getContentBinary().getFileContentType());
            }
            messageResponseBuilder.textContent(message.getText())
                    .type(message.getType());
        }

        return messageResponseBuilder.build();
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
