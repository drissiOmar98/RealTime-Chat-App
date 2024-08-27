package com.omar.chatappback.message;


import java.util.List;
import java.util.UUID;

public record MessageIdWithUsers(ConversationViewedForNotification conversationViewedForNotification,
                                 List<UUID> usersToNotify) {
}
