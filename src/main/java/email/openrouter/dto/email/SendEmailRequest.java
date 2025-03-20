package email.openrouter.dto.email;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SendEmailRequest {
    private From from;
    private List<Recipient> recipients;
    private Content content;

    @Data
    @Builder
    public static class From {
        private String name;
        private String email;
    }

    @Data
    @Builder
    public static class Recipient {
        private String name;
        private String email;
    }

    @Data
    @Builder
    public static class Content {
        private String subject;

        @JsonProperty("text_body")
        private String textBody;

        @JsonProperty("html_body")
        private String htmlBody;
    }
}





