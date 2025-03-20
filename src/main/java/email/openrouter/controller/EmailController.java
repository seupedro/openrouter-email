package email.openrouter.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import email.openrouter.dto.email.IncomingEmail;
import email.openrouter.dto.email.SendEmailResponse;
import email.openrouter.exception.OpenRouterException;
import email.openrouter.service.ChatService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

import static email.openrouter.filter.RequestBodyCachingFilter.*;

@RestController("/api/v1")
public class EmailController {

    private final Logger logger = LoggerFactory.getLogger(EmailController.class);
    private final ChatService chatService;

    public EmailController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping("/process")
    public ResponseEntity<SendEmailResponse> processEmail(@RequestBody IncomingEmail incomingEmail) {
        return chatService.processRequest(incomingEmail);
    }

    @ExceptionHandler(OpenRouterException.class)
    public ResponseEntity<?> handleException(OpenRouterException ex,
                                             HttpServletRequest request,
                                             ObjectMapper objectMapper) {

        if (request instanceof CachedBodyHttpServletRequest cachedRequest) {
            byte[] cachedBody = cachedRequest.getCachedBody();

            try {
                IncomingEmail incomingEmail = objectMapper.readValue(cachedBody, IncomingEmail.class);
                return chatService.processError(incomingEmail, ex);
            } catch (IOException e) {
                logger.error("Failed to parse request body", e);
            }
        }

        return ResponseEntity.status(ex.getErrorCode()).body(ex.getErrorResponse().getError());
    }
}
