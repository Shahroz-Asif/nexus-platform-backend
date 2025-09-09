package com.nexus.platform.controller;

import com.nexus.platform.dto.SignalingMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class SignalingController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/signal")
    public void handleSignal(@Payload SignalingMessage message) {
        messagingTemplate.convertAndSendToUser(message.getRecipient(), "/queue/signal", message);
    }
}
