package com.omar.chatappback.services.ConversationServices;


import com.omar.chatappback.dto.conversation.ConversationResponse;
import com.omar.chatappback.dto.conversation.ConversationToCreate;
import com.omar.chatappback.dto.user.UserResponse;
import com.omar.chatappback.entities.Conversation;
import com.omar.chatappback.entities.User;
import com.omar.chatappback.mappers.UserMapper;
import com.omar.chatappback.services.UserReader;
import com.omar.chatappback.state.State;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ConversationCreator {

    private final ConversationService conversationService;

    private final UserReader userReader;

    private final UserMapper userMapper;


    public State<Conversation, String> create(ConversationToCreate newConversation, User authenticatedUser) {
        // Add the authenticated user's public ID to the members of the new conversation
        newConversation.getMembers().add(authenticatedUser.getPublicId());
        // Fetch UserResponse objects for the public IDs in the new conversation
        List<UserResponse> memberResponses = userReader.getUsersByPublicId(newConversation.getMembers());

        // Map UserResponse to User entities
        List<User> members = memberResponses.stream()
                .map(userMapper::toUser)
                .toList();

        // Extract the public IDs of the members for checking if the conversation already exists
        List<UUID> membersUuids = members.stream().map(User::getPublicId).toList();

        // Check if a conversation with the same members already exists
        Optional<ConversationResponse> conversationAlreadyPresent = conversationService.getConversationByUserPublicIds(membersUuids);
        State<Conversation, String> stateResult;
        if (conversationAlreadyPresent.isEmpty()) {
            // Save the new conversation if it doesn't already exist
            Conversation newConversationSaved = conversationService.save(newConversation, members);
            stateResult = State.<Conversation, String>builder().forSuccess(newConversationSaved);
        } else {
            // Return an error state if the conversation already exists
            stateResult = State.<Conversation, String>builder().forError("This conversation already exists");
        }
        return stateResult;
    }


}
