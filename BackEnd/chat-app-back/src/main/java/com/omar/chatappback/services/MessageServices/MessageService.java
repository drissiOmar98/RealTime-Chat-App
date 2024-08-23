package com.omar.chatappback.services.MessageServices;

import com.omar.chatappback.dto.message.MessageResponse;
import com.omar.chatappback.entities.Conversation;
import com.omar.chatappback.entities.Message;
import com.omar.chatappback.entities.User;
import com.omar.chatappback.message.MessageSendState;

import java.util.List;
import java.util.UUID;

public interface MessageService {

    Message save(Message message, User sender, Conversation conversation);

    int updateMessageSendState(UUID conversationPublicId, UUID  userPublicId, MessageSendState state);

    List<MessageResponse> findMessageToUpdateSendState(UUID  conversationPublicId, UUID  userPublicId);
}
