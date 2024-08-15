package com.omar.chatappback.message;



import com.omar.chatappback.exception.Assert;

import java.time.Instant;

public record MessageSentTime(Instant date) {
    public MessageSentTime {
        Assert.field("date", date).notNull();
    }
}
