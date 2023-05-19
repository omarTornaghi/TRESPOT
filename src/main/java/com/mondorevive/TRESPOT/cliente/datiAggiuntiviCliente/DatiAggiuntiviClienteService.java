package com.mondorevive.TRESPOT.cliente.datiAggiuntiviCliente;

import com.mondorevive.TRESPOT.cliente.Cliente;
import com.mondorevive.TRESPOT.stabilimento.sistemaEsterno.SistemaEsterno;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class DatiAggiuntiviClienteService {
    private final DatiAggiuntiviClienteRepository datiAggiuntiviClienteRepository;

    private void salva(DatiAggiuntiviCliente datiAggiuntiviCliente){
        datiAggiuntiviClienteRepository.save(datiAggiuntiviCliente);
    }
    @Transactional(readOnly = true)
    public Optional<DatiAggiuntiviCliente> getByCodiceAndIdSistemaEsterno(String codiceCliente, Integer idSistemaEsterno) {
        return datiAggiuntiviClienteRepository.getByCodiceAndIdSistemaEsterno(codiceCliente,idSistemaEsterno);
    }

    public void aggiungiDatoAggiuntivo(String codiceCliente, String ragioneSocialeCliente, Cliente cliente, SistemaEsterno sistemaEsternoUtente) {
        salva(new DatiAggiuntiviCliente(codiceCliente,ragioneSocialeCliente,cliente,sistemaEsternoUtente));
    }
}
