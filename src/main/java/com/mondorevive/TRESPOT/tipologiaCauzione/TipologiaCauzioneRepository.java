package com.mondorevive.TRESPOT.tipologiaCauzione;

import com.mondorevive.TRESPOT.responses.DettaglioTipologiaCauzioneResponse;
import com.mondorevive.TRESPOT.responses.GetAllTipologieCauzioneResponse;
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

    @Query("select new com.mondorevive.TRESPOT.responses.DettaglioTipologiaCauzioneResponse(tc.id,tc.codice,tc.descrizione,tc.numeroCauzioniMassimo,tc.numeroKgMassimo,cc.id) " +
            "from TipologiaCauzione tc inner join tc.categoriaCauzione cc where tc.id = :id")
    DettaglioTipologiaCauzioneResponse getDettaglioById(Long id);

    @Modifying @Query("update TipologiaCauzione tc set tc.codice = :codice,tc.descrizione=:descrizione,tc.numeroCauzioniMassimo=:numeroMassimoCauzioni,tc.numeroKgMassimo=:numeroKgMassimo,tc.categoriaCauzione.id=:idCategoriaCauzione where tc.id = :id")
    void updateTipologiaCauzione(Long id, String codice, String descrizione, Integer numeroMassimoCauzioni, Integer numeroKgMassimo, Long idCategoriaCauzione);

    @Query("select tc from TipologiaCauzione tc order by tc.codice")
    List<TipologiaCauzione> findAllTipologieCauzioneSelect();

    @Query("select tc,cc from TipologiaCauzione tc inner join tc.categoriaCauzione cc where tc.codice like :codice")
    Optional<TipologiaCauzione> findByCodice(String codice);
}