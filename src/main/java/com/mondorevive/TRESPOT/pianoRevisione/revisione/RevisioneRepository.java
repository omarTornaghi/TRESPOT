package com.mondorevive.TRESPOT.pianoRevisione.revisione;

import com.mondorevive.TRESPOT.responses.DettaglioRevisioneResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface RevisioneRepository extends JpaRepository<Revisione, Long> {
    @Query("select r " +
            "from Revisione r inner join r.storicoCauzione sc " +
            "where sc.timestampOperazione = (" +
            "select max(sc1.timestampOperazione) from Revisione r1 inner join r1.storicoCauzione sc1 where sc1.cauzione.id = :idCauzione) " +
            "and sc.cauzione.id = :idCauzione " +
            "order by r.id desc")
    List<Revisione> getUltimaRevisione(Long idCauzione);
    
    @Query("select new com.mondorevive.TRESPOT.responses.DettaglioRevisioneResponse(" +
            "c.id,c.epcTag,c.matricola,stato.codice,m.descrizione,tc.descrizione," +
            "r.id,r.dataRevisione,r.mancaAggiornamento,r.conformitaTotale," +
            "r.targaPresente," +
            "r.conformitaDisegnoTecnico," +
            "r.interventoMembrature," +
            "r.descrizioneInterventoMembrature," +
            "r.interventoSaldatura," +
            "r.cernieraBullonata," +
            "r.cattivoUsoInforcatura," +
            "r.cattivoUsoCollisione," +
            "r.altroIntervento," +
            "r.stabilitaGlobale," +
            "r.funzionamentoRfid," +
            "r.ulterioriNote," +
            "u.username) " +
            "from Revisione r inner join r.storicoCauzione sc " +
            "inner join sc.utente u " +
            "inner join sc.cauzione c " +
            "inner join c.tipologiaCauzione tc " +
            "inner join c.statoCauzione stato " +
            "inner join c.magazzino m " +
            "where r.id = :id")
    DettaglioRevisioneResponse getDettaglioById(Long id);

    @Modifying @Query("delete from Revisione r where r.storicoCauzione.id in " +
            "(select sc.id from StoricoCauzione sc where sc.cauzione.id = :id)")
    void deleteByIdCauzione(Long id);

    @Query("select r from Revisione r inner join r.storicoCauzione sc where r.id = :id")
    Optional<Revisione> findRevisioneById(Long id);

    @Modifying
    @Query("update Revisione r set " +
            "r.dataRevisione = :dataRevisione," +
            "r.mancaAggiornamento = :mancaAggiornamento," +
            "r.conformitaTotale = :conformitaTotale," +
            "r.targaPresente = :targaPresente," +
            "r.conformitaDisegnoTecnico = :conformitaDisegnoTecnico," +
            "r.interventoMembrature = :interventoMembrature," +
            "r.descrizioneInterventoMembrature = :descrizioneInterventoMembrature," +
            "r.interventoSaldatura = :interventoSaldatura," +
            "r.cernieraBullonata = :cernieraBullonata," +
            "r.cattivoUsoInforcatura = :cattivoUsoInforcatura," +
            "r.cattivoUsoCollisione = :cattivoUsoCollisione," +
            "r.altroIntervento = :altroIntervento," +
            "r.stabilitaGlobale = :stabilitaGlobale," +
            "r.funzionamentoRfid = :funzionamentoRfid," +
            "r.ulterioriNote = :ulterioriNote " +
            "where r.id = :id")
    void updateRevisione(Long id, LocalDateTime dataRevisione, Boolean mancaAggiornamento,
                         Boolean conformitaTotale, String targaPresente, String conformitaDisegnoTecnico,
                         String interventoMembrature, String descrizioneInterventoMembrature,
                         String interventoSaldatura, String cernieraBullonata,
                         String cattivoUsoInforcatura, String cattivoUsoCollisione, String altroIntervento,
                         String stabilitaGlobale, String funzionamentoRfid, String ulterioriNote);
}