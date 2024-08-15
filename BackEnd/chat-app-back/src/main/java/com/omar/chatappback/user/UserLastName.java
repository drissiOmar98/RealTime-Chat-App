package com.omar.chatappback.user;


import com.omar.chatappback.exception.Assert;

public record UserLastName(String value) {

    public UserLastName {
        Assert.field(value, value).maxLength(255);
    }
}
