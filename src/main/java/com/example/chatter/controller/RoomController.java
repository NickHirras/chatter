package com.example.chatter.controller;

import com.example.chatter.model.Message;
import com.example.chatter.model.Room;
import com.example.chatter.repository.MessageRepository;
import com.example.chatter.repository.RoomRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Collections;
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

        Pageable pageable = PageRequest.of(0, 10);
        Page<Message> page = messageRepository.findByRoomIdOrderByCreatedAtDesc(id, pageable);
        List<Message> messages = new ArrayList<>(page.getContent());
        Collections.reverse(messages);

        // Organize into threads
        List<Message> rootMessages = new ArrayList<>();
        Map<Long, Message> messageMap = new HashMap<>();

        for (Message msg : messages) {
            messageMap.put(msg.getId(), msg);
        }

        for (Message msg : messages) {
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
        model.addAttribute("currentPage", 0);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("room_id", id);

        return "partials/_messages";
    }

    @GetMapping("/rooms/{id}/messages/older")
    public String getOlderMessages(@PathVariable Long id,
                                   @RequestParam(defaultValue = "1") int page,
                                   Model model) {
        Pageable pageable = PageRequest.of(page, 10);
        Page<Message> messagePage = messageRepository.findByRoomIdOrderByCreatedAtDesc(id, pageable);
        List<Message> messages = new ArrayList<>(messagePage.getContent());
        Collections.reverse(messages);

        // Organize into threads
        List<Message> rootMessages = new ArrayList<>();
        Map<Long, Message> messageMap = new HashMap<>();
        for (Message msg : messages) {
            messageMap.put(msg.getId(), msg);
        }
        for (Message msg : messages) {
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

        model.addAttribute("messages", rootMessages);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", messagePage.getTotalPages());
        model.addAttribute("room_id", id);

        return "partials/_message_list";
    }
}
