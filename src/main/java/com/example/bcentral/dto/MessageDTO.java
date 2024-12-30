package com.example.bcentral.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MessageDTO {
    private String id;
    private String timestamp;
    private String message;
    private Metadata metadata;

    public MessageDTO(String id, String timestamp, String message, String source, String type) {
        this.id = id;
        this.timestamp = timestamp;
        this.message = message;
        this.metadata = new Metadata(source, type);
    }

    @Data
    @NoArgsConstructor
    public static class Metadata {
        private String source;
        private String type;

        // Constructor explícito
        public Metadata(String source, String type) {
            this.source = source;
            this.type = type;
        }
    }
}