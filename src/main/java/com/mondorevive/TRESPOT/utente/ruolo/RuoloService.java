package com.mondorevive.TRESPOT.utente.ruolo;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RuoloService {
    private final RuoloRepository ruoloRepository;

    public RuoloService(RuoloRepository ruoloRepository) {
        this.ruoloRepository = ruoloRepository;
    }

    @Transactional(readOnly = true)
    public Ruolo findByName(TipoRuolo tipoRuolo) {
        return ruoloRepository.findRuoloByCodice(tipoRuolo.name()).orElseThrow(EntityNotFoundException::new);
    }

    @Transactional(readOnly = true)
    public List<Ruolo> getAll() {
        return ruoloRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Ruolo findById(Integer idRuolo) {
        return ruoloRepository.findById(idRuolo).orElseThrow(() -> new EntityNotFoundException(idRuolo.toString()));
    }

    public void crea(TipoRuolo tipoRuolo) {
        ruoloRepository.save(new Ruolo(tipoRuolo.name()));
    }
}
