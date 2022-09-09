package com.webpush.application.service;

import com.webpush.application.entity.SubscriptionEntity;
import com.webpush.application.repository.SubscriptionEntityRepository;
import lombok.AllArgsConstructor;
import nl.martijndwars.webpush.Subscription;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * сервис сохранения и удаления объекта-потребителя пуш уведомлений
 */
@Service
@AllArgsConstructor
public class SubscriptionEntityService {

    private SubscriptionConverterService subscriptionConverterService;

    private SubscriptionEntityRepository subscriptionEntityRepository;

    public void save(Subscription subscription) {
        SubscriptionEntity subscriptionEntity = subscriptionConverterService.toEntity(subscription);
        subscriptionEntityRepository.save(subscriptionEntity);
    }

    @Transactional
    public void delete(String endpoint) {
        subscriptionEntityRepository.deleteByEndpoint(endpoint);
    }

    public List<SubscriptionEntity> findAll() {
        return subscriptionEntityRepository.findAll();
    }

}
