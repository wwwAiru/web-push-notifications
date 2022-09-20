package com.webpush.application.service;

import com.webpush.application.dto.MessageDto;
import com.webpush.application.entity.SubscriptionEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import nl.martijndwars.webpush.Notification;
import nl.martijndwars.webpush.PushService;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.jose4j.lang.JoseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.security.spec.InvalidKeySpecException;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 *  сервис для рассылки пуш уведомлений,
 *  также отдаёт на фронт публичный ключ сервера
 */
@Service
public class PushNotificationService {

    /**
     * ключи находится в файле .env,
     * сгенерировать ключи для сервера по ссылке
     * https://web-push-codelab.glitch.me/
     * или
     * npm install -g web-push
     * web-push generate-vapid-keys
     */
    @Value("${vapid.public.key}")
    private String publicKey;

    @Value("${vapid.private.key}")
    private String privateKey;

    private PushService pushService;

    @Autowired
    private SubscriptionEntityService subscriptionEntityService;


    private ObjectMapper objectMapper = new ObjectMapper();

    /**
     *  инициализация пуш сервиса, секьюрити провайдера
     */
    @PostConstruct
    private void init() throws GeneralSecurityException {
        Security.addProvider(new BouncyCastleProvider());
        pushService = new PushService(publicKey, privateKey);
    }

    /**
     * отдает на фронт публичный ключ сервера
     */
    public String getPublicKey() {
        return publicKey;
    }

    /**
     *
     * @param endpoint - уникальный url для рассылки пуш уведомлений
     * удаляется потребитель из б.д.
     */
    @Transactional
    public void unsubscribe(String endpoint) {
        subscriptionEntityService.delete(endpoint);
    }

    /**
     * отправляет единственное пуш уведомление
     * @param subscriptionEntity - потребитель пуш-уведомлений
     * @param messageDto - уведомление, включает: title - заголовок, message - текст сообщения,
     * clickTarget - ссылка для перехода
     */
    public void sendOne(SubscriptionEntity subscriptionEntity, MessageDto messageDto) {
        Notification notification = null;
        try {
            notification = new Notification(
                        subscriptionEntity.getEndpoint(),
                        subscriptionEntity.getP256dh(),
                        subscriptionEntity.getAuth(),
                        objectMapper.writeValueAsString(messageDto));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        try {
            pushService.send(notification);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JoseException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * рассылка всем потребителям
     * @param messageDto - уведомление, включает: title - заголовок, message - текст сообщения,
     * clickTarget - ссылка для перехода
     */
    public void sendAll(MessageDto messageDto) {
        List<SubscriptionEntity> subscriptionEntities = subscriptionEntityService.findAll();
        if (!subscriptionEntities.isEmpty()) {
            subscriptionEntities.forEach(subscriptionEntity -> sendOne(subscriptionEntity, messageDto));
        }
    }

}
