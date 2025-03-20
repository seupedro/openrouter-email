package email.openrouter.exception;

import email.openrouter.dto.openrouter.ErrorResponse;
import lombok.Getter;

@Getter
public class OpenRouterException extends RuntimeException {
    private final int errorCode;
    private final ErrorResponse errorResponse;

    public OpenRouterException(String message, int errorCode, ErrorResponse errorResponse) {
        super(message);
        this.errorCode = errorCode;
        this.errorResponse = errorResponse;
    }
}