package com.mondorevive.TRESPOT.categoriaCauzione;

import com.mondorevive.TRESPOT.responses.GetAllCategorieCauzioneResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoriaCauzioneRepository extends JpaRepository<CategoriaCauzione, Long> {
    @Query("select new com.mondorevive.TRESPOT.responses.GetAllCategorieCauzioneResponse(cc.id,cc.codice,cc.descrizione,pr.id,pr.codice) " +
            "from CategoriaCauzione cc left join cc.pianoRevisione pr")
    List<GetAllCategorieCauzioneResponse> findAllCategorieCauzione();

    @Query("select cc,pr from CategoriaCauzione cc left join cc.pianoRevisione pr where cc.id = :id")
    CategoriaCauzione findCategoriaCauzioneById(Long id);
    @Modifying @Query("update CategoriaCauzione cc set cc.codice = :codice, cc.descrizione = :descrizione,cc.pianoRevisione.id = :idPianoRevisione where cc.id = :id")
    void updateCategoriaCauzione(Long id, String codice, String descrizione, Long idPianoRevisione);

    @Query("select cc from CategoriaCauzione cc where cc.codice = :codice")
    CategoriaCauzione getByCodice(String codice);
}