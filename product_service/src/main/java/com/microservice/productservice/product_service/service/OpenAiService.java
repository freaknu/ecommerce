package com.microservice.productservice.product_service.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservice.productservice.product_service.dto.filter.ProductFilterResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OpenAiService {

    @Qualifier("externalBuilder")
    private final RestClient.Builder restClientBuilder;

    private final ObjectMapper objectMapper;
    private final FilterService filterService;

    @Value("${openai.api.key}")
    private String apiKey;

    private static final String OPENAI_URL = "https://api.openai.com/v1/chat/completions";

    public ProductFilterResponseDto parse(String query) {

        RestClient restClient = restClientBuilder.build();

        ProductFilterResponseDto catalog = filterService.getFilters();
        String dynamicPrompt = buildPrompt(catalog);

        Map<String, Object> body = Map.of(
                "model", "gpt-4o-mini",
                "messages", List.of(
                        Map.of("role", "system", "content", dynamicPrompt),
                        Map.of("role", "user", "content", query)
                ),
                "temperature", 0.05
        );

        Map response = restClient.post()
                .uri(OPENAI_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + apiKey)
                .body(body)
                .retrieve()
                .body(Map.class);

        if (response == null)
            throw new RuntimeException("OpenAI response is empty");

        List choices = (List) response.get("choices");
        Map firstChoice = (Map) choices.get(0);
        Map message = (Map) firstChoice.get("message");
        String json = (String) message.get("content");

        try {
            return objectMapper.readValue(json, ProductFilterResponseDto.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse OpenAI JSON: " + json, e);
        }
    }

    private String buildPrompt(ProductFilterResponseDto c) {
        return """
You are an e-commerce AI search engine.

User may write in English, Hindi, or Hinglish.

IMPORTANT:
You must ONLY use values from the catalog lists below.
Never invent values.

Catalog:

Categories: %s
Brands: %s
Fabrics: %s
Fits: %s
Sleeve Types: %s
Neck Types: %s
Patterns: %s
Occasions: %s
Seasons: %s

Also extract price if mentioned (example: under 1500, below 2000, between 500 and 1200).

Convert the user query into this JSON format:

{
  "categories": [],
  "genders": [],
  "brands": [],
  "sizes": [],
  "fabrics": [],
  "fits": [],
  "sleeveTypes": [],
  "neckTypes": [],
  "patterns": [],
  "occasions": [],
  "seasons": [],
  "minPrice": null,
  "maxPrice": null
}

Rules:
- Use ONLY values from the catalog lists
- Extract price only if user mentions it
- If no match exists, return empty array
- Never invent values
- Only return JSON
""".formatted(
                c.getCategories(),
                c.getBrands(),
                c.getFabrics(),
                c.getFits(),
                c.getSleeveTypes(),
                c.getNeckTypes(),
                c.getPatterns(),
                c.getOccasions(),
                c.getSeasons()
        );
    }

}
