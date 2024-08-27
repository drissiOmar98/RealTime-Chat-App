package com.omar.chatappback.services.ConversationServices.Impl;

import com.omar.chatappback.dto.conversation.ConversationResponse;
import com.omar.chatappback.dto.conversation.ConversationToCreate;
import com.omar.chatappback.entities.Conversation;
import com.omar.chatappback.entities.User;
import com.omar.chatappback.mappers.ConversationMapper;
import com.omar.chatappback.repositories.ConversationRepository;
import com.omar.chatappback.repositories.UserRepository;
import com.omar.chatappback.services.ConversationServices.ConversationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
@RequiredArgsConstructor
public class ConversationServiceImpl implements ConversationService {

    private final ConversationRepository conversationRepository;

    private final ConversationMapper conversationMapper;

    private final UserRepository userRepository;


    @Override
    public Conversation save(ConversationToCreate conversation, List<User> members) {
        // Create a set to keep track of managed users
        Set<User> managedUsers = new HashSet<>();

        for (User member : members) {
            if (member.getEmail() != null) {
                // Check if the user already exists by email
                Optional<User> existingUserOpt = userRepository.findByEmail(member.getEmail());

                if (existingUserOpt.isPresent()) {
                    // Use the existing user if found
                    managedUsers.add(existingUserOpt.get());
                } else {
                    // Save the new user if not found
                    User savedUser = userRepository.save(member);
                    managedUsers.add(savedUser);
                }
            }
        }

        // Create a new Conversation entity
        Conversation newConversation = Conversation.builder()
                .name(conversation.getName().name())  // Assuming ConversationName has a getValue() method
                .users(managedUsers)  // Use managed users
                .build();

        // Save the new conversation entity
        return conversationRepository.saveAndFlush(newConversation);
    }


    @Override
    public Optional<ConversationResponse> get(UUID conversationPublicId) {
        return conversationRepository.findOneByPublicId(conversationPublicId)
                .map(conversationMapper::toConversationResponse);
    }


    @Override
    public Page<ConversationResponse> getConversationByUserPublicId(UUID publicId, Pageable pageable) {
        return conversationRepository.findAllByUsersPublicId(publicId,pageable)
                .map(conversationMapper::toConversationResponse);
    }

    @Override
    public int deleteByPublicId(UUID userPublicId, UUID conversationPublicId) {
        return conversationRepository.deleteByUsersPublicIdAndPublicId(userPublicId,conversationPublicId);
    }

    @Override
    public Optional<ConversationResponse> getConversationByUsersPublicIdAndPublicId(UUID userPublicId, UUID conversationPublicId) {
        return conversationRepository.findOneByUsersPublicIdAndPublicId(userPublicId,conversationPublicId)
                .map(conversationMapper::toConversationResponse);
    }

    @Override
    public Optional<ConversationResponse> getConversationByUserPublicIds(List<UUID> publicIds) {
        Optional<Conversation> conversationOptional = conversationRepository.findOneByUsersPublicIdIn(publicIds, publicIds.size());
        return conversationOptional.map(conversationMapper::toConversationResponse);
    }

    @Override
    public Optional<ConversationResponse> getOneByPublicId(UUID conversationPublicId) {
        return conversationRepository.findOneByPublicId(conversationPublicId)
                .map(conversationMapper::toConversationResponse);
    }

    @Override
    public Optional<Conversation> getConversationByPublicId(UUID conversationPublicId) {
        return conversationRepository.findOneByPublicId(conversationPublicId);
    }
}
