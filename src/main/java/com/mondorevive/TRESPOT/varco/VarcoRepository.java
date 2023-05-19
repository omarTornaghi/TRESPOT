package com.mondorevive.TRESPOT.varco;

import com.mondorevive.TRESPOT.responses.GetAllVarchiResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface VarcoRepository extends JpaRepository<Varco, Long> {
    @Query("select new com.mondorevive.TRESPOT.responses.GetAllVarchiResponse(v.id,v.codice,v.descrizione,mc.id,mc.descrizione,ms.id,ms.descrizione)" +
            "from Varco v inner join v.magazzinoCarico mc inner join v.magazzinoScarico ms")
    List<GetAllVarchiResponse> findAllVarchi();
    @Query("select v,mc,ms from Varco v inner join v.magazzinoCarico mc inner join v.magazzinoScarico ms where v.id = :id")
    Optional<Varco> getVarcoById(Long id);
    @Modifying @Query("update Varco v set v.codice = :codice, v.descrizione = :descrizione, v.magazzinoCarico.id = :idMagazzinoCarico," +
            "v.magazzinoScarico.id = :idMagazzinoScarico where v.id = :id")
    void updateVarco(Long id, String codice, String descrizione, Long idMagazzinoCarico, Long idMagazzinoScarico);
}