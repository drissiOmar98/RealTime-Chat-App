package com.omar.chatappback.repositories;

import com.omar.chatappback.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByEmail(String email);

    List<User> findByPublicIdIn(List<UUID> publicIds);

    @Query("SELECT user FROM User user WHERE lower(user.lastName) LIKE lower(concat('%', :query, '%')) " +
            "OR lower(user.firstName) LIKE lower(concat('%', :query, '%'))")
    Page<User> search(Pageable pageable, String query);

    Optional<User> findOneByPublicId(UUID publicId);


    @Modifying
    @Query("UPDATE User  user SET user.lastSeen = :lastSeen WHERE user.publicId = :userPublicID")
    int updateLastSeen(UUID userPublicID, Instant lastSeen);

    List<User> findByConversationsPublicIdAndPublicIdIsNot(UUID conversationsPublicId, UUID publicIdToExclude);


}
