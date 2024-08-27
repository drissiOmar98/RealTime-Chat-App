package com.omar.chatappback.services.MessageServices;



import com.omar.chatappback.entities.Conversation;
import com.omar.chatappback.entities.Message;
import com.omar.chatappback.entities.MessageContentBinary;
import com.omar.chatappback.entities.User;
import com.omar.chatappback.message.MessageSendNew;
import com.omar.chatappback.message.MessageSendState;
import com.omar.chatappback.services.ConversationServices.ConversationService;
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


    private final ConversationService conversationService;


    public State<Message, String> create(MessageSendNew messageSendNew, User sender) {
        Optional<Conversation> conversationToLink = conversationService.getConversationByPublicId(messageSendNew.conversationPublicId());
        State<Message, String> creationState;

        if (conversationToLink.isPresent()) {
            Conversation conversation = conversationToLink.get();
            Message newMessage = Message.builder()
                    .text(messageSendNew.messageContent().text())    // Set the text content
                    .type(messageSendNew.messageContent().type())
                    .publicId(UUID.randomUUID())                      // Generate a random publicId
                    .sendState(MessageSendState.RECEIVED)             // Set the send state
                    .sendTime(Instant.now())
                    .conversation(conversation) // Set the Conversation object
                    .sender(sender)
                    .build();

            // Check if there is media content to add to the message
            if (messageSendNew.messageContent().media() != null) {
                MessageContentBinary contentBinary = MessageContentBinary.builder()
                        .file(messageSendNew.messageContent().media().file())  // Set the binary content file
                        .fileContentType(messageSendNew.messageContent().media().mimetype())  // Set the MIME type
                        .build();
                newMessage.setContentBinary(contentBinary);
            }

            Message messageSaved = messageService.save(newMessage, sender, conversation);
            // Notify users
            messageChangeNotifier.send(newMessage, conversation.getUsers().stream()
                    .map(User::getPublicId).toList());

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
