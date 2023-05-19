package com.mondorevive.TRESPOT.pianoRevisione.frequenzaRevisione;

import com.mondorevive.TRESPOT.pianoRevisione.PianoRevisione;
import com.mondorevive.TRESPOT.requests.CreaFrequenzeRevisioneRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class FrequenzaRevisioneService {
    private final FrequenzaRevisioneRepository frequenzaRevisioneRepository;

    private void salva(FrequenzaRevisione frequenzaRevisione){frequenzaRevisioneRepository.save(frequenzaRevisione); }

    @Transactional(readOnly = true)
    public List<FrequenzaRevisione> getFrequenzeRevisioneByIdPianoRevisione(Long idPianoRevisione){
        return frequenzaRevisioneRepository.findByIdPianoRevisione(idPianoRevisione);
    }

    public void salvaFrequenzeRevisione(List<CreaFrequenzeRevisioneRequest>frequenzeList, PianoRevisione pianoRevisione){
        frequenzeList.forEach(x -> salva(new FrequenzaRevisione(x.getDa(),x.getA(),x.getFrequenza(),pianoRevisione)));
    }

    public void deleteByIdPianoRevisione(PianoRevisione pianoRevisione){
        frequenzaRevisioneRepository.deleteByIdPianoRevisione(pianoRevisione.getId());
    }
}
