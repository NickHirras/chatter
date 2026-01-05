package com.example.chatter.controller;

import com.example.chatter.model.Message;
import com.example.chatter.model.Room;
import com.example.chatter.repository.MessageRepository;
import com.example.chatter.repository.RoomRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class RoomController {

    private final RoomRepository roomRepository;
    private final MessageRepository messageRepository;

    public RoomController(RoomRepository roomRepository, MessageRepository messageRepository) {
        this.roomRepository = roomRepository;
        this.messageRepository = messageRepository;
    }

    @GetMapping("/rooms/{id}/messages")
    public String getRoomMessages(@PathVariable Long id, Model model) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid room Id:" + id));
        List<Message> allMessages = messageRepository.findByRoomIdOrderByCreatedAtAsc(id);

        // Organize into threads
        List<Message> rootMessages = new ArrayList<>();
        Map<Long, Message> messageMap = new HashMap<>();

        for (Message msg : allMessages) {
            messageMap.put(msg.getId(), msg);
        }

        for (Message msg : allMessages) {
            if (msg.getParent() != null) {
                Message parent = messageMap.get(msg.getParent().getId());
                if (parent != null) {
                    parent.getReplies().add(msg);
                } else {
                    rootMessages.add(msg);
                }
            } else {
                rootMessages.add(msg);
            }
        }

        model.addAttribute("room", room);
        model.addAttribute("messages", rootMessages);

        return "partials/_messages";
    }
}
