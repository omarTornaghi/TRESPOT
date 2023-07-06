package com.mondorevive.TRESPOT.cauzione.storicoCauzione;

import com.mondorevive.TRESPOT.bobina.Bobina;
import com.mondorevive.TRESPOT.bobina.BobinaService;
import com.mondorevive.TRESPOT.bobina.bobinaStoricoCauzione.BobinaStoricoCauzione;
import com.mondorevive.TRESPOT.bobina.bobinaStoricoCauzione.BobinaStoricoCauzioneService;
import com.mondorevive.TRESPOT.cauzione.Cauzione;
import com.mondorevive.TRESPOT.cauzione.statoCauzione.StatoCauzione;
import com.mondorevive.TRESPOT.cauzione.statoCauzione.StatoCauzioneService;
import com.mondorevive.TRESPOT.cauzione.statoCauzione.TipoStatoCauzione;
import com.mondorevive.TRESPOT.cauzione.storicoCauzione.operazione.OperazioneService;
import com.mondorevive.TRESPOT.cauzione.storicoCauzione.operazione.TipoOperazione;
import com.mondorevive.TRESPOT.magazzino.Magazzino;
import com.mondorevive.TRESPOT.pianoRevisione.revisione.Revisione;
import com.mondorevive.TRESPOT.pianoRevisione.revisione.RevisioneService;
import com.mondorevive.TRESPOT.pojo.DataInizioDataFine;
import com.mondorevive.TRESPOT.pojo.UltimoCaricoCauzione;
import com.mondorevive.TRESPOT.pojo.UltimoStorico;
import com.mondorevive.TRESPOT.utente.Utente;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class StoricoCauzioneService {
    private final StoricoCauzioneRepository storicoCauzioneRepository;
    private final BobinaStoricoCauzioneService bobinaStoricoCauzioneService;
    private final OperazioneService operazioneService;
    private final RevisioneService revisioneService;
    private final BobinaService bobinaService;
    private final StatoCauzioneService statoCauzioneService;
    private void salva(StoricoCauzione storicoCauzione){storicoCauzioneRepository.save(storicoCauzione); }

    public void aggiungiStorico(Cauzione cauzione, StatoCauzione statoCauzione, Magazzino magazzino, Utente utente, TipoOperazione tipoOperazione, Revisione revisione){
        //Creo lo storico e ci associo le bobine che ho ora?
        //Lo storico deve avere lo stato in cui è andato il trespolo
        StoricoCauzione nuovoStorico = new StoricoCauzione(cauzione, statoCauzione,magazzino, utente,
                operazioneService.getByTipo(tipoOperazione), revisione);
        salva(nuovoStorico);
        //Ottengo le bobine associate alla cauzione
        List<Bobina> bobineAssociate = bobinaService.getBobineAssociate(cauzione.getId());
        //Per ognuna di queste le associo al nuovo storico con entità BobinaStorico
        bobineAssociate.forEach(x -> bobinaStoricoCauzioneService.salvaBobinaStorico(x,nuovoStorico));
    }

    @Transactional(readOnly = true)
    public List<StoricoCauzione> getStoricoByIdCauzioneDataInizioDataFine(Long id, DataInizioDataFine dataInizioDataFine) {
        return storicoCauzioneRepository.getStoricoByIdCauzioneDataInizioDataFine(id,dataInizioDataFine.getDataInizio(), dataInizioDataFine.getDataFine());
    }

    @Transactional(readOnly = true)
    public List<BobinaStoricoCauzione> getBobinaStoricoCauzioneByIdStoricoCauzioneList(List<Long> IdStoricoCauzioneList) {
        return bobinaStoricoCauzioneService.getBobinaStoricoCauzioneByIdStoricoCauzioneList(IdStoricoCauzioneList);
    }

    @Transactional(readOnly = true)
    public Optional<Revisione> getUltimaRevisione(Long idCauzione) {
        return revisioneService.getUltimaRevisione(idCauzione);
    }

    @Transactional(readOnly = true)
    public List<UltimoCaricoCauzione> getUltimiCarichi(List<Long> idCauzioniList) {
        return storicoCauzioneRepository.getUltimiCarichi(idCauzioniList);
    }

    public void importaOperazione(StoricoCauzione storicoCauzione) {
        salva(storicoCauzione);
    }

    public void deleteByIdCauzione(Long id) {
        revisioneService.deleteByIdCauzione(id);
        bobinaStoricoCauzioneService.deleteByIdCauzione(id);
        storicoCauzioneRepository.deleteByIdCauzione(id);
    }

    public void deleteById(Long id) {
        bobinaStoricoCauzioneService.deleteByIdStorico(id);
        storicoCauzioneRepository.deleteById(id);
    }

    public void updateStatoStoricoByRevisioneId(Long idRevisione, Long idStato) {
        storicoCauzioneRepository.updateStatoStoricoByRevisioneId(idRevisione,idStato);
    }

    @Transactional(readOnly = true)
    public List<UltimoStorico> getUltimiStorici() {
        return storicoCauzioneRepository.getUltimiStorici(statoCauzioneService.getByTipo(TipoStatoCauzione.NON_ATTIVA).getId());
    }
}
