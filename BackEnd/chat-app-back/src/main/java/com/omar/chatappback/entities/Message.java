package com.omar.chatappback.entities;


import com.omar.chatappback.message.MessageSendState;
import com.omar.chatappback.message.MessageType;
import jakarta.persistence.*;
import lombok.*;


import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "message")
@Builder
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "messageSequenceGenerator")
    @SequenceGenerator(name = "messageSequenceGenerator", sequenceName = "message_sequence", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "public_id", nullable = false, updatable = false)
    private UUID publicId;

    @Column(name = "send_time", nullable = false)
    private Instant sendTime;

    @Column(name = "text", nullable = false)
    private String text;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private MessageType type;

    @Enumerated(EnumType.STRING)
    @Column(name = "send_state")
    private MessageSendState sendState;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_fk_sender", nullable = false)
    private User sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conversation_fk", nullable = false)
    private Conversation conversation;

    @OneToOne
    @JoinColumn(name = "message_binary_content_fk", referencedColumnName = "id")
    private MessageContentBinary contentBinary;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message that = (Message) o;
        return Objects.equals(publicId, that.publicId) && Objects.equals(sendTime, that.sendTime) && Objects.equals(text, that.text) && type == that.type && sendState == that.sendState;
    }

    @Override
    public int hashCode() {
        return Objects.hash(publicId, sendTime, text, type, sendState);
    }





}
