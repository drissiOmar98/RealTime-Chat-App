package com.omar.chatappback.dto.conversation;


import com.omar.chatappback.exception.Assert;
import com.omar.chatappback.message.ConversationName;
import org.jilt.Builder;

import java.util.Set;
import java.util.UUID;

@Builder
public class ConversationToCreate {

    private final Set<UUID> members;

    private final ConversationName name;

    public ConversationToCreate(Set<UUID> members, ConversationName name) {
        assertMandatoryFields(members, name);
        this.members = members;
        this.name = name;
    }

    private void assertMandatoryFields(Set<UUID> members, ConversationName name) {
        Assert.notNull("name", name);
        Assert.notNull("members", members);
    }

    public Set<UUID> getMembers() {
        return members;
    }

    public ConversationName getName() {
        return name;
    }

}
