package com.omar.chatappback.services.ConversationServices;


import com.omar.chatappback.dto.conversation.ConversationResponse;
import com.omar.chatappback.dto.conversation.ConversationToCreate;
import com.omar.chatappback.entities.Conversation;
import com.omar.chatappback.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ConversationService {

    Conversation save(ConversationToCreate conversation, List<User> members);

    Optional<ConversationResponse> get(UUID conversationPublicId);

    Page<ConversationResponse> getConversationByUserPublicId(UUID publicId, Pageable pageable);

    int deleteByPublicId(UUID userPublicId, UUID conversationPublicId);

    Optional<ConversationResponse> getConversationByUsersPublicIdAndPublicId(UUID userPublicId, UUID conversationPublicId);

    Optional<ConversationResponse> getConversationByUserPublicIds(List<UUID> publicIds);

    Optional<ConversationResponse> getOneByPublicId(UUID conversationPublicId);

    Optional<Conversation> getConversationByPublicId(UUID conversationPublicId);




}
