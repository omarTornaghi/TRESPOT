package com.mondorevive.TRESPOT.tipologiaCauzione;

import com.mondorevive.TRESPOT.categoriaCauzione.CategoriaCauzioneService;
import com.mondorevive.TRESPOT.requests.CreaNuovaTipologiaCauzioneRequest;
import com.mondorevive.TRESPOT.requests.UpdateTipologiaCauzioneRequest;
import com.mondorevive.TRESPOT.responses.DettaglioTipologiaCauzioneResponse;
import com.mondorevive.TRESPOT.responses.GetAllTipologieCauzioneResponse;
import com.mondorevive.TRESPOT.responses.standardSelect.EntitySelect;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class TipologiaCauzioneService {
    private final TipologiaCauzioneRepository tipologiaCauzioneRepository;
    private final CategoriaCauzioneService categoriaCauzioneService;

    private void salva(TipologiaCauzione tipologiaCauzione){
        tipologiaCauzioneRepository.save(tipologiaCauzione);
    }

    @Transactional(readOnly = true)
    public TipologiaCauzione getById(Long id){
        return tipologiaCauzioneRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Transactional(readOnly = true)
    public List<GetAllTipologieCauzioneResponse> getAll(){
        return tipologiaCauzioneRepository.findAllTipologieCauzione();
    }


    @Transactional(readOnly = true)
    public List<EntitySelect> getAllSelect(){
        return tipologiaCauzioneRepository.findAllTipologieCauzioneSelect().stream().map(TipologiaCauzione::convertToSelectableEntity).collect(Collectors.toList());
    }
    @Transactional(readOnly = true)
    public DettaglioTipologiaCauzioneResponse getDettaglioById(Long id){
        return tipologiaCauzioneRepository.getDettaglioById(id);
    }
    public Long creaNuovaTipologiaCauzione(CreaNuovaTipologiaCauzioneRequest request){
        TipologiaCauzione nuova = new TipologiaCauzione(request.getCodice(),request.getDescrizione(),request.getNumeroCauzioniMassimo(),request.getNumeroKgMassimo(),categoriaCauzioneService.getById(request.getIdCategoriaCauzione()));
        salva(nuova);
        return nuova.getId();
    }
    public void updateTipologiaCauzione(UpdateTipologiaCauzioneRequest request){
        tipologiaCauzioneRepository.updateTipologiaCauzione(request.getId(),request.getCodice(),request.getDescrizione(),request.getNumeroCauzioniMassimo(),request.getNumeroKgMassimo(),request.getIdCategoriaCauzione());
    }
    public void deleteTipologiaCauzione(Long id){
        tipologiaCauzioneRepository.deleteById(id);
    }

    public TipologiaCauzione importa(TipologiaCauzione trespolo) {
        salva(trespolo);
        return trespolo;
    }

    @Transactional(readOnly = true)
    public TipologiaCauzione getByCodice(String codice) {
        return tipologiaCauzioneRepository.findByCodice(codice).orElseThrow(() -> new EntityNotFoundException("Tipologia cauzione -" + codice + "- non trovata"));
    }
    @Transactional(readOnly = true)
    public List<String> getTypeList() {
        return tipologiaCauzioneRepository.findAll().stream().map(TipologiaCauzione::getCodice).sorted().collect(Collectors.toList());
    }
}
