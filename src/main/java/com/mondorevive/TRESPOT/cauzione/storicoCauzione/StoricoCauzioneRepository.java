package com.mondorevive.TRESPOT.cauzione.storicoCauzione;

import com.mondorevive.TRESPOT.pojo.UltimoCaricoCauzione;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface StoricoCauzioneRepository extends JpaRepository<StoricoCauzione, Long> {
    @Query("select sc,stc,m,u,op,r " +
            "from StoricoCauzione sc inner join sc.statoCauzione stc " +
            "inner join sc.magazzino m " +
            "left join sc.utente u " +
            "inner join sc.operazione op " +
            "left join sc.revisione r " +
            "where sc.cauzione.id = :id and " +
            "sc.timestampOperazione >= :dataInizio and " +
            "sc.timestampOperazione <= :dataFine " +
            "order by sc.timestampOperazione desc ")
    List<StoricoCauzione> getStoricoByIdCauzioneDataInizioDataFine(Long id, LocalDateTime dataInizio, LocalDateTime dataFine);

    //IN REALTA' QUESTA QUERY OTTIENE L'ULTIMO TIMESTAMP PER OGNI CAUZIONE
    @Query("select new com.mondorevive.TRESPOT.pojo.UltimoCaricoCauzione(sc.cauzione.id,max(sc.timestampOperazione)) " +
            "from StoricoCauzione sc " +
            "where sc.cauzione.id in :idCauzioniList " +
            "group by sc.cauzione.id")
    List<UltimoCaricoCauzione> getUltimiCarichi(List<Long> idCauzioniList);

    @Modifying @Query("delete from StoricoCauzione sc where sc.cauzione.id = :id")
    void deleteByIdCauzione(Long id);
}