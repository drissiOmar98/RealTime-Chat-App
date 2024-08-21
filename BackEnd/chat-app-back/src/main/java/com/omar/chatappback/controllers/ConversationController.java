package com.omar.chatappback.controllers;



import com.omar.chatappback.dto.conversation.ConversationToCreate;
import com.omar.chatappback.dto.conversation.RestConversation;
import com.omar.chatappback.dto.conversation.RestConversationToCreate;
import com.omar.chatappback.entities.Conversation;
import com.omar.chatappback.services.ConversationServices.ConversationUtilityService;
import com.omar.chatappback.state.State;
import com.omar.chatappback.state.StatusNotification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/conversations")
@RequiredArgsConstructor
public class ConversationController {

    private final ConversationUtilityService conversationService;

    @GetMapping
    ResponseEntity<List<RestConversation>> getAll(Pageable pageable) {
        List<RestConversation> restConversations = conversationService.getAllByConnectedUser(pageable)
                .stream().map(RestConversation::from).toList();
        return ResponseEntity.ok(restConversations);
    }

    @PostMapping
    ResponseEntity<RestConversation> create(@RequestBody
                                            RestConversationToCreate restConversationToCreate) {
        ConversationToCreate newConversation = RestConversationToCreate.toConversationToCreate(restConversationToCreate);
        State<Conversation, String> conversationState = conversationService.create(newConversation);
        if (conversationState.getStatus().equals(StatusNotification.OK)) {
            RestConversation restConversations = RestConversation.from(conversationState.getValue());
            return ResponseEntity.ok(restConversations);
        } else {
            ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Not allowed to create conversation");
            return ResponseEntity.of(problemDetail).build();
        }
    }


    @DeleteMapping
    ResponseEntity<UUID> delete(@RequestParam UUID publicId) {
        State<UUID, String> restConversation = conversationService.delete(publicId);
        if (restConversation.getStatus().equals(StatusNotification.OK)) {
            return ResponseEntity.ok(publicId);
        } else {
            ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Not allowed to delete conversation");
            return ResponseEntity.of(problemDetail).build();
        }
    }

    @GetMapping("/get-one-by-public-id")
    ResponseEntity<RestConversation> getOneByPublicId(@RequestParam UUID conversationId) {
        Optional<RestConversation> restConversation = conversationService.getOneByConversationId(conversationId)
                .map(RestConversation::from);
        if (restConversation.isPresent()) {
            return ResponseEntity.ok(restConversation.get());
        } else {
            ProblemDetail problemDetail = ProblemDetail
                    .forStatusAndDetail(HttpStatus.BAD_REQUEST, "Not able to find this conversation");
            return ResponseEntity.of(problemDetail).build();
        }
    }



















}
