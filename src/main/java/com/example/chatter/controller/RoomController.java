package com.example.chatter.controller;

import com.example.chatter.model.Message;
import com.example.chatter.model.Room;
import com.example.chatter.repository.MessageRepository;
import com.example.chatter.repository.RoomRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

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
        List<Message> messages = messageRepository.findByRoomIdOrderByCreatedAtAsc(id);

        model.addAttribute("room", room);
        model.addAttribute("messages", messages);

        return "partials/_messages";
    }
}
