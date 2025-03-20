package email.openrouter.config;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EnvConfig {

    @Value("${OPENROUTER_TOKEN}")
    private String openRouterToken;

    @Value("${EMAILPROVIDER_TOKEN}")
    private String emailToken;

    @Bean
    public String openRouterToken() {
        return openRouterToken;
    }

    @Bean
    public String emailToken() {
        return openRouterToken;
    }

    @PostConstruct
    public void validateTokens() {
        if (openRouterToken == null || openRouterToken.isEmpty()) {
            throw new IllegalStateException("OPENROUTER_TOKEN environment variable is not set or is empty");
        }

        if (emailToken == null || emailToken.isEmpty()) {
            throw new IllegalStateException("EMAILPROVIDER_TOKEN environment variable is not set or is empty");
        }
    }
}
