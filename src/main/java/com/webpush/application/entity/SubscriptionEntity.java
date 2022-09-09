package com.webpush.application.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * сущность потребителя пуш уведомлений
 */
@Entity
@Table(name = "subscriptions")
@Getter
@Setter
public class SubscriptionEntity {

    @Id
    @Column(name = "endpoint")
    private String endpoint;

    @Column(name = "p256dh")
    private String p256dh;

    @Column(name = "auth")
    private String auth;

    public SubscriptionEntity(String endpoint, String p256dh, String auth) {
        this.endpoint = endpoint;
        this.p256dh = p256dh;
        this.auth = auth;
    }

    public SubscriptionEntity() {
    }
}
