package com.omar.chatappback.repositories;

import com.omar.chatappback.entities.Conversation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ConversationRepository extends JpaRepository<Conversation, Long> {

    Page<Conversation> findAllByUsersPublicId(UUID publicId, Pageable pageable);

    int deleteByUsersPublicIdAndPublicId(UUID userPublicId, UUID conversationPublicId);

    Optional<Conversation> findOneByUsersPublicIdAndPublicId(UUID userPublicId, UUID conversationPublicId);

    @Query("SELECT conversation FROM Conversation conversation " +
            "JOIN conversation.users users " +
            "WHERE users.publicId IN :userPublicIds " +
            "GROUP BY conversation.id " +
            "HAVING COUNT(users.id) = :userCount")
    Optional<Conversation> findOneByUsersPublicIdIn(List<UUID> userPublicIds, int userCount);

    Optional<Conversation> findOneByPublicId(UUID publicId);


}
