package com.omar.chatappback.user;


import com.omar.chatappback.exception.Assert;

public record UserImageUrl(String value) {

    public UserImageUrl {
        Assert.field(value, value).maxLength(255);
    }
}
