package email.openrouter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "email.openrouter.http")
public class OpenRouterEmailApplication {

    public static void main(String[] args) {
        SpringApplication.run(OpenRouterEmailApplication.class, args);
    }

}
