package com.mondorevive.TRESPOT.stabilimento.sistemaEsterno;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SistemaEsternoRepository extends JpaRepository<SistemaEsterno, Integer> {
    @Query("select se from SistemaEsterno se where se.codice = :codice")
    SistemaEsterno getByCodice(String codice);
}