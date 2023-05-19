package com.mondorevive.TRESPOT.utente.utenteStabilimento;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UtenteStabilimentoRepository extends JpaRepository<UtenteStabilimento, Long> {
    @Query("select us,s from UtenteStabilimento us inner join us.stabilimento s where us.utente.id = :id")
    List<UtenteStabilimento> getStabilimentiByUtenteId(Long id);

    @Modifying
    @Query("delete from UtenteStabilimento us where us.utente.id = :id")
    void deleteByUtenteId(Long id);

    @Modifying @Query("update UtenteStabilimento us set us.stabilimento.id = :idStabilimento where us.id = :idAssociazione")
    void updateAssociazione(Long idAssociazione, Long idStabilimento);

    @Query("select us,s,u from UtenteStabilimento us inner join us.stabilimento s inner join us.utente u " +
            "where u.username like :username")
    List<UtenteStabilimento> getByUtenteUsername(String username);
}