package com.omar.chatappback.services;

import com.omar.chatappback.dto.user.UserResponse;
import com.omar.chatappback.entities.User;
import com.omar.chatappback.message.ConversationPublicId;
import com.omar.chatappback.user.UserPublicId;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserReader {

    private final UserService userService;

    public Optional<User> getByEmail(String email) {
        return userService.getOneByEmail(email);
    }

    public List<UserResponse> getUsersByPublicId(Set<UUID> publicIds) {
        return userService.getByPublicIds(publicIds);
    }

    public Page<UserResponse> search(Pageable pageable, String query) {
        return userService.search(pageable, query);
    }

    public Optional<UserResponse> getByPublicId(UUID publicId) {
        return userService.getOneByPublicId(publicId);
    }

    public List<UserResponse> findUsersToNotify(UUID conversationPublicId, UUID readerPublicId) {
        return userService.getRecipientByConversationIdExcludingReader(conversationPublicId, readerPublicId);
    }






}
