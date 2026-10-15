package site.rubi.DTO;

public record OllamaResponseDto(
        Message message
) {
    public record Message(
            String role,
            String content
    ) {}
}