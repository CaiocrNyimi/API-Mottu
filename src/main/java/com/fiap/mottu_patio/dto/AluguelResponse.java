package com.fiap.mottu_patio.dto;

import com.fiap.mottu_patio.model.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AluguelResponse {

    private Long id;
    private Long userId;
    private String userName;
    private Long motoId;
    private String motoModel;
    private String motoPlaca;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Status status;
}