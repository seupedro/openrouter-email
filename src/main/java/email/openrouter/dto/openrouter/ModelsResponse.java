package email.openrouter.dto.openrouter;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.List;
import java.util.Map;

@Data
public class ModelsResponse {
    private List<ModelData> data;

    @Data
    public static class ModelData {
        private String id;
        private String name;
        private Long created;
        private String description;

        @JsonProperty("context_length")
        private Integer contextLength;

        private Architecture architecture;

        @JsonProperty("top_provider")
        private ProviderInfo topProvider;

        private Pricing pricing;

        @JsonProperty("per_request_limits")
        private Map<String, String> perRequestLimits;
    }

    @Data
    public static class Architecture {
        private String modality;
        private String tokenizer;
    }

    @Data
    public static class Pricing {
        private String prompt;
        private String completion;
        private String image;
        private String request;

        @JsonProperty("input_cache_read")
        private String inputCacheRead;

        @JsonProperty("input_cache_write")
        private String inputCacheWrite;

        @JsonProperty("web_search")
        private String webSearch;

        @JsonProperty("internal_reasoning")
        private String internalReasoning;
    }

    @Data
    public static class ProviderInfo {
        @JsonProperty("context_length")
        private Integer contextLength;

        @JsonProperty("max_completion_tokens")
        private Integer maxCompletionTokens;

        @JsonProperty("is_moderated")
        private Boolean isModerated;
    }
}







