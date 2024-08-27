package com.omar.chatappback.services.ConversationServices;

import com.omar.chatappback.dto.conversation.ConversationResponse;
import com.omar.chatappback.dto.conversation.ConversationToCreate;
import com.omar.chatappback.entities.Conversation;
import com.omar.chatappback.entities.User;
import com.omar.chatappback.services.UserUtilityService;
import com.omar.chatappback.state.State;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ConversationUtilityService {

    private final ConversationCreator conversationCreator;
    private final ConversationReader conversationReader;
    private final ConversationDeleter conversationDeleter;
    private final UserUtilityService userUtilityService;
    private final ConversationViewed conversationViewed;


    @Transactional
    public State<Conversation, String> create(ConversationToCreate conversation) {
        User authenticatedUser = userUtilityService.getAuthenticatedUser();
        return conversationCreator.create(conversation, authenticatedUser);
    }

    @Transactional(readOnly = true)
    public List<ConversationResponse> getAllByConnectedUser(Pageable pageable) {
        User authenticatedUser = userUtilityService.getAuthenticatedUser();
        return this.conversationReader.getAllByUserPublicID(authenticatedUser.getPublicId(), pageable)
                .stream().toList();
    }

    @Transactional
    public State<UUID, String> delete(UUID conversationPublicId) {
        User authenticatedUser = userUtilityService.getAuthenticatedUser();
        return this.conversationDeleter.deleteById(conversationPublicId, authenticatedUser);
    }

    @Transactional(readOnly = true)
    public Optional<ConversationResponse> getOneByConversationId(UUID conversationPublicId) {
        User authenticatedUser = userUtilityService.getAuthenticatedUser();
        return this.conversationReader.getOneByPublicIdAndUserId(conversationPublicId, authenticatedUser.getPublicId());
    }

    @Transactional
    public State<Integer, String> markConversationAsRead(UUID conversationPublicId) {
        User authenticatedUser = userUtilityService.getAuthenticatedUser();
        return conversationViewed.markAsRead(conversationPublicId, authenticatedUser.getPublicId());
    }

}






