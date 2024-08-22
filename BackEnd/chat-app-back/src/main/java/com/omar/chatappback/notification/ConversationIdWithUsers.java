package com.omar.chatappback.notification;


import java.util.List;
import java.util.UUID;

public record ConversationIdWithUsers(UUID conversationPublicId,
                                      List<UUID> users) {
}
