package com.upc.ecochipstf.services;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class InteligenciaArtificialService {

    private final RestTemplate restTemplate;

    private static final String API_URL = "https://api.groq.com/openai/v1/chat/completions";
    private static final String API_KEY = System.getenv("GROQ_API_KEY");


    public InteligenciaArtificialService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String generarRecomendacion(String datosReporte) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(API_KEY);

        Map<String, Object> body = Map.of(
                "model", "llama-3.1-8b-instant",
                "messages", List.of(
                        Map.of(
                                "role", "user",
                                "content",
                                "Actúa como un eco-coach experto. Analiza los siguientes puntos mensuales (ordenados cronológicamente): "
                                        + datosReporte +
                                        ". Devuelve SIEMPRE la respuesta en español y con el siguiente formato estricto:\n\n" +
                                        "✨ ANÁLISIS:\n" +
                                        "• (Describe en máximo 2 líneas la tendencia general: aumento, caída, estabilidad, picos o irregularidades).\n" +
                                        "• (Explica brevemente por qué ocurrió la tendencia).\n\n" +
                                        "🌱 RECOMENDACIÓN:\n" +
                                        "• (Da 1 recomendación ecológica clara, directa y aplicable basada en los patrones observados).\n\n" +
                                        "Es obligatorio respetar el formato, los títulos y los símbolos."
                        )
                )
        );

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<Map> response =
                    restTemplate.postForEntity(API_URL, entity, Map.class);

            List choices = (List) response.getBody().get("choices");
            Map<String, Object> choice = (Map<String, Object>) choices.get(0);
            Map<String, Object> msg = (Map<String, Object>) choice.get("message");

            return msg.get("content").toString();

        } catch (Exception e) {
            e.printStackTrace();
            return "No se pudo generar recomendación. Pero sigue reciclando :)";
        }
    }
}
