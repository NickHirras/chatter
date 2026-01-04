package com.example.chatter.controller;

import com.example.chatter.model.Room;
import com.example.chatter.repository.RoomRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.List;

@Controller
public class HomeController {

    private static final Logger log = LoggerFactory.getLogger(HomeController.class);

    private final RoomRepository roomRepository;

    public HomeController(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @GetMapping("/")
    public String home(Model model, Principal principal) {
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
