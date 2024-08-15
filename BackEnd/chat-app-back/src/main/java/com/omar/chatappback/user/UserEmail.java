package com.omar.chatappback.user;


import com.omar.chatappback.exception.Assert;

public record UserEmail(String value) {

    public UserEmail {
        Assert.field(value, value).maxLength(255);
    }
}
