package com.mondorevive.TRESPOT.cliente;

import com.mondorevive.TRESPOT.cliente.datiAggiuntiviCliente.DatiAggiuntiviCliente;
import com.mondorevive.TRESPOT.cliente.datiAggiuntiviCliente.DatiAggiuntiviClienteService;
import com.mondorevive.TRESPOT.magazzino.MagazzinoService;
import com.mondorevive.TRESPOT.responses.standardSelect.EntitySelect;
import com.mondorevive.TRESPOT.stabilimento.sistemaEsterno.SistemaEsterno;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class ClienteService {
    private final ClienteRepository clienteRepository;
    private final DatiAggiuntiviClienteService datiAggiuntiviClienteService;
    private final MagazzinoService magazzinoService;

    public ClienteService(ClienteRepository clienteRepository,
                          DatiAggiuntiviClienteService datiAggiuntiviClienteService,
                          @Lazy @NotNull MagazzinoService magazzinoService) {
        this.clienteRepository = clienteRepository;
        this.datiAggiuntiviClienteService = datiAggiuntiviClienteService;
        this.magazzinoService = magazzinoService;
    }

    private void salva(Cliente cliente){
        clienteRepository.save(cliente);
    }

    @Transactional(readOnly = true)
    public Cliente getById(Long idCliente) {
        return clienteRepository.findById(idCliente).orElseThrow(EntityNotFoundException::new);
    }
    @Transactional(readOnly = true)
    public List<EntitySelect> getClientiSelect() {
        return clienteRepository.findAll().stream().map(Cliente::convertToSelectableEntity).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Optional<Cliente> getClienteByCodiceIdSistemaEsterno(String codiceCliente, Integer idSistemaEsterno) {
        Optional<DatiAggiuntiviCliente> datiAggiuntiviCliente = datiAggiuntiviClienteService.getByCodiceAndIdSistemaEsterno(codiceCliente,idSistemaEsterno);
        return datiAggiuntiviCliente.map(DatiAggiuntiviCliente::getCliente);
    }

    public Cliente creaCliente(String codiceClienteSE, String ragioneSocialeCliente, SistemaEsterno sistemaEsternoUtente) {
        String codiceCliente = codiceClienteSE + " - " + ragioneSocialeCliente;
        Cliente cliente = new Cliente(codiceCliente, ragioneSocialeCliente);
        salva(cliente);
        datiAggiuntiviClienteService.aggiungiDatoAggiuntivo(codiceClienteSE,ragioneSocialeCliente,cliente,sistemaEsternoUtente);
        //Creo il suo magazzino
        magazzinoService.creaMagazzino(codiceCliente,cliente);
        return cliente;
    }
}
