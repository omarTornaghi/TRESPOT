package com.mondorevive.TRESPOT.cauzione.storicoCauzione.operazione;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class OperazioneService {
    private final OperazioneRepository operazioneRepository;

    @Transactional(readOnly = true)
    public Operazione getByTipo(TipoOperazione tipoOperazione) {
        return operazioneRepository.getByCodice(tipoOperazione.name()).orElseThrow(EntityNotFoundException::new);
    }

    private void salva(Operazione operazione){
        operazioneRepository.save(operazione);
    }
    public void crea(TipoOperazione tipoOperazione) {
        salva(new Operazione(tipoOperazione.name()));
    }
}
