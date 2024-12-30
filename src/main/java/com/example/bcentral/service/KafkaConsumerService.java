package com.example.bcentral.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

import com.example.bcentral.dto.MessageDTO;

@Service
public class KafkaConsumerService {

    private MessageDTO lastMessage;
    private final List<MessageDTO> messages = new CopyOnWriteArrayList<>();
    
    @KafkaListener(topics = "messages", groupId = "group_id")
    public void consume(ConsumerRecord<String, MessageDTO> record, Acknowledgment acknowledgment) {
        try {
            messages.add(record.value());
            System.out.println("Message consumed: " + record.value());
            acknowledgment.acknowledge(); // Confirmar el offset manualmente
        } catch (Exception e) {
            System.err.println("Error processing message: " + record);
            e.printStackTrace();
        }
    }
    
    private void processMessage(MessageDTO message) {
        System.out.println("Processing message: " + message);
    }

    
    public List<MessageDTO> getAndClearMessages() {
        if (messages.isEmpty()) {
            System.out.println("No messages to consume.");
            return new ArrayList<>();
        }

        List<MessageDTO> consumedMessages = new ArrayList<>(messages);
        messages.clear(); // Limpia los mensajes procesados
        System.out.println("Cleared messages. Returning: " + consumedMessages);
        return consumedMessages;
    }
    
}