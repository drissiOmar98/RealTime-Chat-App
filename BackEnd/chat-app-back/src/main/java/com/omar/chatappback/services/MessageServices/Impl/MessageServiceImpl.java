package com.omar.chatappback.services.MessageServices.Impl;

import com.omar.chatappback.dto.conversation.ConversationResponse;
import com.omar.chatappback.dto.message.MessageResponse;
import com.omar.chatappback.entities.Conversation;
import com.omar.chatappback.entities.Message;
import com.omar.chatappback.entities.User;
import com.omar.chatappback.mappers.MessageMapper;
import com.omar.chatappback.message.MessageSendState;
import com.omar.chatappback.message.MessageType;
import com.omar.chatappback.repositories.MessageBinaryContentRepository;
import com.omar.chatappback.repositories.MessageRepository;
import com.omar.chatappback.services.MessageServices.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;

    private final MessageBinaryContentRepository messageBinaryContentRepository;

    private final MessageMapper messageMapper;



    @Override
    public Message save(Message message, User sender, Conversation conversation) {
        message.setSender(sender);
        message.setConversation(conversation);

        // Check if the message type is not TEXT (indicating it has binary content)
        if (message.getType() != MessageType.TEXT) {
            // Save the binary content associated with the message
            // This is necessary for messages that include files, images, or videos
            messageBinaryContentRepository.save(message.getContentBinary());
        }
        // Save the message entity to the database
        // This persists the message along with its properties and relationships
        return messageRepository.save(message);

    }

    @Override
    public int updateMessageSendState(UUID conversationPublicId, UUID userPublicId, MessageSendState state) {
        return messageRepository.updateMessageSendState(conversationPublicId, userPublicId, state);
    }

    @Override
    public List<MessageResponse> findMessageToUpdateSendState(UUID conversationPublicId, UUID userPublicId) {
        List<Message> messagesToUpdate = messageRepository.findMessageToUpdateSendState(conversationPublicId, userPublicId);
        // Map the Message entities to MessageResponse DTOs
        return messagesToUpdate.stream()
                .map(messageMapper::toMessageResponse)
                .toList(); // Collect as a list

    }
}
