package com.mondorevive.TRESPOT.tipologiaCauzione;

import com.mondorevive.TRESPOT.responses.DettaglioTipologiaCauzioneResponse;
import com.mondorevive.TRESPOT.responses.GetAllTipologieCauzioneResponse;
import com.mondorevive.TRESPOT.responses.GetIndiceResponse;
import com.mondorevive.TRESPOT.responses.GetInfoPortaleResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public interface TipologiaCauzioneRepository extends JpaRepository<TipologiaCauzione, Long> {
    @Query("select new com.mondorevive.TRESPOT.responses.GetAllTipologieCauzioneResponse(t.id,t.codice,t.descrizione,t.numeroCauzioniMassimo,t.numeroKgMassimo,c.id,c.codice) " +
            "from TipologiaCauzione t inner join t.categoriaCauzione c order by t.codice")
    List<GetAllTipologieCauzioneResponse> findAllTipologieCauzione();

    @Query("select new com.mondorevive.TRESPOT.responses.DettaglioTipologiaCauzioneResponse(tc.id,tc.codice,tc.descrizione,tc.numeroCauzioniMassimo,tc.numeroKgMassimo," +
            "cc.id,tc.descrizionePortale,tc.note,tc.impG1,tc.impG2,tc.impG3,tc.impMuletto,tc.impMezzo) " +
            "from TipologiaCauzione tc inner join tc.categoriaCauzione cc where tc.id = :id")
    DettaglioTipologiaCauzioneResponse getDettaglioById(Long id);

    @Modifying @Query("update TipologiaCauzione tc set tc.codice = :codice,tc.descrizione=:descrizione," +
            "tc.numeroCauzioniMassimo=:numeroMassimoCauzioni,tc.numeroKgMassimo=:numeroKgMassimo," +
            "tc.categoriaCauzione.id=:idCategoriaCauzione, tc.descrizionePortale = :descrizionePortale," +
            "tc.note = :note, tc.impG1 = :impG1, tc.impG2 = :impG2, tc.impG3 = :impG3," +
            "tc.impMuletto = :impMuletto, tc.impMezzo = :impMezzo " +
            "where tc.id = :id")
    void updateTipologiaCauzione(Long id, String codice, String descrizione, Integer numeroMassimoCauzioni,
                                 Integer numeroKgMassimo, Long idCategoriaCauzione, String descrizionePortale,
                                 String note, Integer impG1,Integer impG2,Integer impG3,Integer impMuletto,
                                 Integer impMezzo);

    @Query("select tc from TipologiaCauzione tc order by tc.codice")
    List<TipologiaCauzione> findAllTipologieCauzioneSelect();

    @Query("select tc,cc from TipologiaCauzione tc inner join tc.categoriaCauzione cc where tc.codice like :codice")
    Optional<TipologiaCauzione> findByCodice(String codice);

    @Query("select new com.mondorevive.TRESPOT.responses.GetIndiceResponse(tc.codice,tc.descrizionePortale,tc.numeroKgMassimo) " +
            "from TipologiaCauzione tc where tc.codice not like 'ITR#000' order by tc.codice")
    List<GetIndiceResponse> getIndice();

    @Query("select new com.mondorevive.TRESPOT.responses.GetInfoPortaleResponse(tc.codice,tc.descrizionePortale,tc.note,tc.numeroKgMassimo,tc.numeroCauzioniMassimo,tc.impG1,tc.impG2,tc.impG3,tc.impMuletto,tc.impMezzo) " +
            "from TipologiaCauzione tc where tc.codice like :codice")
    GetInfoPortaleResponse getInfoPortale(String codice);
}