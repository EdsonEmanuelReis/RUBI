package site.rubi.DTO;

public record ChatMessageDto(
        long id,
        String userMessage,
        String assistantResponse,
        String createdAt
) {}
