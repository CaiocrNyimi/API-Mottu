package com.fiap.mottu_patio.service;

import com.fiap.mottu_patio.dto.VideoDetectionResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class VideoDetectionService {

    public List<VideoDetectionResponse> fetchDetections(String videoName) throws Exception {
        String endpoint = "http://localhost:8000/verdados/" + videoName + ".mp4";
        URL url = new URL(endpoint);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        try (InputStream inputStream = conn.getInputStream()) {
            String rawJson = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
            System.out.println("JSON recebido: " + rawJson);

            ObjectMapper mapper = new ObjectMapper();
            mapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
            return mapper.readValue(rawJson, new TypeReference<List<VideoDetectionResponse>>() {});
        }
    }
}