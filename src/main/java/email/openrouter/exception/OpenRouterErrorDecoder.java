package email.openrouter.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import email.openrouter.dto.openrouter.ErrorResponse;
import feign.FeignException;
import feign.Response;
import feign.codec.ErrorDecoder;

import java.io.IOException;

public class OpenRouterErrorDecoder implements ErrorDecoder {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Exception decode(String methodKey, Response response) {

        try {
            byte[] responseBody = response.body().asInputStream().readAllBytes();
            ErrorResponse errorResponse = objectMapper.readValue(responseBody, ErrorResponse.class);

            return new OpenRouterException(
                    errorResponse.getError().getMessage(),
                    errorResponse.getError().getCode(),
                    errorResponse
            );
        } catch (IOException e) {
            return FeignException.errorStatus(methodKey, response);
        }
    }
}
