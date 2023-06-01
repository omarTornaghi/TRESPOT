package com.mondorevive.TRESPOT.statistiche;

import com.mondorevive.TRESPOT.cauzione.Cauzione;
import com.mondorevive.TRESPOT.responses.ChartDataResponse;
import com.mondorevive.TRESPOT.responses.ChartDataTipologieCauzione;
import com.mondorevive.TRESPOT.responses.StatisticaTipologiaCauzioneCliente;
import com.mondorevive.TRESPOT.responses.StatisticaTipologiaCauzioneMagazzinoInterno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface StatisticheCauzioneRepository extends JpaRepository<Cauzione, Long> {
    @Query("select count(c) from Cauzione c")
    Long countCauzioni();

    @Query("select count(c) from Cauzione c " +
            "where c.magazzino.stabilimento.id in :idStabilimentiList and c.statoCauzione.id = :idLibero")
    Long countCauzioniLibere(List<Long> idStabilimentiList, Long idLibero);

    @Query("select count(c) from Cauzione c " +
            "where c.magazzino.stabilimento.id not in :idStabilimentiList")
    Long countCauzioniFuori(List<Long>idStabilimentiList);

    @Query("select count(c) from Cauzione c " +
            "where c.magazzino.stabilimento.id in :idStabilimentiList and c.statoCauzione.id = :idLibero")
    Long countCauzioniManutenzione(List<Long>idStabilimentiList,Long idLibero);

    @Query("select count(c) from Cauzione c " +
            "where c.magazzino.stabilimento.id in :idStabilimentiList and c.statoCauzione.id = :idLibero")
    Long countCauzioniRiparazione(List<Long>idStabilimentiList,Long idLibero);

    @Query("select new com.mondorevive.TRESPOT.responses.ChartDataResponse(function('TO_CHAR',sc.timestampOperazione,'yyyy, mm'), " +
            "SUM(CASE WHEN r.conformitaTotale = true THEN 1 ELSE 0 END)," +
            "SUM(CASE WHEN r.conformitaTotale = false THEN 1 ELSE 0 END)) " +
            "from Revisione r inner join r.storicoCauzione sc " +
            "inner join sc.magazzino m " +
            "where sc.timestampOperazione >= :dataInizio and " +
            "sc.timestampOperazione <= :dataFine and " +
            "m.stabilimento.id in :idStabilimentiList " +
            "group by function('TO_CHAR',sc.timestampOperazione,'yyyy, mm') " +
            "order by function('TO_CHAR',sc.timestampOperazione,'yyyy, mm')")
    List<ChartDataResponse>getRevisioniChartData(LocalDateTime dataInizio, LocalDateTime dataFine, List<Long>idStabilimentiList);

    @Query("select new com.mondorevive.TRESPOT.responses.ChartDataResponse(function('TO_CHAR',sc.timestampOperazione,'yyyy, mm'), " +
            "SUM(CASE WHEN sc.operazione.id = :idCaricoVarco OR sc.operazione.id = :idCaricoManuale THEN 1 ELSE 0 END)," +
            "SUM(CASE WHEN sc.operazione.id = :idScaricoVarco OR sc.operazione.id = :idScaricoManuale THEN 1 ELSE 0 END)) " +
            "from StoricoCauzione sc inner join sc.magazzino m " +
            "where sc.timestampOperazione >= :dataInizio and " +
            "sc.timestampOperazione <= :dataFine and " +
            "(sc.operazione.id = :idCaricoVarco OR sc.operazione.id = :idCaricoManuale OR m.stabilimento.id in :idStabilimentiList)" +
            "group by function('TO_CHAR',sc.timestampOperazione,'yyyy, mm') " +
            "order by function('TO_CHAR',sc.timestampOperazione,'yyyy, mm')")
    List<ChartDataResponse>getOperazioniChartData(LocalDateTime dataInizio, LocalDateTime dataFine, Long idCaricoVarco, Long idCaricoManuale,Long idScaricoVarco,Long idScaricoManuale,List<Long>idStabilimentiList);

    @Query("select new com.mondorevive.TRESPOT.responses.ChartDataTipologieCauzione(tc.descrizione,count(c)) " +
            "from Cauzione c inner join c.tipologiaCauzione tc " +
            "group by tc.id,tc.codice,tc.descrizione " +
            "order by count(c) desc")
    List<ChartDataTipologieCauzione> getTipologieCauzioneChartData();

    @Query("select new com.mondorevive.TRESPOT.responses.StatisticaTipologiaCauzioneCliente(m.id,m.descrizione,count(c.id)) " +
            "from Cauzione c " +
            "inner join c.magazzino m " +
            "inner join m.cliente cl " +
            "where c.tipologiaCauzione.id = :idTipologiaCauzione " +
            "group by m.id,m.descrizione " +
            "order by count(c.id) desc")
    List<StatisticaTipologiaCauzioneCliente> getStatisticaTipologiaCauzioneCliente(Long idTipologiaCauzione);

    //PER STATISTICHE TIPOLOGIA CAUZIONE
    @Query("select count(c) from Cauzione c  where c.tipologiaCauzione.id = :idTipologiaCauzione")
    Long countCauzioni(Long idTipologiaCauzione);

    @Query("select count(c) " +
            "from Cauzione c " +
            "inner join c.statoCauzione sc " +
            "where c.magazzino.stabilimento.id in :idStabilimentiList and " +
            "c.statoCauzione.id = :idLibero and " +
            "c.tipologiaCauzione.id = :idTipologiaCauzione")
    Long countCauzioniDisponibili(List<Long> idStabilimentiList, Long idTipologiaCauzione, Long idLibero);
    @Query("select count(c) from Cauzione c " +
            "where c.magazzino.stabilimento.id not in :idStabilimentiList and " +
            "c.tipologiaCauzione.id = :idTipologiaCauzione")
    Long countCauzioniFuori(List<Long> idStabilimentiList, Long idTipologiaCauzione);

    @Query("select count(c) from Cauzione c " +
            "where c.magazzino.stabilimento.id in :idStabilimentiList and " +
            "c.statoCauzione.id = :idStato and " +
            "c.tipologiaCauzione.id = :idTipologiaCauzione")
    Long countCauzioniByStato(List<Long> idStabilimentiList, Long idTipologiaCauzione, Long idStato);

    @Query("select new com.mondorevive.TRESPOT.responses.StatisticaTipologiaCauzioneMagazzinoInterno(m.id,m.descrizione," +
            "COUNT(c)," +
            "SUM(CASE WHEN c.statoCauzione.id = :idLibero THEN 1 ELSE 0 END)," +
            "SUM(CASE WHEN c.statoCauzione.id = :idOccupato THEN 1 ELSE 0 END)," +
            "SUM(CASE WHEN c.statoCauzione.id = :idInManutenzione THEN 1 ELSE 0 END)," +
            "SUM(CASE WHEN c.statoCauzione.id = :idInRiparazione THEN 1 ELSE 0 END)) " +
            "from Cauzione c " +
            "inner join c.magazzino m " +
            "inner join m.stabilimento s " +
            "where " +
            "c.tipologiaCauzione.id = :idTipologiaCauzione " +
            "group by m.id,m.descrizione " +
            "order by COUNT(c) DESC"
    )
    List<StatisticaTipologiaCauzioneMagazzinoInterno> getTipologieCauzioneMagazzinoInterno(Long idTipologiaCauzione,
                                                                                           Long idLibero,
                                                                                           Long idOccupato,
                                                                                           Long idInManutenzione,
                                                                                           Long idInRiparazione);
}