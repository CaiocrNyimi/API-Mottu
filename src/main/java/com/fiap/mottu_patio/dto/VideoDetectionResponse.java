package com.fiap.mottu_patio.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonProperty;

@Data
@AllArgsConstructor
public class VideoDetectionResponse {

    private Long id;

    @JsonProperty("frame_id")
    private Integer frameId;

    private Double timestamp;

    @JsonProperty("video_file")
    private String videoFile;

    @JsonProperty("total_motos")
    private Integer totalMotos;

    private String label;
    private Double confianca;
    private Integer x1;
    private Integer y1;
    private Integer x2;
    private Integer y2;

    public VideoDetectionResponse() {}
}