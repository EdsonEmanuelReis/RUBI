package site.rubi;

import org.junit.jupiter.api.Test;
import site.rubi.Service.ChatService;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ChatServiceTest {

    @Test
    void deveRetornarMensagemRecebida() {
        ChatService service = new ChatService();

        String resposta = service.processMessage("Olá, RUBI.");

        assertEquals(
                "Recebi sua mensagem: Olá, RUBI.",
                resposta
        );
    }
}