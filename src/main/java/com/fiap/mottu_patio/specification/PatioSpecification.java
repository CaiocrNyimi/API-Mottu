package com.fiap.mottu_patio.specification;

import com.fiap.mottu_patio.model.Patio;
import org.springframework.data.jpa.domain.Specification;

public class PatioSpecification {

    public static Specification<Patio> hasNome(String nome) {
        return (root, query, criteriaBuilder) -> {
            if (nome == null || nome.isEmpty()) return null;
            return criteriaBuilder.like(criteriaBuilder.lower(root.get("nome")), "%" + nome.toLowerCase() + "%");
        };
    }

    public static Specification<Patio> hasEndereco(String endereco) {
        return (root, query, criteriaBuilder) -> {
            if (endereco == null || endereco.isEmpty()) return null;
            return criteriaBuilder.like(criteriaBuilder.lower(root.get("endereco")), "%" + endereco.toLowerCase() + "%");
        };
    }

    public static Specification<Patio> hasCapacidade(Integer capacidade) {
        return (root, query, criteriaBuilder) -> {
            if (capacidade == null) return null;
            return criteriaBuilder.equal(root.get("capacidade"), capacidade);
        };
    }
}