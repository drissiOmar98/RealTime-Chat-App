package com.omar.chatappback.user;


import com.omar.chatappback.exception.Assert;

public record UserFirstname(String value) {

    public UserFirstname {
        Assert.field(value, value).maxLength(255);
    }
}
