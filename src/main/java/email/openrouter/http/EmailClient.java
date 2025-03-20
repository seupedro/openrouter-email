package email.openrouter.http;

import email.openrouter.dto.email.SendEmailRequest;
import email.openrouter.dto.email.SendEmailResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "emailClient", url = "https://api.ahasend.com/v1/email")
public interface EmailClient {

    @PostMapping("/send")
    ResponseEntity<SendEmailResponse> sendEmail(
            @RequestHeader("X-Api-Key") String apiKey,
            @RequestBody SendEmailRequest request);
}