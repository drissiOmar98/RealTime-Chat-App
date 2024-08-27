package com.omar.chatappback.repositories;

import com.omar.chatappback.entities.Message;
import com.omar.chatappback.message.MessageSendState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MessageRepository extends JpaRepository<Message,Long> {

    @Modifying
    @Query("UPDATE Message  message SET message.sendState = :sendState " +
            "WHERE message.conversation.publicId = :conversationId " +
            "AND message.sender.publicId != :userPublicId " +
            "AND :userPublicId IN (SELECT user.publicId FROM message.conversation.users user) " +
            "AND message.sendState = 'RECEIVED'")
    int updateMessageSendState(UUID conversationId, UUID userPublicId, MessageSendState sendState);

    @Query("SELECT message FROM Message message " +
            "WHERE message.conversation.publicId = :conversationId " +
            "AND message.sender.publicId != :userPublicId " +
            "AND :userPublicId IN (SELECT user.publicId FROM message.conversation.users user) " +
            "AND message.sendState = 'RECEIVED'")
    List<Message> findMessageToUpdateSendState(UUID conversationId, UUID userPublicId);

}
