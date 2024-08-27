package com.omar.chatappback.services;

import com.omar.chatappback.dto.user.UserResponse;
import com.omar.chatappback.entities.User;
import com.omar.chatappback.message.ConversationPublicId;
import com.omar.chatappback.user.UserPublicId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface UserService {

    void save(User user);

    Optional<User> get(UserPublicId userPublicId);

    Optional<User> getOneByEmail(String userEmail);

    List<UserResponse> getByPublicIds(Set<UUID> userPublicIds);

    Page<UserResponse> search(Pageable pageable, String query);

    int updateLastSeenByPublicId(UUID userPublicId, Instant lastSeen);

    List<UserResponse> getRecipientByConversationIdExcludingReader(UUID conversationPublicId, UUID readerPublicId);

    Optional<UserResponse> getOneByPublicId(UUID userPublicId);

    Optional<User> getUserByPublicId(UUID userPublicId);


}
