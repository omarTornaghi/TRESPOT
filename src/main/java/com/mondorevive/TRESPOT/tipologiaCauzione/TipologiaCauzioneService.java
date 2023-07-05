package com.mondorevive.TRESPOT.tipologiaCauzione;

import com.mondorevive.TRESPOT.categoriaCauzione.CategoriaCauzioneService;
import com.mondorevive.TRESPOT.requests.CreaNuovaTipologiaCauzioneRequest;
import com.mondorevive.TRESPOT.requests.TipologiaCauzioneFileRequest;
import com.mondorevive.TRESPOT.requests.UpdateTipologiaCauzioneRequest;
import com.mondorevive.TRESPOT.responses.*;
import com.mondorevive.TRESPOT.responses.standardSelect.EntitySelect;
import com.mondorevive.TRESPOT.tipologiaCauzione.fileAllegati.TipologiaCauzioneFileAllegato;
import com.mondorevive.TRESPOT.tipologiaCauzione.fileAllegati.TipologiaCauzioneFileAllegatoService;
import com.mondorevive.TRESPOT.utils.MultipartFileUtils;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class TipologiaCauzioneService {
    private final TipologiaCauzioneRepository tipologiaCauzioneRepository;
    private final CategoriaCauzioneService categoriaCauzioneService;
    private final TipologiaCauzioneFileAllegatoService tipologiaCauzioneFileAllegatoService;
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
        DettaglioTipologiaCauzioneResponse response = tipologiaCauzioneRepository.getDettaglioById(id);
        //Ottengo i file e li mappo
        List<TipologiaCauzioneFileAllegato>files = tipologiaCauzioneFileAllegatoService.getAllFiles(id);
        response.setFiles(files.stream().map(x -> new TipologiaCauzioneFileAllegatoResponse(x.getId(), x.getFileName(), x.getIsCopertina())).collect(Collectors.toList()));
        return response;
    }
    public Long creaNuovaTipologiaCauzione(CreaNuovaTipologiaCauzioneRequest request){
        TipologiaCauzione nuova = new TipologiaCauzione(request.getCodice(),request.getDescrizione(),request.getNumeroCauzioniMassimo(),request.getNumeroKgMassimo(),categoriaCauzioneService.getById(request.getIdCategoriaCauzione()));
        salva(nuova);
        return nuova.getId();
    }
    public void updateTipologiaCauzione(UpdateTipologiaCauzioneRequest request, MultipartFile[] files) throws IOException {
        tipologiaCauzioneRepository.updateTipologiaCauzione(request.getId(),request.getCodice(),
                request.getDescrizione(),request.getNumeroCauzioniMassimo(),request.getNumeroKgMassimo(),
                request.getIdCategoriaCauzione(),request.getDescrizionePortale(),request.getNote(),
                request.getImpG1(),request.getImpG2(),request.getImpG3(),request.getImpMuletto(),
                request.getImpMezzo());
        TipologiaCauzione byId = getById(request.getId());
        if(files == null)files = new MultipartFile[0];
        for(TipologiaCauzioneFileRequest fileRequest : request.getFiles()){
            if(fileRequest.getRimosso() != null && fileRequest.getRimosso()){tipologiaCauzioneFileAllegatoService.deleteById(fileRequest.getId());continue;}
            Optional<MultipartFile> fileMultiPart =
                    Arrays.stream(files).filter(x -> Objects.equals(x.getOriginalFilename(),fileRequest.getFilename())).findFirst();
            if(fileRequest.getIsCopertina()){
                if(fileMultiPart.isPresent()){
                    tipologiaCauzioneFileAllegatoService.aggiornaCopertina(byId, fileMultiPart.get());
                }
            }
            else {
                if(fileMultiPart.isPresent()){
                    tipologiaCauzioneFileAllegatoService.aggiungiFileAllegato(byId,fileMultiPart.get());
                }
            }
        }
    }
    public void deleteTipologiaCauzione(Long id){
        tipologiaCauzioneFileAllegatoService.deleteAllByTipologiaCauzioneId(id);
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
        return tipologiaCauzioneRepository.findAll().stream().map(x -> x.getCodiceTerminalino()+ "|" + x.getDescrizione()).sorted().collect(Collectors.toList());
    }

    public void deleteFileAllegato(Long id) {
        tipologiaCauzioneFileAllegatoService.deleteById(id);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<byte[]> getFileAllegato(Long id) {
        TipologiaCauzioneFileAllegato byId = tipologiaCauzioneFileAllegatoService.getById(id);
        return MultipartFileUtils.preparaFileDownload(byId.getContenuto(),byId.getFileName(),byId.getContentType());
    }

    public List<GetIndiceResponse> getIndice() {
        return tipologiaCauzioneRepository.getIndice();
    }

    public ResponseEntity<byte[]> getCopertina(String codice) {
        Optional<TipologiaCauzioneFileAllegato> byCodice = tipologiaCauzioneFileAllegatoService.getCopertinaByCodice(codice);
        if(byCodice.isEmpty()) throw new EntityNotFoundException("No copertina");
        TipologiaCauzioneFileAllegato fileAllegato = byCodice.get();
        return MultipartFileUtils.preparaFileDownload(fileAllegato.getContenuto(),fileAllegato.getFileName(),fileAllegato.getContentType());
    }

    public GetInfoPortaleResponse getInfoPortale(String codice) {
        GetInfoPortaleResponse response = tipologiaCauzioneRepository.getInfoPortale(codice);
        if(response == null) return new GetInfoPortaleResponse();
        List<TipologiaCauzioneFileAllegato>files = tipologiaCauzioneFileAllegatoService.getAllFiles(codice);
        response.setFiles(files.stream().filter(x -> !x.getIsCopertina()).map(x -> new TipologiaCauzioneFileAllegatoResponse(x.getId(), x.getFileName(), x.getIsCopertina())).collect(Collectors.toList()));
        return response;
    }
}
