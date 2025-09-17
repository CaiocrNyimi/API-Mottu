package com.fiap.mottu_patio.repository;

import com.fiap.mottu_patio.model.Vaga;
import com.fiap.mottu_patio.model.Patio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VagaRepository extends JpaRepository<Vaga, Long> {
    List<Vaga> findByPatioId(Long patioId);
    List<Vaga> findByPatio(Patio patio);
    Optional<Vaga> findByCodigoAndPatio(String codigo, Patio patio);
    boolean existsByCodigoAndPatioAndOcupadaTrue(String codigo, Patio patio);
    int countByPatioAndOcupadaTrue(Patio patio);
    void deleteAllByPatio(Patio patio);
    Optional<Vaga> findFirstByPatioIdAndOcupadaFalse(Long patioId);
}