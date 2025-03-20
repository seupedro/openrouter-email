package email.openrouter.http;

import email.openrouter.config.OpenRouterClientConfig;
import email.openrouter.dto.openrouter.ModelsResponse;
import email.openrouter.dto.openrouter.ChatRequest;
import email.openrouter.dto.openrouter.ChatResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(
        name = "openRouterClient",
        url = "https://openrouter.ai/api/v1",
        configuration = OpenRouterClientConfig.class
)
public interface OpenRouterClient {

    @PostMapping("/chat/completions")
    ResponseEntity<ChatResponse> getChatCompletion(
            @RequestHeader("Authorization") String authorization,
            @RequestBody ChatRequest request);

    @GetMapping("/models")
    ModelsResponse getAvailableModels();
}
