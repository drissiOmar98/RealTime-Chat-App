package com.omar.chatappback.entities;

import com.omar.chatappback.common.AbstractAuditingEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;


@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "conversation")
public class Conversation extends AbstractAuditingEntity<Long> {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "conversationSequenceGenerator")
    @SequenceGenerator(name = "conversationSequenceGenerator", sequenceName = "conversation_sequence", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @UuidGenerator
    @Column(name = "public_id", nullable = false, updatable = false)
    private UUID publicId;

    @Column(name = "name")
    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "conversation")
    private Set<Message> messages = new HashSet<>();

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(
            name = "user_conversation",
            joinColumns = {@JoinColumn(name = "conversation_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")}
    )
    private Set<User> users = new HashSet<>();


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Conversation that = (Conversation) o;
        return Objects.equals(publicId, that.publicId) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(publicId, name);
    }






}
