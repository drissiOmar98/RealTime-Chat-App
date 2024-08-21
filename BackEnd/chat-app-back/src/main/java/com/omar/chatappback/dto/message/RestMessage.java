package com.omar.chatappback.dto.message;

import com.omar.chatappback.entities.Message;
import com.omar.chatappback.message.*;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;
import java.util.List;
import java.util.Set;
import java.util.UUID;


@Builder
@Getter
public class RestMessage {

    private String textContent;
    private Instant sendDate;
    private MessageSendState state;
    private UUID publicId;
    private UUID conversationId;
    private MessageType type;
    private byte[] mediaContent;
    private String mimeType;
    private UUID senderId;


    public static RestMessage from(Message message) {
        RestMessage.RestMessageBuilder builder = RestMessage.builder()
                .textContent(message.getText())
                .sendDate(message.getSendTime())
                .state(message.getSendState())
                .publicId(message.getPublicId())
                .conversationId(message.getConversation().getPublicId())
                .type(message.getType())
                .senderId(message.getSender().getPublicId());

        if (message.getType() != MessageType.TEXT && message.getContentBinary() != null) {
            builder.mediaContent(message.getContentBinary().getFile());
            builder.mimeType(message.getContentBinary().getFileContentType());
        }

        return builder.build();
    }

    public static RestMessage from(MessageResponse messageResponse) {
        RestMessageBuilder builder = RestMessage.builder()
                .textContent(messageResponse.getTextContent())
                .sendDate(messageResponse.getSendDate())
                .state(messageResponse.getState())
                .publicId(messageResponse.getPublicId())
                .conversationId(messageResponse.getConversationId())
                .type(messageResponse.getType())
                .senderId(messageResponse.getSenderId());

        if (messageResponse.getType() != MessageType.TEXT && messageResponse.getMediaContent() != null) {
            builder.mediaContent(messageResponse.getMediaContent());
            builder.mimeType(messageResponse.getMimeType());
        }

        return builder.build();
    }




    public static List<RestMessage> from(Set<Message> messages) {
        return messages.stream().map(RestMessage::from).toList();
    }

   /* public static MessageSendNew toDomain(RestMessage restMessage) {
        // Build MessageContent, handling optional media content
        MessageContent messageContent = MessageContent.builder()
                .type(restMessage.getType())
                .text(restMessage.getTextContent())
                .build();

        // If the message is not text, add media content to MessageContent
        if (restMessage.getType() != MessageType.TEXT && restMessage.getMediaContent() != null) {
            messageContent = messageContent.toBuilder()  // Create a new builder from the existing object
                    .media(new MessageMediaContent(restMessage.getMediaContent(), restMessage.getMimeType()))
                    .build();
        }

        // Return MessageSendNew with the constructed MessageContent and ConversationPublicId
        return MessageSendNew.builder()
                .messageContent(messageContent)
                .conversationPublicId(new ConversationPublicId(restMessage.getConversationId()))
                .build();
    }
*/

    public boolean hasMedia() {
        return !type.equals(MessageType.TEXT);
    }

    public void setMediaAttachment(byte[] file, String contentType) {
        this.mediaContent = file;
        this.mimeType = contentType;
    }
}
