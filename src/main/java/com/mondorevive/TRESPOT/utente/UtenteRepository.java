package com.mondorevive.TRESPOT.utente;

import org.springframework.data.jpa.repository.*;

import java.util.List;
import java.util.Optional;

public interface UtenteRepository extends JpaRepository<Utente, Long> {

    @Query("select u,r from Utente u inner join u.ruolo r where u.attivo = true order by u.username")
    List<Utente> getAll();
    @Query("select u,r,m,s,se from Utente u " +
            "inner join u.ruolo r " +
            "inner join u.magazzino m " +
            "inner join m.stabilimento s " +
            "inner join s.sistemaEsterno se " +
            "where u.username = :username and u.attivo = true")
    Optional<Utente> findUserByUsername(String username);
    boolean existsByUsernameAndAttivo(String username, Boolean attivo);

    @Modifying
    @Query("update Utente u set u.password = :encode where u.username = :username")
    void updatePassword(String username, String encode);

    @Query("select u,r from Utente u inner join u.ruolo r left join u.magazzino m where u.id = :id")
    Optional<Utente> getUtenteWithRuolo(Long id);

    @Modifying
    @Query("update Utente u set u.username = :username, u.password = :password, u.nome = :nome, u.cognome = :cognome, u.ruolo.id = :idRuolo, u.magazzino.id = :idMagazzino where u.id = :id")
    void aggiornaUtenteEPassword(Long id, String username, String password, String nome, String cognome, Integer idRuolo, Long idMagazzino);

    @Modifying
    @Query("update Utente u set u.username = :username, u.nome = :nome, u.cognome = :cognome, u.ruolo.id = :idRuolo, u.magazzino.id = :idMagazzino where u.id = :id")
    void aggiornaUtente(Long id, String username, String nome, String cognome, Integer idRuolo, Long idMagazzino);

    @Modifying
    @Query("update Utente u set u.username = :username, u.nome = :nome, u.cognome = :cognome where u.id = :id")
    void aggiornaProfilo(Long id, String username, String nome, String cognome);
    @Modifying
    @Query("update Utente u set u.username = :usernameFittizio, u.attivo = false where u.id = :id")
    void nascondiUtente(Long id, String usernameFittizio);
    @Modifying @Query("update Utente u set u.password = :password where u.id = :id")
    void aggiornaPassword(Long id, String password);
}