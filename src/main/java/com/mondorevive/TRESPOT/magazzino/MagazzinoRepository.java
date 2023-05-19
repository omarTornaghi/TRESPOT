package com.mondorevive.TRESPOT.magazzino;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MagazzinoRepository extends JpaRepository<Magazzino, Long> {
    @Query("select m,s from Magazzino m inner join m.stabilimento s order by m.descrizione")
    List<Magazzino> getMagazziniInterni();

    @Query("select m,c from Magazzino m inner join m.cliente c order by m.descrizione")
    List<Magazzino> getMagazziniClienti();

    @Modifying @Query("update Magazzino set descrizione = :descrizione, cliente.id = :idCliente, stabilimento.id = :idStabilimento " +
            "where id = :id")
    void updateMagazzino(Long id, String descrizione, Long idCliente, Long idStabilimento);
    @Query("select m,s,c from Magazzino m left join m.stabilimento s left join m.cliente c where m.id = :id")
    Magazzino getDettaglioById(Long id);

    @Query("select m,s,c from Magazzino m left join m.stabilimento s left join m.cliente c order by m.descrizione")
    List<Magazzino> getAll();

    @Query("select m from Magazzino m " +
            "inner join m.cliente c " +
            "where exists (select b from Bobina b where b.id = :idBobina and b.cliente.id = c.id)")
    Magazzino getByIdBobina(Long idBobina);

    @Query("select m from Magazzino m where m.descrizione = :magazzino")
    Magazzino getByDescrizione(String magazzino);

    @Query("select m,c,s from Magazzino m left join m.cliente c left join m.stabilimento s " +
            "where m.id = :id")
    Optional<Magazzino> getMagazzinoById(Long id);
}