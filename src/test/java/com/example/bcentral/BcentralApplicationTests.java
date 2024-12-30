package com.example.bcentral;

import com.example.bcentral.controller.MessageController;
import com.example.bcentral.dto.MessageDTO;
import com.example.bcentral.service.KafkaConsumerService;
import com.example.bcentral.service.KafkaProducerService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MessageControllerTest {

    @Mock
    private KafkaProducerService producerService;

    @Mock
    private KafkaConsumerService consumerService;

    @InjectMocks
    private MessageController messageController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Inicializa los mocks
    }
    
    @Test
    void testPostMessage() {
        MessageDTO message = new MessageDTO("12345", "2024-12-23T11:19:32Z", "Test Message", "app1", "notification");
        // Llama al método del controlador
        messageController.postMessage(message);

        // Verifica que el servicio del productor se llamó correctamente
        verify(producerService, times(1)).sendMessage("messages", message);
    }

    @Test
    void testConsumeAllMessages() {
        MessageDTO message1 = new MessageDTO("12345", "2024-12-23T11:19:32Z", "Test Message", "app1", "notification");
        MessageDTO message2 = new MessageDTO("12345", "2024-12-23T11:19:32Z", "Test Message", "app1", "notification");

        List<MessageDTO> mockMessages = Arrays.asList(message1, message2);

        when(consumerService.getAndClearMessages()).thenReturn(mockMessages);

        List<MessageDTO> result = messageController.consumeAllMessages();

        verify(consumerService, times(1)).getAndClearMessages();
        assertEquals(2, result.size());
        assertEquals(mockMessages, result);
    }
}
