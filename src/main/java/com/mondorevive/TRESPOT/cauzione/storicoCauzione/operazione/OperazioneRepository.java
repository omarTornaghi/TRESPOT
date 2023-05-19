package com.mondorevive.TRESPOT.cauzione.storicoCauzione.operazione;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface OperazioneRepository extends JpaRepository<Operazione, Long> {
    @Query("select o from Operazione o where o.codice = :codice")
    Optional<Operazione> getByCodice(String codice);
}