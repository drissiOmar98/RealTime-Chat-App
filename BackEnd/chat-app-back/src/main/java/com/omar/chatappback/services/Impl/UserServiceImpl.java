package com.omar.chatappback.services.Impl;

import com.omar.chatappback.entities.User;
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

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;


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
    public List<User> getByPublicIds(Set<UserPublicId> userPublicIds) {
        return List.of();
    }

    @Override
    public Page<User> search(Pageable pageable, String query) {
        return null;
    }

    @Override
    public int updateLastSeenByPublicId(UserPublicId userPublicId, Instant lastSeen) {
        return 0;
    }

    @Override
    public List<User> getRecipientByConversationIdExcludingReader(ConversationPublicId conversationPublicId, UserPublicId readerPublicId) {
        return List.of();
    }

    @Override
    public Optional<User> getOneByPublicId(UserPublicId userPublicId) {
        return Optional.empty();
    }
}
