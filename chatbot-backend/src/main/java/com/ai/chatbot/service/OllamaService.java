package com.ai.chatbot.service;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.json.JSONObject;

@Service
public class OllamaService {

    public String generateReply(String userMessage) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:11434/api/generate";

        try {
            // Build request body
            JSONObject body = new JSONObject();
            body.put("model", "phi");  // ✅ Ensure you’ve pulled this model
            body.put("prompt", userMessage);
            body.put("stream", false);

            // Set headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> entity = new HttpEntity<>(body.toString(), headers);

            // Call Ollama
            ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);

            System.out.println("Ollama raw response: " + response.getBody()); // 👈 Debug output

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                try {
                    JSONObject respJson = new JSONObject(response.getBody());
                    if (respJson.has("response")) {
                        return respJson.getString("response").trim();
                    } else {
                        return "Unexpected response format: " + response.getBody();
                    }
                } catch (Exception e) {
                    return "Invalid JSON from Ollama: " + response.getBody();
                }
            } else {
                return "Ollama returned HTTP " + response.getStatusCode();
            }

        } catch (Exception e) {
            e.printStackTrace(); // shows in Spring Boot console
            return "Error connecting to Ollama: " + e.getMessage();
        }
    }
}
