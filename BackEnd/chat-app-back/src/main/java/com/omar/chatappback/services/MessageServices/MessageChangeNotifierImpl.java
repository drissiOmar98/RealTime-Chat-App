package com.omar.chatappback.services.MessageServices;

import com.omar.chatappback.dto.message.RestMessage;
import com.omar.chatappback.entities.Message;
import com.omar.chatappback.message.ConversationViewedForNotification;
import com.omar.chatappback.message.MessageIdWithUsers;
import com.omar.chatappback.message.MessageWithUsers;
import com.omar.chatappback.notification.ConversationIdWithUsers;
import com.omar.chatappback.notification.NotificationEventName;
import com.omar.chatappback.notification.NotificationService;
import com.omar.chatappback.state.State;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MessageChangeNotifierImpl implements MessageChangeNotifier {

    private final NotificationService notificationService;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;


    @Override
    public State<Void, String> send(Message message, List<UUID> userToNotify) {
        MessageWithUsers messageWithUsers = new MessageWithUsers(message, userToNotify);
        applicationEventPublisher.publishEvent(messageWithUsers);
        return State.<Void, String>builder().forSuccess();
    }

    @Override
    public State<Void, String> delete(UUID conversationPublicId, List<UUID> userToNotify) {
        ConversationIdWithUsers conversationIdWithUsers = new ConversationIdWithUsers(conversationPublicId, userToNotify);
        applicationEventPublisher.publishEvent(conversationIdWithUsers);
        return State.<Void, String>builder().forSuccess();
    }

    @Override
    public State<Void, String> view(ConversationViewedForNotification conversationViewedForNotification, List<UUID> usersToNotify) {
        MessageIdWithUsers messageIdWithUsers = new MessageIdWithUsers(conversationViewedForNotification, usersToNotify);
        applicationEventPublisher.publishEvent(messageIdWithUsers);
        return State.<Void, String>builder().forSuccess();
    }

    @EventListener
    public void handleNewMessage(MessageWithUsers messageWithUsers) {
        notificationService.sendMessage(RestMessage.from(messageWithUsers.message()),
                messageWithUsers.userToNotify(), NotificationEventName.NEW_MESSAGE);
    }

    @EventListener
    public void handleDeleteConversation(ConversationIdWithUsers conversationIdWithUsers) {
        notificationService.sendMessage(conversationIdWithUsers.conversationPublicId(),
                conversationIdWithUsers.users(), NotificationEventName.DELETE_CONVERSATION);
    }

    @EventListener
    public void handleView(MessageIdWithUsers messageIdWithUsers) {
        notificationService.sendMessage(messageIdWithUsers.conversationViewedForNotification(),
                messageIdWithUsers.usersToNotify(), NotificationEventName.VIEWS_MESSAGES);
    }

}
