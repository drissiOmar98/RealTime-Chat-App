package com.omar.chatappback.message;


import com.omar.chatappback.exception.Assert;

public record ConversationName(String name) {

    public ConversationName {
        Assert.field("name", name).minLength(3).maxLength(255);
    }
}
