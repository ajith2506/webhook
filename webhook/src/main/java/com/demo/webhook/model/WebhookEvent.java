package com.demo.webhook.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WebhookEvent {
    private String event;
    private String repository;
    private String sender;

    @Override
    public String toString() {
        return "WebhookEvent{" +
                "event='" + event + '\'' +
                ", repository='" + repository + '\'' +
                ", sender='" + sender + '\'' +
                '}';
    }
}
