package com.mondorevive.TRESPOT.tipologiaCauzione.fileAllegati;

import com.mondorevive.TRESPOT.tipologiaCauzione.TipologiaCauzione;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class TipologiaCauzioneFileAllegatoService {
    private final TipologiaCauzioneFileAllegatoRepository tipologiaCauzioneFileAllegatoRepository;

    private void salva(TipologiaCauzioneFileAllegato tipologiaCauzioneFileAllegato){
        tipologiaCauzioneFileAllegatoRepository.save(tipologiaCauzioneFileAllegato);
    }
    @Transactional(readOnly = true)
    public TipologiaCauzioneFileAllegato getById(Long id){
        return tipologiaCauzioneFileAllegatoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("File con id " + id + " non trovato"));
    }

    @Transactional(readOnly = true)
    public List<TipologiaCauzioneFileAllegato> getAllFiles(Long idTipologiaCauzione) {
        return tipologiaCauzioneFileAllegatoRepository.getAllFilesByIdTipologiaCauzione(idTipologiaCauzione);
    }

    public void aggiornaCopertina(TipologiaCauzione tipologiaCauzione, MultipartFile fileCopertina) throws IOException {
        //Elimino copertina attuale
        tipologiaCauzioneFileAllegatoRepository.deleteCopertina(tipologiaCauzione.getId());
        //Creo nuovo allegato e salvo
        TipologiaCauzioneFileAllegato fileAllegato = new TipologiaCauzioneFileAllegato(tipologiaCauzione,fileCopertina,true);
        salva(fileAllegato);
    }

    public void aggiungiFileAllegato(TipologiaCauzione tipologiaCauzione, MultipartFile multipartFile) throws IOException {
        TipologiaCauzioneFileAllegato fileAllegato = new TipologiaCauzioneFileAllegato(tipologiaCauzione,multipartFile,false);
        salva(fileAllegato);
    }

    public void deleteAllByTipologiaCauzioneId(Long idTipologiaCauzione){
        tipologiaCauzioneFileAllegatoRepository.deleteAllByIdTipologiaCauzione(idTipologiaCauzione);
    }

    public void deleteById(Long id) {
        if(id == null)return;
        tipologiaCauzioneFileAllegatoRepository.deleteFileById(id);
    }

    @Transactional(readOnly = true)
    public Optional<TipologiaCauzioneFileAllegato> getCopertinaByCodice(String codice) {
        List<TipologiaCauzioneFileAllegato>copertine = tipologiaCauzioneFileAllegatoRepository.getCopertineByCodice(codice);
        if(copertine.isEmpty()) return Optional.empty();
        return Optional.of(copertine.get(0));
    }

    public List<TipologiaCauzioneFileAllegato> getAllFiles(String codice) {
        return tipologiaCauzioneFileAllegatoRepository.getAllFilesByCodice(codice);
    }
}
