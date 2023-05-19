package com.mondorevive.TRESPOT.cauzione.statoCauzione;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class StatoCauzioneService {
    private final StatoCauzioneRepository statoCauzioneRepository;

    private void salva(StatoCauzione statoCauzione){
        statoCauzioneRepository.save(statoCauzione);
    }
    public void crea(TipoStatoCauzione tipoStatoCauzione){
        salva(new StatoCauzione(tipoStatoCauzione.name()));
    }
    @Transactional(readOnly = true)
    public List<StatoCauzione> getAll(){
        return statoCauzioneRepository.findAll();
    }
    @Transactional(readOnly = true)
    public StatoCauzione getById(Long id){
        return statoCauzioneRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }
    @Transactional(readOnly = true)
    public StatoCauzione getByTipo(TipoStatoCauzione tipoStatoCauzione){
        return statoCauzioneRepository.getByCodice(tipoStatoCauzione.name());
    }
}
