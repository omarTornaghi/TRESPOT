package com.mondorevive.TRESPOT.tipologiaCauzione.fileAllegati;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TipologiaCauzioneFileAllegatoRepository extends JpaRepository<TipologiaCauzioneFileAllegato, Long> {
    @Modifying @Query("delete from TipologiaCauzioneFileAllegato t where t.isCopertina = true and t.tipologiaCauzione.id = :idTipologiaCauzione")
    void deleteCopertina(Long idTipologiaCauzione);
    @Modifying @Query("delete from TipologiaCauzioneFileAllegato t where t.id = :id")
    void deleteFileById(Long id);

    @Modifying @Query("delete from TipologiaCauzioneFileAllegato t where t.tipologiaCauzione.id = :idTipologiaCauzione")
    void deleteAllByIdTipologiaCauzione(Long idTipologiaCauzione);

    @Query("select t from TipologiaCauzioneFileAllegato t where t.tipologiaCauzione.id = :idTipologiaCauzione")
    List<TipologiaCauzioneFileAllegato> getAllFilesByIdTipologiaCauzione(Long idTipologiaCauzione);

    @Query("select t from TipologiaCauzioneFileAllegato t inner join t.tipologiaCauzione tc where tc.codice like :codice and t.isCopertina = TRUE")
    List<TipologiaCauzioneFileAllegato> getCopertineByCodice(String codice);
    @Query("select t from TipologiaCauzioneFileAllegato t inner join t.tipologiaCauzione tc where tc.codice = :codice")
    List<TipologiaCauzioneFileAllegato> getAllFilesByCodice(String codice);
}