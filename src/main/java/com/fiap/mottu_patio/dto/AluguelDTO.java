package com.fiap.mottu_patio.dto;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
public class AluguelDTO {
    private Long id;
    private Long userId;
    private String userName;
    private Long motoId;
    private String motoModel;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String status;
}