package com.fiap.mottu_patio.repository;

import com.fiap.mottu_patio.model.Patio;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PatioRepository extends JpaRepository<Patio, Long>, JpaSpecificationExecutor<Patio> {
    Optional<Patio> findByNomeIgnoreCase(String nome);
}