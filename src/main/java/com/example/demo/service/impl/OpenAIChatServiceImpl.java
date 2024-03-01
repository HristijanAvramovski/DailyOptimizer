package com.example.demo.service.impl;

import com.example.demo.service.OpenAIChatService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Service
public class OpenAIChatServiceImpl implements OpenAIChatService {
    private final RestTemplate restTemplate;
    private final String apiKey;
    public OpenAIChatServiceImpl(RestTemplate restTemplate, @Value("${openai.api.key}") String apiKey) {
        this.restTemplate = restTemplate;
        this.apiKey = apiKey;
    }
    @Override
    public String generateChatResponse(String inputText) throws JsonProcessingException {
        JsonNodeFactory factory = JsonNodeFactory.instance;
        ObjectNode[] messages = new ObjectNode[50];
        String url = "https://api.openai.com/v1/chat/completions";
        String modelName = "gpt-3.5-turbo";
        for (int i = 0; i < messages.length; i++) {
            ObjectNode message = factory.objectNode();
            message.put("role", "user");
            message.put("content", inputText); // Set the content field to an empty string
            messages[i] = message;
        }
        ObjectMapper objectMapper = new ObjectMapper();
        String messagesJson = objectMapper.writeValueAsString(messages);

        String requestBody = "{\"max_tokens\": 150, \"model\": \"" + modelName + "\", \"messages\": " + messagesJson + "}";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + apiKey);

        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, requestEntity, String.class);
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            ObjectMapper objectMapper2 = new ObjectMapper();
            JsonNode responseJson = objectMapper2.readTree(responseEntity.getBody());

            String content = "";
            JsonNode completionsNode = responseJson.get("choices");
            for (JsonNode choice : completionsNode) {
                content = choice.get("message").get("content").asText();
            }
            return content;
        } else {
            return "Error occurred: " + responseEntity.getStatusCode().toString();
        }
    }
}
