package site.rubi.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RenameConversationRequest(
        @NotBlank(message = "O título não pode ficar vazio.")
        @Size(max = 250, message = "O título deve ter no máximo 250 caracteres.")
        String title
) {}
