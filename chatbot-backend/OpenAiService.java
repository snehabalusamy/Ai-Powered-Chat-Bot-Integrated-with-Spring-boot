package com.ai.chatbot.service;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.json.JSONObject;

@Service
public class OpenAiService {

    public String generateReply(String userMessage) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:11434/api/generate";

        try {
            JSONObject body = new JSONObject();
            body.put("model", "phi");
            body.put("prompt", userMessage);
            body.put("stream", false);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> entity = new HttpEntity<>(body.toString(), headers);

            ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                String responseBody = response.getBody();

                try {
                    JSONObject respJson = new JSONObject(responseBody);
                    if (respJson.has("response")) {
                        return respJson.getString("response").trim();
                    } else {
                        return "Unexpected format: " + responseBody;
                    }
                } catch (Exception ex) {
                    return "Invalid response from Ollama: " + responseBody;
                }
            }
            return "Ollama did not respond successfully (" + response.getStatusCode() + ").";

        } catch (Exception e) {
            e.printStackTrace();
            return "Error while communicating with Ollama: " + e.getMessage();
        }
    }
}
