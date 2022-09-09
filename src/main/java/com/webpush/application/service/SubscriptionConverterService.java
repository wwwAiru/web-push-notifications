package com.webpush.application.service;

import com.webpush.application.entity.SubscriptionEntity;
import nl.martijndwars.webpush.Subscription;
import org.springframework.stereotype.Service;

/**
 * сервис-конвертер объекта потребителя пуш уведомлений в сушность для хранения в базе данных
 */
@Service
public class SubscriptionConverterService {

    public SubscriptionEntity toEntity(Subscription subscription) {
        SubscriptionEntity entity = new SubscriptionEntity(subscription.endpoint,
                                                           subscription.keys.p256dh,
                                                           subscription.keys.auth);
        return entity;
    }

}
