package com.fiap.mottu_patio.specification;

import com.fiap.mottu_patio.model.Moto;
import com.fiap.mottu_patio.model.Patio;
import org.springframework.data.jpa.domain.Specification;


public class MotoSpecification {

    public static Specification<Moto> hasPatio(Patio patio) {
        return (root, query, criteriaBuilder) -> {
            if (patio == null) return null;
            return criteriaBuilder.equal(root.get("patio"), patio);
        };
    }

    public static Specification<Moto> hasModelo(String modelo) {
        return (root, query, criteriaBuilder) -> {
            if (modelo == null || modelo.isEmpty()) return null;
            return criteriaBuilder.like(root.get("modelo"), "%" + modelo + "%");
        };
    }

    public static Specification<Moto> hasAno(Integer ano) {
        return (root, query, criteriaBuilder) -> {
            if (ano == null) return null;
            return criteriaBuilder.equal(root.get("ano"), ano);
        };
    }

}