package email.openrouter.config;

import email.openrouter.exception.OpenRouterErrorDecoder;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenRouterClientConfig {

    @Bean
    public ErrorDecoder errorDecoder() {
        return new OpenRouterErrorDecoder();
    }
}