package email.openrouter.dto.openrouter;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ErrorResponse {
    @JsonProperty("user_id")
    private String userId;
    private Error error;

    @Data
    public static class Error {
        private String message;
        private int code;
    }
}
