
package site.rubi.Controller;

import site.rubi.DTO.ChatMessageDto;
import site.rubi.DTO.ChatRequest;
import site.rubi.DTO.ChatResponse;
import site.rubi.Service.ChatService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping("/{conversationId}")
    public ChatResponse chat(
            @PathVariable long conversationId,
            @Valid @RequestBody ChatRequest request
    ) {
        String response = chatService.processMessage(
                conversationId,
                request.message()
        );

        return new ChatResponse(
                request.message(),
                response
        );
    }

    @GetMapping("/{conversationId}/history")
    public List<ChatMessageDto> getHistory(
            @PathVariable long conversationId
    ) {
        return chatService.getHistory(conversationId);
    }
}
