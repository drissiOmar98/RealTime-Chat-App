package com.omar.chatappback.notification;


import com.omar.chatappback.shared.AuthenticatedUser;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequestMapping("/api/sse")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;


    @GetMapping("/subscribe")
    public SseEmitter subscribe() {
        return notificationService.addEmitter(
               AuthenticatedUser.username().username());
    }
}
