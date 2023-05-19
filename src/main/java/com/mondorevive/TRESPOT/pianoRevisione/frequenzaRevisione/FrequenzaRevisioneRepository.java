package com.mondorevive.TRESPOT.pianoRevisione.frequenzaRevisione;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FrequenzaRevisioneRepository extends JpaRepository<FrequenzaRevisione, Long> {
    @Query("select f from FrequenzaRevisione f where f.pianoRevisione.id = :idPianoRevisione order by f.daAnni")
    List<FrequenzaRevisione> findByIdPianoRevisione(Long idPianoRevisione);
    @Modifying @Query("delete from FrequenzaRevisione where pianoRevisione.id = :id")
    void deleteByIdPianoRevisione(Long id);
}