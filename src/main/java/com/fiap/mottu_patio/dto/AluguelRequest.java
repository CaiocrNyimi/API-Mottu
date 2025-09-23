package com.fiap.mottu_patio.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class AluguelRequest {
    private Long userId;
    private Long motoId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}