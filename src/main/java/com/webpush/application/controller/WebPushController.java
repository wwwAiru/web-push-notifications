package com.webpush.application.controller;

import com.webpush.application.entity.MessageDto;
import com.webpush.application.service.PushNotificationService;
import com.webpush.application.service.SubscriptionEntityService;
import lombok.AllArgsConstructor;
import nl.martijndwars.webpush.Subscription;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1")
@AllArgsConstructor
public class WebPushController {

	private PushNotificationService pushNotificationService;

	private SubscriptionEntityService subscriptionEntityService;

	@GetMapping("/get-public-key")
	public ResponseEntity<String> getPublicKey() {
		HttpHeaders responseHeader = new HttpHeaders();
		return new ResponseEntity<> (pushNotificationService.getPublicKey(), responseHeader, HttpStatus.OK);
	}
	
	@PostMapping("/subscribe")
	public void subscribe(@RequestBody Subscription subscription) {
		subscriptionEntityService.save(subscription);
	}

	@PostMapping("/unsubscribe")
	public ResponseEntity<?> unsubscribe(@RequestBody String endpoint) {
		pushNotificationService.unsubscribe(endpoint);
		HttpHeaders responseHeader = new HttpHeaders();
		return new ResponseEntity<> (responseHeader, HttpStatus.OK);
	}
	
	@PostMapping("/push-message")
	public MessageDto notifyAll(@RequestBody MessageDto message) {
		pushNotificationService.sendAll(message);
		return message;		
	}
}
