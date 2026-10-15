
package site.rubi.Service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.rubi.DTO.ConversationDto;
import site.rubi.Repository.ChatMessageRepository;
import site.rubi.Repository.ConversationRepository;

import java.util.List;

@Service
public class ConversationService {

    private final ConversationRepository conversationRepository;
    private final ChatMessageRepository chatMessageRepository;

    public ConversationService(
            ConversationRepository conversationRepository,
            ChatMessageRepository chatMessageRepository
    ) {
        this.conversationRepository = conversationRepository;
        this.chatMessageRepository = chatMessageRepository;
    }

    public long create(String title) {
        String normalizedTitle;

        if (title == null || title.isBlank()) {
            normalizedTitle = "Nova conversa";
        } else {
            normalizedTitle = title.trim();
        }

        return conversationRepository.create(normalizedTitle);
    }

    public List<ConversationDto> findAll() {
        return conversationRepository.findAll();
    }

    public boolean existsById(long conversationId) {
        return conversationRepository.existsById(conversationId);
    }

    @Transactional
    public void delete(long conversationId) {
        if (!conversationRepository.existsById(conversationId)) {
            throw new ConversationNotFoundException(conversationId);
        }

        chatMessageRepository.deleteByConversationId(conversationId);

        conversationRepository.deleteById(conversationId);
    }

    public void rename(long conversationId, String title) {
        if (!conversationRepository.existsById(conversationId)) {
            throw new ConversationNotFoundException(conversationId);
        }

        String normalizedTitle = title.trim();

        if (normalizedTitle.isEmpty()) {
            throw new IllegalArgumentException(
                    "O título não pode ficar vazio."
            );
        }

        conversationRepository.rename(conversationId, normalizedTitle);
    }

    public boolean updateAutomaticTitle(long conversationId, String title) {
        if (!conversationRepository.existsById(conversationId)) {
            throw new ConversationNotFoundException(conversationId);
        }

        String normalizedTitle = title.trim();

        if (normalizedTitle.isEmpty()) {
            throw new IllegalArgumentException(
                    "O título não pode ficar vazio."
            );
        }

        return conversationRepository.updateAutomaticTitle(
                conversationId,
                normalizedTitle
        );
    }

}
