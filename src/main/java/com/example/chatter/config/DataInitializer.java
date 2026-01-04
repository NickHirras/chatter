package com.example.chatter.config;

import com.example.chatter.model.Message;
import com.example.chatter.model.Room;
import com.example.chatter.model.User;
import com.example.chatter.repository.MessageRepository;
import com.example.chatter.repository.RoomRepository;
import com.example.chatter.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

@Configuration
public class DataInitializer {

    private final Random random = new Random();

    @Bean
    public CommandLineRunner initData(UserRepository userRepository, RoomRepository roomRepository,
            MessageRepository messageRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            seedUsers(userRepository, passwordEncoder);
            seedRooms(roomRepository, userRepository);
            seedMessages(messageRepository, roomRepository, userRepository);
        };
    }

    private void seedUsers(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        // Initial Admin/User
        if (userRepository.findByEmail("bill@localhost").isEmpty()) {
            userRepository
                    .save(new User("Bill Gates", "bill@localhost", passwordEncoder.encode("password"), "ROLE_USER"));
        }
        if (userRepository.findByEmail("linus@localhost").isEmpty()) {
            userRepository.save(
                    new User("Linus Torvalds", "linus@localhost", passwordEncoder.encode("password"), "ROLE_ADMIN"));
        }

        // 25 Realistic Users
        String[] firstNames = { "James", "Mary", "Robert", "Patricia", "John", "Jennifer", "Michael", "Linda", "David",
                "Elizabeth",
                "William", "Barbara", "Richard", "Susan", "Joseph", "Jessica", "Thomas", "Sarah", "Charles", "Karen",
                "Christopher", "Nancy", "Daniel", "Lisa", "Matthew" };
        String[] lastNames = { "Smith", "Johnson", "Williams", "Brown", "Jones", "Garcia", "Miller", "Davis",
                "Rodriguez", "Martinez",
                "Hernandez", "Lopez", "Gonzalez", "Wilson", "Anderson", "Thomas", "Taylor", "Moore", "Jackson",
                "Martin",
                "Lee", "Perez", "Thompson", "White", "Harris" };

        for (int i = 0; i < 25; i++) {
            String firstName = firstNames[i % firstNames.length];
            String lastName = lastNames[i % lastNames.length];
            String name = firstName + " " + lastName;
            String email = firstName.toLowerCase() + "." + lastName.toLowerCase() + "@localhost";

            if (userRepository.findByEmail(email).isEmpty()) {
                userRepository.save(new User(name, email, passwordEncoder.encode("password"), "ROLE_USER"));
            }
        }
    }

    private void seedRooms(RoomRepository roomRepository, UserRepository userRepository) {
        if (roomRepository.count() > 0)
            return;

        User bill = userRepository.findByEmail("bill@localhost").orElseThrow();
        List<User> allUsers = userRepository.findAll();

        String[][] roomsData = {
                { "General Chat", "A place for general discussion." },
                { "Tech Talk", "Discussing the latest in technology." },
                { "Random", "Anything goes here." },
                { "Spring Boot", "All things Spring Boot." },
                { "Java", "Java programming language discussions." },
                { "Frontend", "HTML, CSS, JS, and frameworks." },
                { "Music", "Share your favorite tunes." },
                { "Movies", "Cinema and TV show discussions." },
                { "Gaming", "Video games and board games." },
                { "News", "Current events and news." }
        };

        for (String[] roomData : roomsData) {
            Room room = new Room(roomData[0], roomData[1], false);

            // Set Moderators
            Set<User> moderators = new HashSet<>();
            moderators.add(bill);
            // Add 1-2 random moderators
            int numMods = 1 + random.nextInt(2);
            for (int i = 0; i < numMods; i++) {
                moderators.add(allUsers.get(random.nextInt(allUsers.size())));
            }
            room.setModerators(moderators);

            // Set CreatedBy and CreatedAt
            room.setCreatedBy(bill);
            room.setCreatedAt(Instant.now().minus(30, ChronoUnit.DAYS)); // Created 30 days ago

            roomRepository.save(room);

            // Fix timestamp after save (JPA Auditing might overwrite it)
            room.setCreatedAt(Instant.now().minus(30, ChronoUnit.DAYS));
            roomRepository.save(room);
        }
    }

    private void seedMessages(MessageRepository messageRepository, RoomRepository roomRepository,
            UserRepository userRepository) {
        if (messageRepository.count() > 0)
            return;

        List<Room> rooms = roomRepository.findAll();
        List<User> users = userRepository.findAll();

        String[] messageTemplates = {
                "Hello everyone!", "How is it going?", "Has anyone seen the new movie?", "I love Spring Boot!",
                "Can someone help me with this bug?", "This is a great chat app.", "What are you working on?",
                "Good morning!", "Good night!", "Interesting point.", "I agree.", "I disagree.",
                "Check this out.", "LOL", "Nice code.", "Java 25 is awesome.", "HTMX is cool.",
                "Pico.css is simple.", "SQLite is fast.", "Deployment is easy."
        };

        for (Room room : rooms) {
            Instant roomCreated = room.getCreatedAt();
            Instant now = Instant.now();
            long totalSeconds = ChronoUnit.SECONDS.between(roomCreated, now);

            List<Message> roomMessages = new ArrayList<>();

            for (int i = 0; i < 100; i++) {
                User author = users.get(random.nextInt(users.size()));
                String content = messageTemplates[random.nextInt(messageTemplates.length)];

                // Random time between room creation and now, ensuring order roughly
                // We'll just distribute them randomly and then sort by time if we were
                // displaying,
                // but here we just want them to exist.
                // To make replies logical, we'll generate them in order.

                long offsetSeconds = (long) (totalSeconds * (i / 100.0)); // Spread out
                Instant msgTime = roomCreated.plus(offsetSeconds, ChronoUnit.SECONDS);

                Message message = new Message(content, room);
                message.setCreatedBy(author);
                message.setCreatedAt(msgTime);

                // 30% chance to be a reply to a previous message in this batch
                if (i > 0 && random.nextDouble() < 0.3) {
                    Message parent = roomMessages.get(random.nextInt(roomMessages.size()));
                    // Ensure reply is after parent
                    if (msgTime.isBefore(parent.getCreatedAt())) {
                        msgTime = parent.getCreatedAt().plus(1, ChronoUnit.MINUTES);
                        message.setCreatedAt(msgTime);
                    }
                    message.setParent(parent);
                }

                messageRepository.save(message);

                // Fix timestamp
                message.setCreatedAt(msgTime);
                messageRepository.save(message);

                roomMessages.add(message);
            }
        }
    }
}
