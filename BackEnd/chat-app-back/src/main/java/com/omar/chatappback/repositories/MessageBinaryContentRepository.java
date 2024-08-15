package com.omar.chatappback.repositories;

import com.omar.chatappback.entities.MessageContentBinary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageBinaryContentRepository extends JpaRepository<MessageContentBinary,Long> {
}
