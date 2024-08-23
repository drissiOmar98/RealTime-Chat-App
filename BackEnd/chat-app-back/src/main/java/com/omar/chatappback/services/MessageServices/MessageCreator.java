package com.omar.chatappback.services.MessageServices;


import com.omar.chatappback.dto.conversation.ConversationResponse;
import com.omar.chatappback.dto.user.UserResponse;
import com.omar.chatappback.entities.Message;
import com.omar.chatappback.entities.MessageContentBinary;
import com.omar.chatappback.entities.User;
import com.omar.chatappback.mappers.ConversationMapper;
import com.omar.chatappback.mappers.UserMapper;
import com.omar.chatappback.message.MessageSendNew;
import com.omar.chatappback.message.MessageSendState;
import com.omar.chatappback.services.ConversationServices.ConversationReader;
import com.omar.chatappback.services.ConversationServices.ConversationService;
import com.omar.chatappback.services.UserReader;
import com.omar.chatappback.state.State;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class MessageCreator {

    private final MessageService messageService;

    private final MessageChangeNotifier messageChangeNotifier;

    private final ConversationReader conversationReader;

    private final ConversationMapper conversationMapper;

    private final ConversationService conversationService;

    private final UserMapper userMapper;

    private final UserReader userReader;


    public State<Message, String> create(MessageSendNew messageSendNew, User sender) {
        Message newMessage = Message.builder()
                .text(messageSendNew.messageContent().text())    // Set the text content
                .type(messageSendNew.messageContent().type())
                .publicId(UUID.randomUUID())                      // Generate a random publicId
                .sendState(MessageSendState.RECEIVED)             // Set the send state
                .sendTime(Instant.now())
                .conversation(conversationMapper.toConversation(
                        conversationReader.getOneByPublicId(messageSendNew.conversationPublicId()).get())) // Set the conversation
                .sender(userMapper.toUser(
                        userReader.getByPublicId(sender.getPublicId())
                                .orElseThrow(() -> new RuntimeException("Sender not found"))))
                .build();

        // Check if there is media content to add to the message
        if (messageSendNew.messageContent().media() != null) {
            MessageContentBinary contentBinary = MessageContentBinary.builder()
                    .file(messageSendNew.messageContent().media().file())  // Set the binary content file
                    .fileContentType(messageSendNew.messageContent().media().mimetype())  // Set the MIME type
                    .build();
            newMessage.setContentBinary(contentBinary);
        }

        State<Message, String> creationState;
        Optional<ConversationResponse> conversationToLink = conversationReader.getOneByPublicId(messageSendNew.conversationPublicId());
        if (conversationToLink.isPresent()) {
            Message messageSaved = messageService.save(newMessage, sender, conversationMapper.toConversation(conversationToLink.get()));
            // Notify users
            messageChangeNotifier.send(newMessage, conversationToLink.get().getUsers().stream()
                    .map(UserResponse::getPublicId).toList());
            creationState = State.<Message, String>builder().forSuccess(messageSaved);
        } else {
            creationState = State.<Message, String>builder().forError(
                    String.format("Unable to find the conversation to link the message with the " +
                            "following publicId %s", messageSendNew.conversationPublicId())
            );
        }
        return creationState;
    }



}
