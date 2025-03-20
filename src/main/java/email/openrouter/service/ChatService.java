package email.openrouter.service;

import email.openrouter.dto.email.IncomingEmail;
import email.openrouter.dto.email.SendEmailRequest;
import email.openrouter.dto.email.SendEmailResponse;
import email.openrouter.dto.openrouter.ChatRequest;
import email.openrouter.dto.openrouter.ChatResponse;
import email.openrouter.dto.openrouter.ChatMessage;
import email.openrouter.exception.OpenRouterException;
import email.openrouter.http.EmailClient;
import email.openrouter.http.OpenRouterClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static email.openrouter.dto.email.SendEmailRequest.*;

@Service
public class ChatService {

    private final EmailClient emailClient;
    private final OpenRouterClient openRouterClient;
    private final String openRouterToken;
    private final String emailToken;

    public ChatService(EmailClient emailClient, OpenRouterClient openRouterClient, String openRouterToken, String emailToken) {
        this.emailClient = emailClient;
        this.openRouterClient = openRouterClient;
        this.openRouterToken = openRouterToken;
        this.emailToken = emailToken;
    }

    public ResponseEntity<SendEmailResponse> processRequest(IncomingEmail incomingEmail) {

        ChatMessage chatMessage = ChatMessage.builder()
                .role("user")
                .content(incomingEmail.getData().getPlainBody())
                .build();

        String model = Optional.ofNullable(incomingEmail.getData().getSubject())
                .orElse("deepseek/deepseek-r1");

        ChatRequest chatRequest = ChatRequest.builder()
                .model(model)
                .chatMessages(List.of(chatMessage))
                .build();

        ChatResponse response = openRouterClient.getChatCompletion("Bearer " + openRouterToken, chatRequest).getBody();

        SendEmailRequest sendEmailRequest = buildSendEmailRequest(incomingEmail, response.toJson());

        return emailClient.sendEmail(emailToken, sendEmailRequest);
    }

    public ResponseEntity<SendEmailResponse> processError(IncomingEmail incomingEmail, OpenRouterException exception) {
        String error = exception.getErrorResponse().getError().getMessage();
        SendEmailRequest sendEmailRequest = buildSendEmailRequest(incomingEmail, error);

        return emailClient.sendEmail(emailToken, sendEmailRequest);
    }

    private static SendEmailRequest buildSendEmailRequest(IncomingEmail incomingEmail, String response) {
        Recipient recipient = Recipient.builder()
                .name(incomingEmail.getData().getFrom())
                .email(incomingEmail.getData().getFrom())
                .build();

        Content content = Content.builder()
                .subject("RE: " + incomingEmail.getData().getSubject())
                .textBody(response)
                .build();

        SendEmailRequest sendEmailRequest = builder()
                .from(From.builder().name("Test OpenRouter.email").build())
                .recipients(List.of(recipient))
                .content(content)
                .build();

        return sendEmailRequest;
    }
}
