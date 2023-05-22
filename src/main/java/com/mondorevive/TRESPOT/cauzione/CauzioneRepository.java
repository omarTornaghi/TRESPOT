package com.mondorevive.TRESPOT.cauzione;

import com.mondorevive.TRESPOT.bobina.Bobina;
import com.mondorevive.TRESPOT.pojo.UltimoCaricoCauzione;
import com.mondorevive.TRESPOT.responses.ChartDataResponse;
import com.mondorevive.TRESPOT.responses.GetAllCauzioniResponse;
import com.mondorevive.TRESPOT.responses.SezioneDatiCauzioneResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public interface CauzioneRepository extends JpaRepository<Cauzione, Long> {
    @Query("select new com.mondorevive.TRESPOT.responses.GetAllCauzioniResponse(c.id,c.epcTag,c.matricola,c.timestampAcquisto,t.id,t.codice,m.id,m.descrizione,s.id,s.codice) " +
            "from Cauzione c inner join c.magazzino m " +
            "inner join c.statoCauzione s " +
            "inner join c.tipologiaCauzione t")
    List<GetAllCauzioniResponse> getAllCauzioni(Pageable pageable);

    @Query("select c,tc,sc,m from Cauzione c inner join c.tipologiaCauzione tc inner join c.statoCauzione sc inner join c.magazzino m where c.id = :id")
    Optional<Cauzione> getCauzioneById(Long id);

    @Modifying @Query("update Cauzione c set c.epcTag = :epcTag,c.matricola = :matricola, c.timestampAcquisto = :timestampAcquisto, " +
            "c.tipologiaCauzione.id = :idTipologiaCauzione, c.magazzino.id = :idMagazzino, c.statoCauzione.id = :idStatoCauzione where " +
            "c.id = :id")
    void updateCauzione(Long id, String epcTag, String matricola, LocalDateTime timestampAcquisto, Long idTipologiaCauzione, Long idMagazzino, Long idStatoCauzione);

    @Modifying @Query("update Cauzione c set c.magazzino.id = :idMagazzino,c.statoCauzione.id = :idStato where c.id = :idCauzione")
    void updateCauzione(Long idCauzione, Long idMagazzino, Long idStato);

    @Modifying @Query("update Cauzione c set c.magazzino.id = :idMagazzino,c.tipologiaCauzione.id = :idTipologiaCauzione where c.epcTag = :epcTag")
    void updateCauzione(String epcTag, Long idTipologiaCauzione, Long idMagazzino);

    @Modifying @Query("update Cauzione c set c.epcTag = :epcTag, c.magazzino.id = :idMagazzino where c.matricola like :matricola")
    void updateCauzione(String matricola,String epcTag,Long idMagazzino);
    @Query("select c,tc from Cauzione c inner join c.tipologiaCauzione tc where c.magazzino.id = :idMagazzino")
    List<Cauzione> getCauzioniByIdMagazzino(Long idMagazzino);

    @Query("select c,sc,m,tc,cc,pr " +
            "from Cauzione c " +
            "inner join c.statoCauzione sc " +
            "inner join c.magazzino m " +
            "inner join c.tipologiaCauzione tc " +
            "inner join tc.categoriaCauzione cc " +
            "left join cc.pianoRevisione pr " +
            "where c.epcTag like :epcTag")
    Optional<Cauzione> getSezioneDatiCauzioneByEpcTag(String epcTag);

    @Query("select c,sc,m,tc,cc,pr " +
            "from Cauzione c " +
            "inner join c.statoCauzione sc " +
            "inner join c.magazzino m " +
            "inner join c.tipologiaCauzione tc " +
            "inner join tc.categoriaCauzione cc " +
            "left join cc.pianoRevisione pr " +
            "where c.matricola like :matricola")
    Optional<Cauzione> getSezioneDatiCauzioneByMatricola(String matricola);

    @Modifying @Query("update Cauzione c set c.statoCauzione.id = :idStato, c.magazzino.id = :idMagazzino where c.id in :idList")
    void mettiInManutenzione(List<Long> idList, Long idStato, Long idMagazzino);

    @Query("select c,sc,m from Cauzione c inner join c.statoCauzione sc inner join c.magazzino m where c.id in :idCauzioniList")
    List<Cauzione> getCauzioniListWithStatoAndMagazzinoByIdCauzioniList(List<Long> idCauzioniList);

    @Query("select c,sc,m from Cauzione c inner join c.statoCauzione sc inner join c.magazzino m " +
            "where c.epcTag in :epcTagList")
    List<Cauzione> getCauzioniListWithStatoAndMagazzinoByEpcTagList(List<String> epcTagList);
}