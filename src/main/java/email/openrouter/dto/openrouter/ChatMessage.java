package email.openrouter.dto.openrouter;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChatMessage {
    private String role;
    private String content;
}
