package com.mondorevive.TRESPOT.cliente.datiAggiuntiviCliente;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface DatiAggiuntiviClienteRepository extends JpaRepository<DatiAggiuntiviCliente, Long> {
    @Query("select dc,c from DatiAggiuntiviCliente dc inner join dc.cliente c where dc.codice like :codiceCliente and " +
            "dc.sistemaEsterno.id = :idSistemaEsterno")
    Optional<DatiAggiuntiviCliente> getByCodiceAndIdSistemaEsterno(String codiceCliente, Integer idSistemaEsterno);
}