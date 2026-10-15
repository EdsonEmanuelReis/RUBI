
package site.rubi.Service;

import org.springframework.stereotype.Service;
import site.rubi.DTO.ChatMessageDto;
import site.rubi.Repository.ChatMessageRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ChatService {

    private final ChatMessageRepository chatMessageRepository;
    private final ConversationService conversationService;
    private final OllamaService ollamaService;

    public ChatService(
            ChatMessageRepository chatMessageRepository,
            ConversationService conversationService,
            OllamaService ollamaService
    ) {
        this.chatMessageRepository = chatMessageRepository;
        this.conversationService = conversationService;
        this.ollamaService = ollamaService;
    }

    public String processMessage(long conversationId, String message) {

        if (!conversationService.existsById(conversationId)) {
            throw new ConversationNotFoundException(conversationId);
        }

        boolean firstMessage =
                !chatMessageRepository.hasMessages(conversationId);

        // Recupera as mensagens anteriores da conversa.
        List<ChatMessageDto> history =
                chatMessageRepository.findByConversationId(conversationId);

        // Converte o histórico para o formato esperado pelo Ollama.
        List<Map<String, String>> messages = new ArrayList<>();


        int startIndex = Math.max(0, history.size() - 4);

        for (int i = startIndex; i < history.size(); i++) {
            ChatMessageDto chatMessage = history.get(i);

            messages.add(Map.of(
                    "role", "user",
                    "content", chatMessage.userMessage()
            ));

            messages.add(Map.of(
                    "role", "assistant",
                    "content", chatMessage.assistantResponse()
            ));
        }

        // Acrescenta a mensagem atual ao contexto.
        messages.add(Map.of(
                "role", "user",
                "content", message
        ));

        // Envia o histórico completo e recebe a resposta.
        String response = ollamaService.generateResponse(messages);

        if (firstMessage) {
            System.out.println(
                    "PRIMEIRA MENSAGEM DA CONVERSA " + conversationId
            );

            // Futuramente: gerar o título com o Ollama.
        }

        // Persiste a nova pergunta e sua resposta.
        chatMessageRepository.save(
                conversationId,
                message,
                response
        );

        return response;
    }

    public List<ChatMessageDto> getHistory(long conversationId) {

        if (!conversationService.existsById(conversationId)) {
            throw new ConversationNotFoundException(conversationId);
        }

        return chatMessageRepository.findByConversationId(conversationId);
    }
}
