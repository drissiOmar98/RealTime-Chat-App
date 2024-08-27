package com.omar.chatappback.services.MessageServices;


import com.omar.chatappback.entities.Message;
import com.omar.chatappback.entities.User;
import com.omar.chatappback.message.MessageSendNew;
import com.omar.chatappback.services.UserReader;
import com.omar.chatappback.shared.AuthenticatedUser;
import com.omar.chatappback.state.State;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MessageApplicationService {

    private final MessageCreator messageCreator;
    private final UserReader userReader;

    @Transactional
    public State<Message, String> send(MessageSendNew messageSendNew) {
        State<Message, String> creationState;
        Optional<User> connectedUser = this.userReader.getByEmail(AuthenticatedUser.username().username());
        if(connectedUser.isPresent()) {
            creationState = this.messageCreator.create(messageSendNew, connectedUser.get());
        } else {
            creationState = State.<Message, String>builder()
                    .forError(String.format("Error retrieving user information inside the DB : %s", AuthenticatedUser.username().username()));
        }
        return creationState;
    }


}
