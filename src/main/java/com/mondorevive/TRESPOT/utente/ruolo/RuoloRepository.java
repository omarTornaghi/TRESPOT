package com.mondorevive.TRESPOT.utente.ruolo;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RuoloRepository extends JpaRepository<Ruolo, Integer> {
    Optional<Ruolo> findRuoloByCodice(String codice);
}