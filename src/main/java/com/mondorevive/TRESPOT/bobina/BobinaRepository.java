package com.mondorevive.TRESPOT.bobina;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BobinaRepository extends JpaRepository<Bobina, Long> {
    @Query("select b,c from Bobina b inner join b.cliente c where b.cauzione.id = :id")
    List<Bobina> getBobineAssociate(Long id);

    @Modifying @Query("update Bobina b set b.cauzione.id = null where b.cauzione.id = :id")
    void rimuoviBobineAssociateByIdCauzione(Long id);

    @Query("select b,c from Bobina b inner join b.cliente c where b.codice like :text")
    Optional<Bobina> getBobinaByText(String text);

    @Modifying @Query("update Bobina b set b.cauzione.id = :id where b.id in :idBobineList")
    void associaBobineACauzione(List<Long> idBobineList, Long id);

    @Query("select b,ca,cl from Bobina b inner join b.cauzione ca inner join b.cliente cl where b.id = :id")
    Optional<Bobina> findBobinaById(Long id);
}