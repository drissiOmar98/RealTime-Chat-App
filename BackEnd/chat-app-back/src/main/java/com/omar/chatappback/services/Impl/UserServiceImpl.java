package com.omar.chatappback.services.Impl;

import com.omar.chatappback.dto.user.UserResponse;
import com.omar.chatappback.entities.User;
import com.omar.chatappback.mappers.UserMapper;
import com.omar.chatappback.message.ConversationPublicId;
import com.omar.chatappback.repositories.UserRepository;
import com.omar.chatappback.services.UserService;
import com.omar.chatappback.user.UserPublicId;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;


    @Override
    public void save(User user) {

        if (user.getId() != null) { // Check if the user has an ID (for updates)
            Optional<User> userToUpdateOpt = userRepository.findById(user.getId());
            if (userToUpdateOpt.isPresent()) {
                User userToUpdate = userToUpdateOpt.get();
                userToUpdate.updateFromUser(user); // Update the fields
                userRepository.saveAndFlush(userToUpdate); // Save the updated entity
            }
        } else {
            userRepository.save(user); // Save the new user
        }

    }

    @Override
    public Optional<User> get(UserPublicId userPublicId) {
        return Optional.empty();
    }

    @Override
    public Optional<User> getOneByEmail(String userEmail) {
        return userRepository.findByEmail(userEmail);
    }

    @Override
    public List<UserResponse> getByPublicIds(Set<UUID> userPublicIds) {
        List<UUID> rawPublicIds = userPublicIds.stream().toList();
        return userRepository.findByPublicIdIn(rawPublicIds)
                .stream()
                .map(userMapper::toUserResponse)
                .toList();
    }

    @Override
    public Page<UserResponse> search(Pageable pageable, String query) {
        Page<User> userPage = userRepository.search(pageable, query);
        return userPage.map(userMapper::toUserResponse);
    }

    @Override
    public int updateLastSeenByPublicId(UUID userPublicId, Instant lastSeen) {
        return userRepository.updateLastSeen(userPublicId,lastSeen);
    }

    @Override
    public List<UserResponse> getRecipientByConversationIdExcludingReader(UUID conversationPublicId, UUID readerPublicId) {
        // Fetch users from the repository excluding the reader
        List<User> users = userRepository.findByConversationsPublicIdAndPublicIdIsNot(
                conversationPublicId,
                readerPublicId
        );
        // Map the list of User to UserResponse objects
        return users.stream()
                .map(userMapper::toUserResponse)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<UserResponse> getOneByPublicId(UUID userPublicId) {
        return userRepository.findOneByPublicId(userPublicId)
                .map(userMapper::toUserResponse);

    }
}
