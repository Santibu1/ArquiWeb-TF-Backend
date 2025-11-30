package com.upc.ecochipstf.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class EcoChatService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    private static final String API_URL = "https://api.adviceslip.com/advice";
    private static final String API_KEY = "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";

    public EcoChatService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    private String traducirConsejo(String texto) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(API_KEY);

            Map<String, Object> body = Map.of(
                    "model", "llama-3.1-8b-instant",
                    "messages", List.of(
                            Map.of("role", "user", "content",
                                    "Traduce al español de forma natural este texto: " + texto)
                    )
            );

            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

            ResponseEntity<Map> response =
                    restTemplate.postForEntity(
                            "https://api.groq.com/openai/v1/chat/completions",
                            entity,
                            Map.class
                    );

            List choices = (List) response.getBody().get("choices");
            Map<String, Object> choice = (Map<String, Object>) choices.get(0);
            Map<String, Object> msg = (Map<String, Object>) choice.get("message");

            return msg.get("content").toString().trim();

        } catch (Exception e) {
            e.printStackTrace();
            return texto; // si falla, enviamos el original
        }
    }

    public String obtenerConsejo() {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Accept", "application/json");

            HttpEntity<Void> entity = new HttpEntity<>(headers);

            // 🚀 Importante: Obtener SIEMPRE como String
            ResponseEntity<String> response =
                    restTemplate.exchange(API_URL, HttpMethod.GET, entity, String.class);

            // Convertir a JSON
            Map json = objectMapper.readValue(response.getBody(), Map.class);
            Map slip = (Map) json.get("slip");

            String consejoIngles = slip.get("advice").toString();

            // --- Llamamos a Groq para traducirlo al español ---
            String consejoEspanol = traducirConsejo(consejoIngles);

            return "🌱 EcoTip del día: " + consejoEspanol;

        } catch (Exception e) {
            e.printStackTrace();
            return "No se pudo obtener un eco-consejo por ahora.";
        }
    }
}
