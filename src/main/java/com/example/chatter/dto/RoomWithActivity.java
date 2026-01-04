package com.example.chatter.dto;

import com.example.chatter.model.Room;
import java.time.Instant;

public class RoomWithActivity {

    private final Room room;
    private final Instant lastMessageAt;

    public RoomWithActivity(Room room, Instant lastMessageAt) {
        this.room = room;
        this.lastMessageAt = lastMessageAt;
    }

    public Room getRoom() {
        return room;
    }

    public Instant getLastMessageAt() {
        return lastMessageAt;
    }
}
