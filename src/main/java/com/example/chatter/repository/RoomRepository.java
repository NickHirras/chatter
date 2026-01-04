package com.example.chatter.repository;

import com.example.chatter.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {

    @Query("""
            SELECT r FROM Room r
            LEFT JOIN Message m ON m.room = r
            WHERE r.isPrivateRoom = false
            GROUP BY r.id
            ORDER BY MAX(m.createdAt) DESC
            """)
    List<Room> findAllPublicOrderedByRecentActivity();
}
