package com.mondorevive.TRESPOT.pianoRevisione;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface PianoRevisioneRepository extends JpaRepository<PianoRevisione, Long> {
    @Modifying @Query("update PianoRevisione pr set pr.codice = :codice, pr.descrizione = :descrizione where pr.id = :id")
    void updatePianoRevisione(Long id, String codice, String descrizione);

}