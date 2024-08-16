package com.omar.chatappback.services;

import com.omar.chatappback.entities.User;
import com.omar.chatappback.message.ConversationPublicId;
import com.omar.chatappback.repositories.UserRepository;
import com.omar.chatappback.user.UserEmail;
import com.omar.chatappback.user.UserPublicId;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserReader {

    private final UserService userService;

    public Optional<User> getByEmail(String email) {
        return userService.getOneByEmail(email);
    }

    public List<User> getUsersByPublicId(Set<UserPublicId> publicIds) {
        return userService.getByPublicIds(publicIds);
    }

    public Page<User> search(Pageable pageable, String query) {
        return userService.search(pageable, query);
    }

    public Optional<User> getByPublicId(UserPublicId publicId) {
        return userService.getOneByPublicId(publicId);
    }

    public List<User> findUsersToNotify(ConversationPublicId conversationPublicId, UserPublicId readerPublicId) {
        return userService.getRecipientByConversationIdExcludingReader(conversationPublicId, readerPublicId);
    }






}
