package com.omar.chatappback.services.MessageServices;

import com.omar.chatappback.entities.Message;
import com.omar.chatappback.message.ConversationPublicId;
import com.omar.chatappback.message.ConversationViewedForNotification;
import com.omar.chatappback.state.State;
import com.omar.chatappback.user.UserPublicId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MessageChangeNotifierImpl implements MessageChangeNotifier {
    @Override
    public State<Void, String> send(Message message, List<UserPublicId> userToNotify) {
        return null;
    }

    @Override
    public State<Void, String> delete(UUID conversationPublicId, List<UUID> userToNotify) {
        return null;
    }

    @Override
    public State<Void, String> view(ConversationViewedForNotification conversationViewedForNotification, List<UserPublicId> usersToNotify) {
        return null;
    }
}
