package com.example.notification_service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.javamail.JavaMailSender;

@EnableDiscoveryClient
@SpringBootTest
class NotificationServiceApplicationTests {
 @MockBean
    private JavaMailSender javaMailSender; 
	@Test
	void contextLoads() {
	}

}
