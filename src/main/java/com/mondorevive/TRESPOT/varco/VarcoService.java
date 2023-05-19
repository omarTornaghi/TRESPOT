package com.mondorevive.TRESPOT.varco;

import com.mondorevive.TRESPOT.magazzino.MagazzinoService;
import com.mondorevive.TRESPOT.requests.NuovoVarcoRequest;
import com.mondorevive.TRESPOT.requests.UpdateVarcoRequest;
import com.mondorevive.TRESPOT.responses.GetAllVarchiResponse;
import com.mondorevive.TRESPOT.responses.GetVarcoDetailsResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class VarcoService {
    private final VarcoRepository varcoRepository;
    private final MagazzinoService magazzinoService;

    public void salva(Varco varco){
        varcoRepository.save(varco);
    }
    @Transactional(readOnly = true)
    public Varco getVarcoById(Long id){
        return varcoRepository.getVarcoById(id).orElseThrow(EntityNotFoundException::new);
    }
    @Transactional(readOnly = true)
    public List<GetAllVarchiResponse> getAll() {
        return varcoRepository.findAllVarchi();
    }
    @Transactional(readOnly = true)
    public GetVarcoDetailsResponse getDetailsById(Long id) {
        Varco varcoById = getVarcoById(id);
        return new GetVarcoDetailsResponse(varcoById.getId(),varcoById.getCodice(), varcoById.getDescrizione(),varcoById.getMagazzinoCarico().getId(),varcoById.getMagazzinoScarico().getId());
    }
    public Long creaNuovoVarco(NuovoVarcoRequest request) {
        Varco nuovoVarco = new Varco(request.getCodice(),request.getDescrizione(),magazzinoService.getById(request.getIdMagazzinoCarico()),magazzinoService.getById(request.getIdMagazzinoScarico()));
        salva(nuovoVarco);
        return nuovoVarco.getId();
    }

    public void updateVarco(UpdateVarcoRequest request) {
        varcoRepository.updateVarco(request.getId(),request.getCodice(),request.getDescrizione(),request.getIdMagazzinoCarico(),request.getIdMagazzinoScarico());
    }

    public void deleteById(Long id) {
        varcoRepository.deleteById(id);
    }
}
