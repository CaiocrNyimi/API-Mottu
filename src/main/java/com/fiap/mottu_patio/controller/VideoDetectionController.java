package com.fiap.mottu_patio.controller;

import com.fiap.mottu_patio.dto.VideoDetectionResponse;
import com.fiap.mottu_patio.service.VideoDetectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/detections")
public class VideoDetectionController {

    @Autowired
    private VideoDetectionService service;

    @GetMapping("/{videoName}")
    public ResponseEntity<List<VideoDetectionResponse>> getDetections(@PathVariable String videoName) {
        try {
            List<VideoDetectionResponse> detections = service.fetchDetections(videoName);
            if (detections.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(detections);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }
    }
}