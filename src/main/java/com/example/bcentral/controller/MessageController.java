package com.example.bcentral.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.example.bcentral.dto.MessageDTO;
import com.example.bcentral.service.KafkaConsumerService;
import com.example.bcentral.service.KafkaProducerService;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    private final KafkaProducerService producerService;
    private final KafkaConsumerService consumerService;

    public MessageController(KafkaProducerService producerService, KafkaConsumerService consumerService) {
        this.producerService = producerService;
        this.consumerService = consumerService;
    }

    @PostMapping
    public String postMessage(@RequestBody MessageDTO messageDTO) {
        producerService.sendMessage("messages", messageDTO);
        return "Message sent successfully!";
    }

    
    @GetMapping("/consume-all")
    public List<MessageDTO> consumeAllMessages() {
        List<MessageDTO> messages = consumerService.getAndClearMessages();
        if (messages.isEmpty()) {
            System.out.println("No messages available.");
        } else {
            System.out.println("Messages returned to client: " + messages);
        }
        return messages;
    }
}