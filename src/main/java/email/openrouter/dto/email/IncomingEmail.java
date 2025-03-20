package email.openrouter.dto.email;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@lombok.Data
@NoArgsConstructor
@AllArgsConstructor
public class IncomingEmail {
    private String type;
    private String timestamp;
    private Data data;

    @lombok.Data
    public static class Data {
        private String id;
        private String from;
        private String replyTo;
        private String to;
        private String subject;
        private String messageId;
        private int size;
        private int spamScore;
        private boolean bounce;
        private String cc;
        private String date;
        private String inReplyTo;
        private String references;
        private String autoSubmitted;
        private String htmlBody;
        private String plainBody;
        private String replyFromPlainBody;
        private List<Attachment> attachments;
    }

    @lombok.Data
    public static class Attachment {
        private String filename;
        private String contentType;
        private String contentId;
        private String data;
    }
}






