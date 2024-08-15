package com.omar.chatappback.user;


import com.omar.chatappback.exception.Assert;

public record AuthorityName(String name) {

    public AuthorityName {
        Assert.field("name", name).notNull();
    }
}
