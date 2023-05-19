package com.mondorevive.TRESPOT.stabilimento.sistemaEsterno;

import com.mondorevive.TRESPOT.responses.standardSelect.EntitySelect;
import com.mondorevive.TRESPOT.responses.standardSelect.Selectable;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class SistemaEsternoService {
    private final SistemaEsternoRepository sistemaEsternoRepository;

    @Transactional(readOnly = true)
    public List<EntitySelect> getAll() {
        return sistemaEsternoRepository.findAll().stream().map(SistemaEsterno::convertToSelectableEntity).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public SistemaEsterno getById(Integer id){
        return sistemaEsternoRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public void crea(TipoSistemaEsterno tipoSistemaEsterno) {
        sistemaEsternoRepository.save(new SistemaEsterno(tipoSistemaEsterno.name()));
    }

    @Transactional(readOnly = true)
    public SistemaEsterno getByCodice(String codiceSistemaEsterno) {
        return sistemaEsternoRepository.getByCodice(codiceSistemaEsterno);
    }
}
