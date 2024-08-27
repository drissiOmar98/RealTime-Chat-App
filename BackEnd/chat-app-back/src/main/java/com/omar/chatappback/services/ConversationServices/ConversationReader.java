package com.omar.chatappback.services.ConversationServices;

import com.omar.chatappback.dto.conversation.ConversationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ConversationReader {

    private final ConversationService conversationService;

    public Page<ConversationResponse> getAllByUserPublicID(UUID userPublicId, Pageable pageable){
        return conversationService.getConversationByUserPublicId(userPublicId, pageable);
    }

    public Optional<ConversationResponse> getOneByPublicId(UUID conversationPublicId) {
        return conversationService.getOneByPublicId(conversationPublicId);
    }

    public Optional<ConversationResponse> getOneByPublicIdAndUserId(UUID conversationPublicId, UUID userPublicId) {
        return conversationService.getConversationByUsersPublicIdAndPublicId(userPublicId, conversationPublicId);
    }


}
