package com.mondorevive.TRESPOT.stabilimento;

import org.springframework.data.jpa.repository.*;

import java.util.List;
import java.util.Optional;

public interface StabilimentoRepository extends JpaRepository<Stabilimento, Long>{

    @Query("select s,se from Stabilimento s inner join s.sistemaEsterno se where s.id = :id")
    Optional<Stabilimento> getStabilimentoById(Long id);
    @Query("select s from Stabilimento s where s.codice = :codice")
    Optional<Stabilimento> findByByCodice(String codice);

    @Modifying @Query("update Stabilimento s set s.codice = :codice,s.descrizione = :descrizione,s.sistemaEsterno.id = :idSistemaEsterno where s.id = :id")
    void updateStabilimento(Long id, String codice, String descrizione, Integer idSistemaEsterno);

    @Query("select s,se from Stabilimento s inner join s.sistemaEsterno se")
    List<Stabilimento> findAllStabilimenti();

    @Query("select s from Stabilimento s where s.codice = :codiceStabilimento")
    Stabilimento getByCodice(String codiceStabilimento);
}