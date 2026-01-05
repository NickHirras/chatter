package com.example.chatter.controller;

import com.example.chatter.model.Message;
import com.example.chatter.model.Room;
import com.example.chatter.repository.MessageRepository;
import com.example.chatter.repository.RoomRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class HomeController {

    private static final Logger log = LoggerFactory.getLogger(HomeController.class);

    private final RoomRepository roomRepository;
    private final MessageRepository messageRepository;

    public HomeController(RoomRepository roomRepository, MessageRepository messageRepository) {
        this.roomRepository = roomRepository;
        this.messageRepository = messageRepository;
    }

    @GetMapping("/")
    public String home(Model model, Principal principal, @RequestParam(required = false) Long roomId) {
        if (principal == null) {
            log.info("Anonymous user accessing home page");
            return "index"; // Public landing page for anonymous users
        }

        log.info("Authenticated user {} accessing home page", principal.getName());

        try {
            // Fetch rooms ordered by most recent activity for authenticated users
            List<Room> rooms = roomRepository.findAllPublicOrderedByRecentActivity();
            log.info("Found {} rooms", rooms.size());

            model.addAttribute("rooms", rooms);

            if (roomId != null) {
                Optional<Room> selectedRoom = roomRepository.findById(roomId);
                if (selectedRoom.isPresent()) {
                    Room room = selectedRoom.get();
                    List<Message> allMessages = messageRepository.findByRoomIdOrderByCreatedAtAsc(roomId);

                    // Organize into threads (same logic as RoomController)
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
                }
            }

            log.info("Returning rooms view");
            return "rooms"; // Application view for logged-in users
        } catch (Exception e) {
            log.error("Error loading rooms", e);
            throw e;
        }
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
