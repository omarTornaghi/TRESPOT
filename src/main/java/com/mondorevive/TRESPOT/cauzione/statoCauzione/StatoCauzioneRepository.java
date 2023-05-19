package com.mondorevive.TRESPOT.cauzione.statoCauzione;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface StatoCauzioneRepository extends JpaRepository<StatoCauzione, Long> {
    @Query("select s from StatoCauzione s where s.codice = :name")
    StatoCauzione getByCodice(String name);
}