package com.mondorevive.TRESPOT.pianoRevisione;

import com.mondorevive.TRESPOT.categoriaCauzione.CategoriaCauzione;
import com.mondorevive.TRESPOT.cauzione.Cauzione;
import com.mondorevive.TRESPOT.pianoRevisione.frequenzaRevisione.FrequenzaRevisione;
import com.mondorevive.TRESPOT.pianoRevisione.frequenzaRevisione.FrequenzaRevisioneService;
import com.mondorevive.TRESPOT.pianoRevisione.revisione.Revisione;
import com.mondorevive.TRESPOT.requests.CreaNuovoPianoRevisioneRequest;
import com.mondorevive.TRESPOT.requests.UpdatePianoRevisioneRequest;
import com.mondorevive.TRESPOT.responses.DettaglioFrequenzaRevisioneResponse;
import com.mondorevive.TRESPOT.responses.DettaglioPianoRevisioneResponse;
import com.mondorevive.TRESPOT.responses.standardSelect.EntitySelect;
import com.mondorevive.TRESPOT.utils.DateUtils;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class PianoRevisioneService {
    private final PianoRevisioneRepository pianoRevisioneRepository;
    private final FrequenzaRevisioneService frequenzaRevisioneService;

    private void salva(PianoRevisione pianoRevisione){
        pianoRevisioneRepository.save(pianoRevisione);
    }

    public PianoRevisione getById(Long id){
        return pianoRevisioneRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Transactional(readOnly = true)
    public List<PianoRevisione> getAll() {
        return pianoRevisioneRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<EntitySelect> getAllSelect() {
        return getAll().stream().map(PianoRevisione::convertToSelectableEntity).collect(Collectors.toList());
    }
    @Transactional(readOnly = true)
    public DettaglioPianoRevisioneResponse getDettaglioById(Long id) {
        PianoRevisione pianoRevisione = getById(id);
        List<FrequenzaRevisione> frequenzeRevisioneList =
                frequenzaRevisioneService.getFrequenzeRevisioneByIdPianoRevisione(id);
        return new DettaglioPianoRevisioneResponse(pianoRevisione.getId(),pianoRevisione.getCodice(),pianoRevisione.getCodice(),
                frequenzeRevisioneList.stream().map(x -> new DettaglioFrequenzaRevisioneResponse(x.getId(),x.getDaAnni(),x.getAAnni(),x.getFrequenza())).collect(Collectors.toList()));
    }

    public Long salvaNuovoPianoRevisione(CreaNuovoPianoRevisioneRequest request) {
        PianoRevisione nuovo = new PianoRevisione(request.getCodice(),request.getDescrizione());
        salva(nuovo);
        frequenzaRevisioneService.salvaFrequenzeRevisione(request.getFrequenzeList(),nuovo);
        return nuovo.getId();
    }

    public void updatePianoRevisione(UpdatePianoRevisioneRequest request) {
        pianoRevisioneRepository.updatePianoRevisione(request.getId(),request.getCodice(),request.getDescrizione());
        PianoRevisione pianoRevisione = getById(request.getId());
        frequenzaRevisioneService.deleteByIdPianoRevisione(pianoRevisione);
        frequenzaRevisioneService.salvaFrequenzeRevisione(request.getFrequenzeList(),pianoRevisione);
    }

    public void deleteById(Long id) {
        frequenzaRevisioneService.deleteByIdPianoRevisione(getById(id));
        pianoRevisioneRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Boolean isCauzioneDaManutenere(CategoriaCauzione categoriaCauzione, LocalDateTime dataAcquisto, Optional<Revisione> ultimaRevisioneOptional) {
        LocalDateTime ultimaDataRevisione = ultimaRevisioneOptional.isPresent() ? ultimaRevisioneOptional.get().getDataRevisione() : dataAcquisto;
        List<FrequenzaRevisione> frequenzeList =
                frequenzaRevisioneService.getFrequenzeRevisioneByIdPianoRevisione(categoriaCauzione.getPianoRevisione().getId());
        LocalDateTime adesso = DateUtils.getTimestampCorrente();
        //Quanto tempo è passato dalla data di acquisto ad adesso?
        long anniDiffDataAcquisto = ChronoUnit.YEARS.between(dataAcquisto, adesso);
        //Ottengo la frequenza con la differenza in anni che ho appena calcolato
        Optional<FrequenzaRevisione> first =
                frequenzeList.stream().filter(x -> x.getDaAnni() <= anniDiffDataAcquisto && x.getAAnni() >= anniDiffDataAcquisto).findFirst();
        //Se non trovo lo scaglione ritorno true
        if(first.isEmpty()) return true;
        FrequenzaRevisione frequenzaRevisione = first.get();
        long anniDiffUltimaRevisione = ChronoUnit.YEARS.between(ultimaDataRevisione, adesso);
        //Se è passato meno è uguale in anni rispetto alla frequenza, siamo a posto, altrimenti ritorno true
        //Se devo farla ogni due anni e sono passati due anni allora setto true
        return frequenzaRevisione.getFrequenza() < anniDiffUltimaRevisione;
    }
}
