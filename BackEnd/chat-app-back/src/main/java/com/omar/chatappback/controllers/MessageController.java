package com.omar.chatappback.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.omar.chatappback.dto.message.RestMessage;
import com.omar.chatappback.entities.Message;
import com.omar.chatappback.message.MessageSendNew;
import com.omar.chatappback.services.MessageServices.MessageApplicationService;
import com.omar.chatappback.state.State;
import com.omar.chatappback.state.StatusNotification;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
public class MessageController {

    private final MessageApplicationService messageService;

    private ObjectMapper objectMapper = new ObjectMapper();


    @PostMapping(value = "/send", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<RestMessage> send(@RequestPart(value = "file", required = false)
                                            MultipartFile file,
                                            @RequestPart("dto") String messageRaw) throws IOException {

        // Deserialize the message payload from the JSON string
        RestMessage restMessage = objectMapper.readValue(messageRaw, RestMessage.class);

        if(restMessage.hasMedia()) {
            restMessage.setMediaAttachment(file.getBytes(), file.getContentType());
        }

        MessageSendNew messageSendNew = RestMessage.toDomain(restMessage);

        State<Message, String> sendState = messageService.send(messageSendNew);
        if(sendState.getStatus().equals(StatusNotification.OK)) {
            return ResponseEntity.ok(RestMessage.from(sendState.getValue()));
        } else {
            ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, sendState.getError());
            return ResponseEntity.of(problemDetail).build();
        }
    }






}
