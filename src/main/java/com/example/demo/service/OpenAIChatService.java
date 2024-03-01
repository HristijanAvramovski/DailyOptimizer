package com.example.demo.service;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface OpenAIChatService {
    String generateChatResponse(String inputText) throws JsonProcessingException;
}
