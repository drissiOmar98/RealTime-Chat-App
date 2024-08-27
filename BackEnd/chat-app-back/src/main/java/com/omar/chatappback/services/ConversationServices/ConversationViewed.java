package com.omar.chatappback.services.ConversationServices;

import com.omar.chatappback.dto.message.MessageResponse;
import com.omar.chatappback.dto.user.UserResponse;
import com.omar.chatappback.entities.Message;
import com.omar.chatappback.message.ConversationViewedForNotification;
import com.omar.chatappback.message.MessageSendState;
import com.omar.chatappback.services.MessageServices.MessageChangeNotifier;
import com.omar.chatappback.services.MessageServices.MessageService;
import com.omar.chatappback.services.UserReader;
import com.omar.chatappback.state.State;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ConversationViewed {

    private final UserReader userReader;

    private final MessageChangeNotifier messageChangeNotifier;

    private final MessageService messageService;


    public State<Integer, String> markAsRead(UUID conversationPublicId, UUID connectedUserPublicId) {
        List<MessageResponse> messageToUpdateSendState = messageService.findMessageToUpdateSendState(conversationPublicId, connectedUserPublicId);
        int nbUpdatedMessages = messageService.updateMessageSendState(conversationPublicId, connectedUserPublicId, MessageSendState.READ);
        List<UUID> usersToNotify = userReader.findUsersToNotify(conversationPublicId, connectedUserPublicId)
                .stream().map(UserResponse::getPublicId).toList();
        ConversationViewedForNotification conversationViewedForNotification = new ConversationViewedForNotification(conversationPublicId,
                messageToUpdateSendState.stream().map(MessageResponse::getPublicId).toList());
        messageChangeNotifier.view(conversationViewedForNotification, usersToNotify);

        if (nbUpdatedMessages > 0) {
            return State.<Integer, String>builder().forSuccess(nbUpdatedMessages);
        } else {
            return State.<Integer, String>builder().forSuccess();
        }
    }


}
