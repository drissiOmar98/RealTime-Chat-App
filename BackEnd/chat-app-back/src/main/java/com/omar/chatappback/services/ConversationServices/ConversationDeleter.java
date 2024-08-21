package com.omar.chatappback.services.ConversationServices;


import com.omar.chatappback.dto.conversation.ConversationResponse;
import com.omar.chatappback.dto.user.UserResponse;
import com.omar.chatappback.entities.User;
import com.omar.chatappback.services.MessageServices.MessageChangeNotifier;
import com.omar.chatappback.state.State;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ConversationDeleter {

    private final ConversationService conversationService;

    private final MessageChangeNotifier messageChangeNotifier;

    public State<UUID, String> deleteById(UUID conversationId, User connectedUser) {
        State<UUID, String> stateResult;

        Optional<ConversationResponse> conversationToDeleteOpt = this.conversationService.getConversationByUsersPublicIdAndPublicId(connectedUser.getPublicId(), conversationId);
        if (conversationToDeleteOpt.isPresent()) {
            this.conversationService.deleteByPublicId(connectedUser.getPublicId(), conversationId);
            messageChangeNotifier.delete(conversationId, conversationToDeleteOpt.get()
                    .getUsers().stream().map(UserResponse::getPublicId).toList());
            stateResult = State.<UUID, String>builder().forSuccess(conversationId);
        } else {
            stateResult = State.<UUID, String>builder().forError("This conversation doesn't belong to this user or doesn't exist");
        }
        return stateResult;
    }


}
