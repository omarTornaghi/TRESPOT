package com.mondorevive.TRESPOT.categoriaCauzione;

import com.mondorevive.TRESPOT.pianoRevisione.PianoRevisione;
import com.mondorevive.TRESPOT.pianoRevisione.PianoRevisioneService;
import com.mondorevive.TRESPOT.requests.CreaNuovaCategoriaCauzioneRequest;
import com.mondorevive.TRESPOT.requests.UpdateCategoriaCauzioneRequest;
import com.mondorevive.TRESPOT.responses.DettaglioCategoriaCauzioneResponse;
import com.mondorevive.TRESPOT.responses.GetAllCategorieCauzioneResponse;
import com.mondorevive.TRESPOT.responses.standardSelect.EntitySelect;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoriaCauzioneService {
    private final CategoriaCauzioneRepository categoriaCauzioneRepository;
    private final PianoRevisioneService pianoRevisioneService;

    public void salva(CategoriaCauzione categoriaCauzione) {
        categoriaCauzioneRepository.save(categoriaCauzione);
    }

    @Transactional(readOnly = true)
    public CategoriaCauzione getById(Long id){
        return categoriaCauzioneRepository.findCategoriaCauzioneById(id);
    }
    @Transactional(readOnly = true)
    public List<GetAllCategorieCauzioneResponse> getAll() {
        return categoriaCauzioneRepository.findAllCategorieCauzione();
    }
    @Transactional(readOnly = true)
    public List<EntitySelect> getAllSelect(){
        return categoriaCauzioneRepository.findAll().stream().map(CategoriaCauzione::convertToSelectableEntity).collect(Collectors.toList());
    }
    @Transactional(readOnly = true)
    public DettaglioCategoriaCauzioneResponse getDettaglioById(Long id){
        CategoriaCauzione categoriaCauzione = getById(id);
        PianoRevisione pianoRevisione = categoriaCauzione.getPianoRevisione();
        return new DettaglioCategoriaCauzioneResponse(categoriaCauzione.getId(), categoriaCauzione.getCodice(), categoriaCauzione.getDescrizione(), pianoRevisione != null ? pianoRevisione.getId() : null);
    }
    public Long creaNuovaCategoriaCauzione(CreaNuovaCategoriaCauzioneRequest request){
        PianoRevisione pianoRevisione = request.getIdPianoRevisione() != null ? pianoRevisioneService.getById(request.getIdPianoRevisione()) : null;
        CategoriaCauzione categoriaCauzione = new CategoriaCauzione(request.getCodice(),request.getDescrizione(),pianoRevisione);
        salva(categoriaCauzione);
        return categoriaCauzione.getId();
    }
    public void updateCategoriaCauzione(UpdateCategoriaCauzioneRequest request){
        categoriaCauzioneRepository.updateCategoriaCauzione(request.getId(),request.getCodice(),request.getDescrizione(),request.getIdPianoRevisione());
    }
    public void deleteCategoriaCauzione(Long id){
        categoriaCauzioneRepository.deleteById(id);
    }

    public void importa(CategoriaCauzione categoriaCauzione) {
        categoriaCauzioneRepository.save(categoriaCauzione);
    }

    @Transactional(readOnly = true)
    public CategoriaCauzione getByCodice(String codice) {
        return categoriaCauzioneRepository.getByCodice(codice);
    }
}
