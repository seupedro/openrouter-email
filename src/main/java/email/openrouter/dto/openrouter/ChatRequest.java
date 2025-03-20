package email.openrouter.dto.openrouter;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ChatRequest {
    private String model;
    private List<ChatMessage> chatMessages;
}
