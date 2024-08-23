package com.omar.chatappback.message;


import com.omar.chatappback.entities.Message;

import java.util.List;
import java.util.UUID;

public record MessageWithUsers(Message message, List<UUID> userToNotify) {
}
