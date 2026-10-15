package site.rubi.Controller;


import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import site.rubi.DTO.ConversationDto;
import site.rubi.DTO.CreateConversationRequest;
import site.rubi.DTO.RenameConversationRequest;
import site.rubi.Service.ConversationService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/conversations")
public class ConversationController {

    private final ConversationService conversationService;

    public ConversationController(
            ConversationService conversationService
    ) {
        this.conversationService = conversationService;
    }

    @PostMapping
    public Map<String, Long> create(
            @Valid @RequestBody CreateConversationRequest request
    ) {
        long id = conversationService.create(request.title());

        return Map.of("id", id);
    }

    @GetMapping
    public List<ConversationDto> findAll() {
        return conversationService.findAll();
    }

    @PutMapping("/{id}")
    public void rename(
            @PathVariable long id,
            @Valid @RequestBody RenameConversationRequest request
    ) {
        conversationService.rename(id, request.title());
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        conversationService.delete(id);
    }

}
