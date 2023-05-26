package com.mondorevive.TRESPOT.pianoRevisione.revisione;

import com.mondorevive.TRESPOT.responses.DettaglioRevisioneResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RevisioneRepository extends JpaRepository<Revisione, Long> {
    @Query("select r " +
            "from Revisione r inner join r.storicoCauzione sc " +
            "where sc.timestampOperazione = (" +
            "select max(sc1.timestampOperazione) from Revisione r1 inner join r1.storicoCauzione sc1 where sc1.cauzione.id = :idCauzione) " +
            "and sc.cauzione.id = :idCauzione")
    Optional<Revisione> getUltimaRevisione(Long idCauzione);
    
    @Query("select new com.mondorevive.TRESPOT.responses.DettaglioRevisioneResponse(" +
            "c.id,c.epcTag,c.matricola,stato.codice,m.descrizione,tc.descrizione," +
            "r.id,r.dataRevisione,r.conformitaTotale," +
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
}