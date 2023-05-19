package com.mondorevive.TRESPOT.stabilimento;

import com.mondorevive.TRESPOT.cliente.Cliente;
import com.mondorevive.TRESPOT.requests.CreaNuovoStabilimentoRequest;
import com.mondorevive.TRESPOT.requests.UpdateStabilimentoRequest;
import com.mondorevive.TRESPOT.responses.standardSelect.EntitySelect;
import com.mondorevive.TRESPOT.stabilimento.sistemaEsterno.SistemaEsternoService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class StabilimentoService {
    private final StabilimentoRepository stabilimentoRepository;
    private final SistemaEsternoService sistemaEsternoService;

    private void salva(Stabilimento stabilimento){
        stabilimentoRepository.save(stabilimento);
    }

    @Transactional(readOnly = true)
    public boolean existsStabilimentoByCodice(String codice){
        return stabilimentoRepository.findByByCodice(codice).isPresent();
    }

    @Transactional(readOnly = true)
    public Stabilimento getById(Long id){
        return stabilimentoRepository.getStabilimentoById(id).orElseThrow(() -> new EntityNotFoundException("Stabilimento con id " + id + " non trovato"));
    }

    @Transactional(readOnly = true)
    public List<Stabilimento> getAll(){
        return stabilimentoRepository.findAllStabilimenti();
    }

    @Transactional(readOnly = true)
    public List<EntitySelect> getAllSelectable(){
        return getAll().stream().map(Stabilimento::convertToSelectableEntity).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<EntitySelect> getAllSistemiEsterni() {
        return sistemaEsternoService.getAll();
    }

    public Long creaNuovoStabilimento(CreaNuovoStabilimentoRequest request){
        Stabilimento nuovoStabilimento = new Stabilimento();
        nuovoStabilimento.setCodice(request.getCodice());
        nuovoStabilimento.setDescrizione(request.getDescrizione());
        nuovoStabilimento.setSistemaEsterno(request.getIdSistemaEsterno() == null ? null : sistemaEsternoService.getById(request.getIdSistemaEsterno()));
        salva(nuovoStabilimento);
        return nuovoStabilimento.getId();
    }

    public void updateStabilimento(UpdateStabilimentoRequest request){
        stabilimentoRepository.updateStabilimento(request.getId(),request.getCodice(),request.getDescrizione(),request.getIdSistemaEsterno());
    }

    public void deleteStabilimento(Long id){
        stabilimentoRepository.deleteById(id);
    }

    public void importaStabilimento(Stabilimento stabilimento) {
        salva(stabilimento);
    }

    @Transactional(readOnly = true)
    public Stabilimento getByCodice(String codiceStabilimento) {
        return stabilimentoRepository.getByCodice(codiceStabilimento);
    }
}
