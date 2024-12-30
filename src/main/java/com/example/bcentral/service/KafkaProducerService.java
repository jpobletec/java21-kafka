package com.example.bcentral.service;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.example.bcentral.dto.MessageDTO;

@Service
public class KafkaProducerService {

    private final KafkaTemplate<String, MessageDTO> kafkaTemplate;

    public KafkaProducerService(KafkaTemplate<String, MessageDTO> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String topic, MessageDTO messageDTO) {
        kafkaTemplate.send(topic, messageDTO);
    }
}