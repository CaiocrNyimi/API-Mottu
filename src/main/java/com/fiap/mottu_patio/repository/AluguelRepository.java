package com.fiap.mottu_patio.repository;

import com.fiap.mottu_patio.model.Aluguel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AluguelRepository extends JpaRepository<Aluguel, Long> {
    List<Aluguel> findByMotoIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(Long motoId, LocalDateTime endDate, LocalDateTime startDate);
    List<Aluguel> findByUserIdAndStatus(Long userId, String status);
}