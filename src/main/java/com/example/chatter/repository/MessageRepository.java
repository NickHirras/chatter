package com.example.chatter.repository;

import com.example.chatter.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByRoomIdOrderByCreatedAtAsc(Long roomId);
    Page<Message> findByRoomIdOrderByCreatedAtDesc(Long roomId, Pageable pageable);
}
