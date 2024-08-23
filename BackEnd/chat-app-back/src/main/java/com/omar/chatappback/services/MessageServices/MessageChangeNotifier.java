package com.omar.chatappback.services.MessageServices;

import com.omar.chatappback.entities.Message;

import com.omar.chatappback.message.ConversationViewedForNotification;
import com.omar.chatappback.state.State;


import java.util.List;
import java.util.UUID;

public interface MessageChangeNotifier {

    State<Void, String> send(Message message, List<UUID> userToNotify);

    State<Void, String> delete(UUID conversationPublicId, List<UUID> userToNotify);

    State<Void, String> view(ConversationViewedForNotification conversationViewedForNotification, List<UUID> usersToNotify);
}
