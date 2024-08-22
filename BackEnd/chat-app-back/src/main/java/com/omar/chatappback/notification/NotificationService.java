package com.omar.chatappback.notification;


import com.omar.chatappback.entities.User;
import com.omar.chatappback.services.UserUtilityService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final UserUtilityService userService;

    private static final Logger log = LoggerFactory.getLogger(NotificationService.class);

    // Stores SSE (Server-Sent Events) emitters for each user, where the key is a user's UUID in string format.
    private Map<String, SseEmitter> emitters = new HashMap<>();


    @Scheduled(fixedRate = 5000) // Schedules this method to run at a fixed rate of every 5000 milliseconds (5 seconds).
    public void heartBeat() throws IOException {
        for (Map.Entry<String, SseEmitter> sseEmitter : emitters.entrySet()) {
            try {
                sseEmitter.getValue().send(SseEmitter.event()
                        .name("heartbeat") // The name of the event being sent.
                        .id(sseEmitter.getKey()) // The unique ID associated with the event (user's UUID).
                        .data("Check heartbeat...")); // The actual data being sent with the event.

                // Updates the user's presence status in the system using the userService.
                this.userService.updatePresence(
                        UUID.fromString(sseEmitter.getKey()));
            } catch (IllegalStateException e) { // Catches the exception if the emitter is in an invalid state.
                log.info("remove this one from the map {}", sseEmitter.getKey());
                // Removes the emitter from the map to prevent further issues.
                this.emitters.remove(sseEmitter.getKey());
            }
        }
    }


    public SseEmitter addEmitter(String userEmail) {
        Optional<User> userByEmail = this.userService.getUserByEmail(userEmail);
        if (userByEmail.isPresent()) {
            log.info("new Emitter added to the list : {}", userEmail);
            // Creates a new SSE emitter with a 60,000 ms (60 seconds) timeout.
            SseEmitter newEmitter = new SseEmitter(60000L);
            try {
                UUID userUUID = userByEmail.get().getPublicId();
                newEmitter.send(SseEmitter.event()
                        .id(userUUID.toString()) // Sends an initial event with the user's UUID as the event ID.
                        .data("Starting connection...")); // Sends data indicating that the connection is starting.


                // Stores the new emitter in the map with the user's UUID as the key.
                this.emitters.put(userUUID.toString(), newEmitter);
                // Returns the created emitter so it can be used by the client.
                return newEmitter;
            } catch (IOException e) { // Catches any IO exceptions that occur while sending the event.
                // Wraps and rethrows the exception as a runtime exception.
                throw new RuntimeException(e);
            }
        }
        // Returns null if the user could not be found.
        return null;
    }


    public void sendMessage(Object message,
                            List<UUID> usersToNotify,
                            NotificationEventName notificationEventName) {
        // Iterates over the list of user IDs that need to be notified.
        for (UUID userId : usersToNotify) {
            String userUUIDRaw = userId.toString(); // Converts the UUID to a string format for easy lookup.
            // Checks if there is an active emitter for the given user ID.
            if (this.emitters.containsKey(userUUIDRaw)) {
                log.info("located userpublicid, let send him message : {}", userUUIDRaw);  // Logs the action of sending a message to this user.
                SseEmitter sseEmitter = this.emitters.get(userUUIDRaw); // Retrieves the SseEmitter associated with this user ID.
                try {
                    // Sends a Server-Sent Event to the user with the specified event name, user ID, and message data.
                    sseEmitter.send(SseEmitter.event()
                            .name(notificationEventName.value) // Sets the event name, indicating the type of notification.
                            .id(userUUIDRaw) // Associates the event with the user's unique ID.
                            .data(message)); // The actual message data being sent to the user.
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
