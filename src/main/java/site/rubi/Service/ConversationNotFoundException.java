
package site.rubi.Service;

public class ConversationNotFoundException extends RuntimeException {

    public ConversationNotFoundException(long conversationId) {
        super("Conversa não encontrada: " + conversationId);
    }
}
