package com.omar.chatappback.services.Impl;

import com.omar.chatappback.entities.User;
import com.omar.chatappback.message.ConversationPublicId;
import com.omar.chatappback.services.UserService;
import com.omar.chatappback.user.UserPublicId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {


    @Override
    public void save(User user) {

    }

    @Override
    public Optional<User> get(UserPublicId userPublicId) {
        return Optional.empty();
    }

    @Override
    public Optional<User> getOneByEmail(String userEmail) {
        return Optional.empty();
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
