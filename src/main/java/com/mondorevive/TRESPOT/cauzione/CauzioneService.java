package com.mondorevive.TRESPOT.cauzione;

import com.mondorevive.TRESPOT.bobina.Bobina;
import com.mondorevive.TRESPOT.bobina.BobinaService;
import com.mondorevive.TRESPOT.bobina.bobinaStoricoCauzione.BobinaStoricoCauzione;
import com.mondorevive.TRESPOT.cauzione.statoCauzione.StatoCauzione;
import com.mondorevive.TRESPOT.cauzione.statoCauzione.StatoCauzioneService;
import com.mondorevive.TRESPOT.cauzione.statoCauzione.TipoStatoCauzione;
import com.mondorevive.TRESPOT.cauzione.storicoCauzione.StoricoCauzione;
import com.mondorevive.TRESPOT.cauzione.storicoCauzione.StoricoCauzioneService;
import com.mondorevive.TRESPOT.cauzione.storicoCauzione.operazione.TipoOperazione;
import com.mondorevive.TRESPOT.cliente.Cliente;
import com.mondorevive.TRESPOT.cliente.ClienteService;
import com.mondorevive.TRESPOT.exceptions.SistemaEsternoNotSupportedException;
import com.mondorevive.TRESPOT.exceptions.exceptions.InvalidRequestException;
import com.mondorevive.TRESPOT.magazzino.Magazzino;
import com.mondorevive.TRESPOT.magazzino.MagazzinoService;
import com.mondorevive.TRESPOT.pagination.PaginationService;
import com.mondorevive.TRESPOT.pianoRevisione.PianoRevisioneService;
import com.mondorevive.TRESPOT.pianoRevisione.revisione.Revisione;
import com.mondorevive.TRESPOT.pojo.UltimoCaricoCauzione;
import com.mondorevive.TRESPOT.pojo.UltimoStorico;
import com.mondorevive.TRESPOT.requests.*;
import com.mondorevive.TRESPOT.requests.silvanoCattaneo.*;
import com.mondorevive.TRESPOT.responses.*;
import com.mondorevive.TRESPOT.responses.silvanoCattaneo.GetTagInfoResponse;
import com.mondorevive.TRESPOT.responses.silvanoCattaneo.ValidateResponse;
import com.mondorevive.TRESPOT.responses.standardSelect.EntitySelect;
import com.mondorevive.TRESPOT.stabilimento.sistemaEsterno.SistemaEsterno;
import com.mondorevive.TRESPOT.stabilimento.sistemaEsterno.SistemaEsternoProvider;
import com.mondorevive.TRESPOT.stabilimento.sistemaEsterno.TipoSistemaEsterno;
import com.mondorevive.TRESPOT.stabilimento.sistemaEsterno.interfaces.BobinaProvider;
import com.mondorevive.TRESPOT.stabilimento.sistemaEsterno.interfaces.DatiBobina;
import com.mondorevive.TRESPOT.tipologiaCauzione.TipologiaCauzione;
import com.mondorevive.TRESPOT.tipologiaCauzione.TipologiaCauzioneService;
import com.mondorevive.TRESPOT.utente.Utente;
import com.mondorevive.TRESPOT.utente.UtenteService;
import com.mondorevive.TRESPOT.utils.DateUtils;
import com.mondorevive.TRESPOT.varco.VarcoService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CauzioneService {
    private final CauzioneRepository cauzioneRepository;
    private final PaginationCauzioneRepository paginationCauzioneRepository;
    private final PaginationService paginationService;
    private final StatoCauzioneService statoCauzioneService;
    private final StoricoCauzioneService storicoCauzioneService;
    private final UtenteService utenteService;
    private final BobinaService bobinaService;
    private final PianoRevisioneService pianoRevisioneService;
    private final SistemaEsternoProvider sistemaEsternoProvider;
    private final ClienteService clienteService;
    private final MagazzinoService magazzinoService;
    private final TipologiaCauzioneService tipologiaCauzioneService;
    private final VarcoService varcoService;

    @Transactional(readOnly = true)
    public Cauzione getById(Long id) {
        return cauzioneRepository.getCauzioneById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Transactional(readOnly = true)
    public PageResponse<GetAllCauzioniResponse> getAll(PaginationRequest request) {
        Specification<Cauzione> specification = Specification.where(CauzioneSpecifications.epcTagContains(request.getText()))
                .or(CauzioneSpecifications.matricolaContains(request.getText()));
        for(FiltroRequest filtro : request.getFiltri()){
            switch (filtro.getColonna()) {
                case "codiceTipologiaCauzione" ->
                        specification = specification.and(Specification.where(CauzioneSpecifications.idTipologiaCauzioneEquals(filtro.getValore())));
                case "descrizioneMagazzino" ->
                        specification = specification.and(Specification.where(CauzioneSpecifications.idMagazzinoEquals(filtro.getValore())));
                case "codiceStatoCauzione" ->
                        specification = specification.and(Specification.where(CauzioneSpecifications.idStatoCauzioneEquals(filtro.getValore())));
            }
        }
        PageResponse<Cauzione> page = paginationService.getPage(specification, request, paginationCauzioneRepository);
        return new PageResponse<>(page, buildContent(page.getContent()));
    }

    private List<GetAllCauzioniResponse> buildContent(List<Cauzione> cauzioneList) {
        return cauzioneList.stream().map(x -> new GetAllCauzioniResponse(x.getId(),x.getEpcTag(),x.getMatricola(),
                x.getTimestampAcquisto(),x.getTipologiaCauzione().getId(),x.getTipologiaCauzione().getCodice(),
                x.getMagazzino().getId(),x.getMagazzino().getDescrizione(),x.getStatoCauzione().getId(),
                x.getStatoCauzione().getCodice())).collect(Collectors.toList());
    }

    public void salva(Cauzione cauzione) {
        cauzioneRepository.save(cauzione);
    }

    @Transactional(readOnly = true)
    public GetDettaglioCauzioneResponse getDettaglioCauzioneResponse(Long id){
        Optional<Revisione> ultimaRevisione = storicoCauzioneService.getUltimaRevisione(id);
        Cauzione cauzione = getById(id);
        List<Bobina> bobineAssociate = bobinaService.getBobineAssociate(id);
        return new GetDettaglioCauzioneResponse(cauzione.getId(),cauzione.getEpcTag(),cauzione.getMatricola(),cauzione.getTimestampAcquisto(),
                cauzione.getTipologiaCauzione().getId(),cauzione.getTipologiaCauzione().getCodice(), cauzione.getMagazzino().getId(),cauzione.getMagazzino().getDescrizione(),cauzione.getStatoCauzione().getId(),
                ultimaRevisione.map(Revisione::getDataRevisione).orElse(null),
                ultimaRevisione.map(revisione -> revisione.getStoricoCauzione().getUtente().getUsername()).orElse(null),
                bobineAssociate.stream().map(x -> new GetDettaglioBobineAssociateResponse(x.getId(),x.getCliente().getId(),x.getCodice(),x.getCliente().getCodice())).collect(Collectors.toList()));
    }

    @Transactional(readOnly = true)
    public List<EntitySelect> getAllStatiCauzioniSelect() {
        return statoCauzioneService.getAll().stream().map(StatoCauzione::convertToSelectableEntity).collect(Collectors.toList());
    }

    public void updateCauzione(UpdateCauzioneRequest request, String username) {
        //Potrebbe essere una funzione che prende la cauzione, prende l'operazione da inserire e lo salva
        cauzioneRepository.updateCauzione(request.getId(),request.getEpcTag(),request.getMatricola(),
                request.getTimestampAcquisto(),request.getIdTipologiaCauzione(),request.getIdMagazzino(),request.getIdStatoCauzione());
        storicoCauzioneService.aggiungiStorico(getById(request.getId()),statoCauzioneService.getById(request.getIdStatoCauzione()),magazzinoService.getById(request.getIdMagazzino()),utenteService.findUserByUsername(username),TipoOperazione.AGGIORNAMENTO_MANUALE,null);
    }

    public List<GetStoricoCauzioneResponse> getStoricoCauzione(Long id, String dataInizio, String dataFine) {
        List<StoricoCauzione>storicoCauzioneList = storicoCauzioneService.getStoricoByIdCauzioneDataInizioDataFine(id,DateUtils.getDataInizioDataFine(dataInizio,dataFine));
        List<BobinaStoricoCauzione>bobinaStoricoCauzioneList = storicoCauzioneService.getBobinaStoricoCauzioneByIdStoricoCauzioneList(storicoCauzioneList.stream().map(StoricoCauzione::getId).collect(Collectors.toList()));
        List<GetStoricoCauzioneResponse>responseList = new LinkedList<>();
        for(StoricoCauzione storicoCauzione : storicoCauzioneList){
            Utente utente = storicoCauzione.getUtente();
            Revisione revisione = storicoCauzione.getRevisione();
            GetStoricoCauzioneResponse response = new GetStoricoCauzioneResponse(storicoCauzione.getId(),storicoCauzione.getTimestampOperazione(),storicoCauzione.getOperazione().getCodice(),utente != null ? utente.getUsername() : null, storicoCauzione.getMagazzino().getDescrizione(), storicoCauzione.getStatoCauzione().getCodice(), revisione != null ? revisione.getId() : null);
            List<BobinaStoricoCauzione> bobineAssociate =
                    bobinaStoricoCauzioneList.stream().filter(x -> x.getStoricoCauzione().getId().equals(storicoCauzione.getId())).toList();
            bobineAssociate.forEach(x -> response.addCodiceBobina(x.getBobina().getCodice()));
            responseList.add(response);
        }
        return responseList;
    }

    @Transactional(readOnly = true)
    public List<Cauzione> getCauzioneListByIdMagazzino(Long idMagazzino) {
        return cauzioneRepository.getCauzioniByIdMagazzino(idMagazzino, statoCauzioneService.getByTipo(TipoStatoCauzione.NON_ATTIVA).getId());
    }

    @Transactional(readOnly = true)
    public List<UltimoCaricoCauzione> getUltimiCarichi(List<Long> idCauzioniList) {
        return storicoCauzioneService.getUltimiCarichi(idCauzioniList);
    }

    @Transactional(readOnly = true)
    public Optional<Cauzione> getSezioneDatiCauzioneByEpcTag(String epcTag){
        return cauzioneRepository.getSezioneDatiCauzioneByEpcTag(epcTag);
    }

    @Transactional(readOnly = true)
    public Optional<Cauzione> getSezioneDatiCauzioneByMatricola(String matricola){
        return cauzioneRepository.getSezioneDatiCauzioneByMatricola(matricola);
    }

    @Transactional(readOnly = true)
    public Optional<Cauzione> getSezioneDatiCauzioneByText(String text){
        if(text == null) return Optional.empty();
        Optional<Cauzione> sezioneDatiCauzioneByEpcTag = getSezioneDatiCauzioneByEpcTag(text.toUpperCase());
        if(sezioneDatiCauzioneByEpcTag.isPresent())return sezioneDatiCauzioneByEpcTag;
        return getSezioneDatiCauzioneByMatricola(text.toUpperCase());
    }

    @Transactional(readOnly = true)
    public SezioneDatiCauzioneResponse getCauzioneByText(String text){
        Optional<Cauzione> cauzioneOptional = getSezioneDatiCauzioneByText(text);
        if(cauzioneOptional.isEmpty()) return new SezioneDatiCauzioneResponse("NON_TROVATO_DA_RICERCA");
        Cauzione cauzione = cauzioneOptional.get();
        Optional<Revisione> ultimaRevisione = storicoCauzioneService.getUltimaRevisione(cauzione.getId());
        return new SezioneDatiCauzioneResponse(cauzione.getId(), cauzione.getEpcTag(),cauzione.getMatricola(),cauzione.getStatoCauzione().getCodice(),cauzione.getMagazzino().getDescrizione(),cauzione.getTipologiaCauzione().getDescrizione(), ultimaRevisione.map(Revisione::getDataRevisione).orElse(null));
    }
    public void aggiungiNuovaRevisione(Long idCauzione, Revisione revisione, String username) {
        //Creare un nuovo oggetto storico e salvare
        Cauzione cauzione = getById(idCauzione);
        Utente utente = utenteService.findUserByUsername(username);
        TipoStatoCauzione tipoStatoCauzione = revisione.getConformitaTotale() ? TipoStatoCauzione.LIBERO : TipoStatoCauzione.IN_RIPARAZIONE;
        StatoCauzione nuovoStato = statoCauzioneService.getByTipo(tipoStatoCauzione);
        Magazzino magazzino = utente.getMagazzino();
        cauzioneRepository.updateCauzione(idCauzione,magazzino.getId(),nuovoStato.getId());
        //Tolgo le eventualo bobine associate
        bobinaService.rimuoviBobineAssociateByIdCauzione(idCauzione);
        storicoCauzioneService.aggiungiStorico(cauzione,nuovoStato,magazzino,utente,TipoOperazione.REVISIONE,revisione);
    }

    public void aggiornaRevisione(Long idCauzione, Long id, Boolean conformitaTotale) {
        TipoStatoCauzione tipoStatoCauzione = conformitaTotale ? TipoStatoCauzione.LIBERO : TipoStatoCauzione.IN_RIPARAZIONE;
        StatoCauzione nuovoStato = statoCauzioneService.getByTipo(tipoStatoCauzione);
        cauzioneRepository.updateCauzione(idCauzione,nuovoStato.getId());
        storicoCauzioneService.updateStatoStoricoByRevisioneId(id,nuovoStato.getId());
    }

    public GetInfoCauzioneResponse getInfoCauzione(String text, String operazione, String username, Long idMagazzino) {
        //Come altra chiamata ma ci chiamo errore
        Optional<Cauzione> sezioneDatiCauzioneByText = getSezioneDatiCauzioneByText(text);
        if(sezioneDatiCauzioneByText.isEmpty())return new GetInfoCauzioneResponse("NON_TROVATO_DA_RICERCA");
        Cauzione cauzione = sezioneDatiCauzioneByText.get();
        Utente utente = utenteService.findUserByUsername(username);

        GetInfoCauzioneResponse response = new GetInfoCauzioneResponse(cauzione.getId(),
                cauzione.getEpcTag(),cauzione.getMatricola(),cauzione.getStatoCauzione().getCodice(),
                cauzione.getMagazzino().getDescrizione());
        //Calcolo errori guardando l'operazione che sto facendo
        switch (operazione.toUpperCase()) {
            case "METTIINMANUTENZIONE" ->
                    response.setErrore(computaErroreOperazioneMettiInManutenzione(cauzione, utente));
            case "SCARICAELIBERA", "SCARICA" ->
                //Qua ritorno come errore solo se il trespolo è da manutenere o no
                    response.setErrore(computaErroreOperazioneScarica(cauzione));
            case "SPOSTAAMAGAZZINO" ->
                    response.setErrore(computaErroreOperazioneSpostaAMagazzino(cauzione));
            case "MANDAACLIENTE" ->
                    response.setErrore(computaErroreOperazioneMandaACliente(cauzione, idMagazzino));
            default -> throw new InvalidRequestException("Operazione non supportata");
        }
        return response;
    }
    private ErroreResponse computaErroreOperazioneMettiInManutenzione(Cauzione cauzione, Utente utente) {
        switch (TipoStatoCauzione.valueOf(cauzione.getStatoCauzione().getCodice())){
            case OCCUPATO -> {
                return ErroreResponse.ErrorResponse("La cauzione è occupata, scaricala con SCARICA E LIBERA");
            }
            case IN_MANUTENZIONE -> {
                return ErroreResponse.ErrorResponse("La cauzione è già in manutenzione");
            }
            case IN_RIPARAZIONE -> {
                return ErroreResponse.ErrorResponse("La cauzione è in riparazione");
            }
            default -> {
                //Se il magazzino non è quello locale dell'utente mando WARNING
                if(!cauzione.getMagazzino().getId().equals(utente.getMagazzino().getId()))
                    return ErroreResponse.WarningResponse("La cauzione non risulta nel tuo magazzino");
            }
        }
        //Se non è niente di tutto ciò, non mando niente
        return null;
    }

    @Transactional(readOnly = true)
    public Boolean cauzioneDaManutenere(Cauzione cauzione){
        return pianoRevisioneService.isCauzioneDaManutenere(cauzione.getTipologiaCauzione().getCategoriaCauzione(), cauzione.getTimestampAcquisto(), storicoCauzioneService.getUltimaRevisione(cauzione.getId()));
    }

    private ErroreResponse computaErroreOperazioneScarica(Cauzione cauzione) {
        Boolean daManutenere = cauzioneDaManutenere(cauzione);
        if(daManutenere)return ErroreResponse.WarningResponse("La cauzione è da manutenere");
        return null;
    }
    private ErroreResponse computaErroreOperazioneSpostaAMagazzino(Cauzione cauzione) {
        switch (TipoStatoCauzione.valueOf(cauzione.getStatoCauzione().getCodice())){
            case OCCUPATO -> {
                return ErroreResponse.InfoResponse("La cauzione risulta occupata");
            }
            case IN_MANUTENZIONE -> {
                return ErroreResponse.WarningResponse("Attenzione la cauzione è in manutenzione");
            }
            case IN_RIPARAZIONE -> {
                return ErroreResponse.WarningResponse("Attenzione la cauzione è in riparazione");
            }
        }
        return null;
    }

    private ErroreResponse computaErroreOperazioneMandaACliente(Cauzione cauzione, Long idMagazzinoCliente) {
        switch (TipoStatoCauzione.valueOf(cauzione.getStatoCauzione().getCodice())){
            case LIBERO,IN_MANUTENZIONE,IN_RIPARAZIONE,NON_ATTIVA -> {return ErroreResponse.ErrorResponse("La cauzione non è associata a bobine del cliente selezionato"); }
            default -> {
                List<Bobina> bobineAssociate = bobinaService.getBobineAssociate(cauzione.getId());
                if(bobineAssociate.size() == 0) return ErroreResponse.ErrorResponse("La cauzione non ha associato nessuna bobina del cliente selezionato");
                Bobina bobina = bobineAssociate.get(0);
                if(!magazzinoService.getById(idMagazzinoCliente).getCliente().getId().equals(bobina.getCliente().getId())) return ErroreResponse.ErrorResponse("La cauzione non è associata a bobine del cliente selezionato");
            }
        }
        return null;
    }

    public void mettiInManutenzione(List<MettiInManutenzioneRequest> request, String username) {
        Utente utente = utenteService.findUserByUsername(username);
        List<MettiInManutenzioneRequest> cauzioniDaMettereInManutenzione =
                request.stream().filter(x -> x.getErrore() == null || !x.getErrore().isTipoError()).toList();
        cauzioneRepository.mettiInManutenzione(cauzioniDaMettereInManutenzione.stream().map(DatoTrespoloRequest::getId).collect(Collectors.toList()),
                statoCauzioneService.getByTipo(TipoStatoCauzione.IN_MANUTENZIONE).getId(),
                utente.getMagazzino().getId()
                );
        for(MettiInManutenzioneRequest r : cauzioniDaMettereInManutenzione){
            Cauzione cauzione = getById(r.getId());
            storicoCauzioneService.aggiungiStorico(cauzione,statoCauzioneService.getByTipo(TipoStatoCauzione.IN_MANUTENZIONE),utente.getMagazzino(),utente,TipoOperazione.MESSA_IN_MANUTENZIONE,null);
        }
    }

    public void scaricaELibera(List<ScaricaRequest> request, String username) {
        scaricaCauzioni(request,username,true);
    }
    public void scarica(List<ScaricaRequest> request, String username) {
        scaricaCauzioni(request,username,false);
    }
    private void scaricaCauzioni(List<ScaricaRequest> request, String username, Boolean libera){
        //TODO GESTIRE OPERAZIONE LIBERA IN MANIERA DIVERSA(rimuoviBobineAssociateSeNecessario è sbagliato)
        Utente utente = utenteService.findUserByUsername(username);
        List<ScaricaRequest> cauzioniDaScaricareELiberare =
                request.stream().filter(x -> x.getErrore() == null || !x.getErrore().isTipoError()).toList();
        for(ScaricaRequest cauzioneScarica : cauzioniDaScaricareELiberare){
            Cauzione cauzione = getById(cauzioneScarica.getId());
            TipoStatoCauzione tipoStatoCauzione = computaStatoCauzioneScarica(cauzione,libera);
            rimuoviBobineAssociateSeNecessario(cauzione, tipoStatoCauzione);
            cauzioneRepository.updateCauzione(cauzione.getId(),utente.getMagazzino().getId(),statoCauzioneService.getByTipo(tipoStatoCauzione).getId());
            storicoCauzioneService.aggiungiStorico(getById(cauzione.getId()),statoCauzioneService.getByTipo(tipoStatoCauzione), utente.getMagazzino(), utente,TipoOperazione.SCARICO_MANUALE,null);
        }
    }

    private void rimuoviBobineAssociateSeNecessario(Cauzione cauzione, TipoStatoCauzione tipoStatoCauzione) {
        //Se il nuovo stato non è OCCUPATO devo togliere le bobine
        if(tipoStatoCauzione.equals(TipoStatoCauzione.OCCUPATO)) return;
        bobinaService.rimuoviBobineAssociateByIdCauzione(cauzione.getId());
    }

    private TipoStatoCauzione computaStatoCauzioneScarica(Cauzione cauzione, Boolean libera) {
        if(cauzioneDaManutenere(cauzione)) return TipoStatoCauzione.IN_MANUTENZIONE;
        if(libera) return TipoStatoCauzione.LIBERO;
        if (TipoStatoCauzione.valueOf(cauzione.getStatoCauzione().getCodice()) == TipoStatoCauzione.OCCUPATO) {
            return TipoStatoCauzione.OCCUPATO;
        }
        return TipoStatoCauzione.LIBERO;
    }

    public void spostaAMagazzino(SpostamentoRequest request, String username) {
        Utente utente = utenteService.findUserByUsername(username);
        List<Cauzione>cauzioneList = getCauzioniListWithStatoAndMagazzinoByIdCauzioniList(request.getCauzioni().stream().map(CauzioniSpostamentorequest::getId).collect(Collectors.toList()));
        for(Cauzione cauzione : cauzioneList){
            TipoStatoCauzione tipoStatoCauzione = computaStatoCauzioneSpostaAMagazzino(cauzione);
            cauzioneRepository.updateCauzione(cauzione.getId(),request.getIdMagazzino(),statoCauzioneService.getByTipo(tipoStatoCauzione).getId());
            storicoCauzioneService.aggiungiStorico(getById(cauzione.getId()),statoCauzioneService.getByTipo(tipoStatoCauzione),magazzinoService.getById(request.getIdMagazzino()),utente,TipoOperazione.CARICO_MANUALE,null);
        }
    }

    public void mandaACliente(SpostamentoRequest request, String username){
        Utente utente = utenteService.findUserByUsername(username);
        List<Cauzione>cauzioneList = getCauzioniListWithStatoAndMagazzinoByIdCauzioniList(request.getCauzioni().stream().map(CauzioniSpostamentorequest::getId).collect(Collectors.toList()));
        for(Cauzione cauzione : cauzioneList){
            if(!verificaClienteBobineAssociate(cauzione, request.getIdMagazzino())) continue;
            //Qua non è necessario rimuovere eventuali bobine associate
            cauzioneRepository.updateCauzione(cauzione.getId(),request.getIdMagazzino(),cauzione.getStatoCauzione().getId());
            storicoCauzioneService.aggiungiStorico(getById(cauzione.getId()),statoCauzioneService.getById(cauzione.getStatoCauzione().getId()),magazzinoService.getById(request.getIdMagazzino()),utente,TipoOperazione.CARICO_MANUALE,null);
        }
    }

    private boolean verificaClienteBobineAssociate(Cauzione cauzione, Long idCliente) {
        switch (TipoStatoCauzione.valueOf(cauzione.getStatoCauzione().getCodice())){
            case LIBERO,IN_MANUTENZIONE,IN_RIPARAZIONE,NON_ATTIVA -> {
                return false;
            }
            default -> { //Sono OCCUPATO
                List<Bobina> bobineAssociate = bobinaService.getBobineAssociate(cauzione.getId());
                if(bobineAssociate.size() == 0) return false;
                Bobina bobina = bobineAssociate.get(0);
                if(!bobina.getCliente().getId().equals(magazzinoService.getById(idCliente).getCliente().getId())) return false;
            }
        }
        return true;
    }

    private TipoStatoCauzione computaStatoCauzioneSpostaAMagazzino(Cauzione cauzione) {
        TipoStatoCauzione tipoStatoCauzione = TipoStatoCauzione.valueOf(cauzione.getStatoCauzione().getCodice());
        if(tipoStatoCauzione.equals(TipoStatoCauzione.LIBERO) || tipoStatoCauzione.equals(TipoStatoCauzione.OCCUPATO))
            return tipoStatoCauzione;
        return TipoStatoCauzione.LIBERO;
    }
    private List<Cauzione> getCauzioniListWithStatoAndMagazzinoByIdCauzioniList(List<Long> idCauzioniList) {
        return cauzioneRepository.getCauzioniListWithStatoAndMagazzinoByIdCauzioniList(idCauzioniList);
    }

    public CauzioneWithBobineAssociateResponse getCauzioneWithBobineAssociate(String text) {
        Optional<Cauzione> sezioneDatiCauzioneByText = getSezioneDatiCauzioneByText(text);
        if(sezioneDatiCauzioneByText.isEmpty())return new CauzioneWithBobineAssociateResponse("NON_TROVATO_DA_RICERCA");
        Cauzione cauzione = sezioneDatiCauzioneByText.get();
        List<Bobina> bobineAssociate = bobinaService.getBobineAssociate(cauzione.getId());
        Optional<Revisione> ultimaRevisione = storicoCauzioneService.getUltimaRevisione(cauzione.getId());
        return new CauzioneWithBobineAssociateResponse(cauzione.getId(), cauzione.getEpcTag(),cauzione.getMatricola(),cauzione.getStatoCauzione().getId(),
                cauzione.getStatoCauzione().getCodice(),cauzione.getMagazzino().getId(),cauzione.getMagazzino().getDescrizione(),cauzione.getTipologiaCauzione().getId(),cauzione.getTipologiaCauzione().getDescrizione(),
                ultimaRevisione.map(Revisione::getDataRevisione).orElse(null),
                cauzione.getTipologiaCauzione().getNumeroCauzioniMassimo(),
                bobineAssociate.stream().map(x -> new GetDettaglioBobineAssociateResponse(x.getId(),x.getCliente().getId(),x.getCodice(),x.getCliente().getCodice())).collect(Collectors.toList()));
    }

    public GetInfoBobinaResponse getInfoBobina(String text, String username) throws SQLException {
        text = text.toUpperCase().replace("-", "/");
        //Prima ricerco nel mio database, se esiste la bobina ritorno le info di quella,altrimenti
        //ricerco da Embyon utilizzando il sistema esterno associato al magazzino dell'utente
        Optional<Bobina> optionalBobina = bobinaService.getBobinaByText(text);
        if(optionalBobina.isPresent()){
            //Ho già memorizzato questa bobina in passato quindi basta ritornare questa
            Bobina bobina = optionalBobina.get();
            return new GetInfoBobinaResponse(bobina.getId(),bobina.getCodice(),bobina.getCliente().getId(),bobina.getCliente().getCodice());
        }
        //Non ho già memorizzato questa bobina, lo devo fare
        //Per farlo devo utilizzare l'embyon associato allo stabilimento del magazzino del utente
        //se è EMB_BA ricerco su Embon BA
        //se è EMB:CHI ricerco su Emb CHI
        //altrimenti ritorno errore(nel codice metto SE_NON_SUPPORTATO)
        Utente utente = utenteService.findUserByUsername(username);
        BobinaProvider bobinaProvider;
        try{
            bobinaProvider = sistemaEsternoProvider.getBobinaProvider(TipoSistemaEsterno.valueOf(utente.getMagazzino().getStabilimento().getSistemaEsterno().getCodice()));
        }
        catch (SistemaEsternoNotSupportedException exception){
            return new GetInfoBobinaResponse("SE_NON_SUPPORTATO");
        }
        Optional<DatiBobina> datiBobinaByTextOptional = bobinaProvider.getDatiBobinaByText(text);
        //Se il service non mi ha ritornato niente ritorno new getInfoBobina con NON TROVATO
        if(datiBobinaByTextOptional.isEmpty()) return new GetInfoBobinaResponse("NON_TROVATO");
        DatiBobina datiBobinaByText = datiBobinaByTextOptional.get();
        //Il service mi ha ritornato un entità(codicePartita,codiceCliente,ragioneSociale)
        //Ricerco per codice cliente + sistema Esterno trovato e vedo se ottengo un cliente
        SistemaEsterno sistemaEsternoUtente = utente.getMagazzino().getStabilimento().getSistemaEsterno();
        Optional<Cliente> clienteOptional = clienteService.getClienteByCodiceIdSistemaEsterno(datiBobinaByText.getCodiceCliente(),sistemaEsternoUtente.getId());
        Bobina bobina;
        if(clienteOptional.isPresent()){
            //Se c'è l'ho lo ottengo e creo solo la bobina associandola a lui
            bobina = bobinaService.creaBobina(datiBobinaByText.getCodice(),clienteOptional.get());
        }
        else {
            //Se non ho il cliente prima lo creo e poi ci associo la bobina
            Cliente cliente = clienteService.creaCliente(datiBobinaByText.getCodiceCliente(),datiBobinaByText.getRagioneSocialeCliente(),sistemaEsternoUtente);
            bobina = bobinaService.creaBobina(datiBobinaByText.getCodice(), cliente);
        }
        //infine ritorno la bobina appena creata
        return new GetInfoBobinaResponse(bobina.getId(),bobina.getCodice(),bobina.getCliente().getId(),bobina.getCliente().getCodice());
    }

    public void associaBobine(AssociaBobineRequest request, String username) {
        Cauzione cauzione = getById(request.getId());
        Utente utente = utenteService.findUserByUsername(username);
        bobinaService.rimuoviBobineAssociateByIdCauzione(request.getId());
        if(request.getCodiciBobine().size() == 0){
            //Cambio stato a LIBERO e ritorno
            cauzioneRepository.updateCauzione(cauzione.getId(),utente.getMagazzino().getId(),statoCauzioneService.getByTipo(TipoStatoCauzione.LIBERO).getId());
            storicoCauzioneService.aggiungiStorico(cauzione, statoCauzioneService.getByTipo(TipoStatoCauzione.LIBERO),magazzinoService.getById(utente.getMagazzino().getId()),utente,TipoOperazione.ASSOCIA_BOBINE, null);
            return;
        }
        //Ho gli id delle bobine gliele devo associare
        bobinaService.associaBobineACauzione(request.getCodiciBobine().stream().map(BobineAssociaBobineRequest::getId).collect(Collectors.toList()),cauzione);
        //Long idPrimaBobina = request.getCodiciBobine().get(0).getId();
        cauzioneRepository.updateCauzione(cauzione.getId(),utente.getMagazzino().getId(),statoCauzioneService.getByTipo(TipoStatoCauzione.OCCUPATO).getId());
        storicoCauzioneService.aggiungiStorico(getById(cauzione.getId()), statoCauzioneService.getByTipo(TipoStatoCauzione.OCCUPATO), utente.getMagazzino(), utente,TipoOperazione.ASSOCIA_BOBINE, null);
    }


    public Long creaNuovaCauzione(CreaNuovaCauzioneRequest request, String username) {
        Utente user = utenteService.findUserByUsername(username);
        Cauzione nuova = new Cauzione(request.getEpcTag(),request.getMatricola(),request.getTimestampAcquisto(),
                tipologiaCauzioneService.getById(request.getIdTipologiaCauzione()),magazzinoService.getById(request.getIdMagazzino()),
                statoCauzioneService.getById(request.getIdStatoCauzione()));
        salva(nuova);
        storicoCauzioneService.aggiungiStorico(nuova,statoCauzioneService.getById(request.getIdStatoCauzione()),
                magazzinoService.getById(request.getIdMagazzino()),user,TipoOperazione.CREAZIONE,null);
        return nuova.getId();
    }

    public void deleteCauzioneById(Long id) {
        storicoCauzioneService.deleteByIdCauzione(id);
        cauzioneRepository.deleteById(id);
    }

    public GetTagInfoResponse getTagInfo(String epcTag) {
        //NB. Utilizzato da SW SILVANO CATTANEO
        Cauzione cauzione = getSezioneDatiCauzioneByText(epcTag.toUpperCase()).orElseThrow(() -> new EntityNotFoundException("Cauzione " + epcTag + " non trovata"));
        Optional<Revisione> ultimaRevisione = storicoCauzioneService.getUltimaRevisione(cauzione.getId());
        return new GetTagInfoResponse(cauzione.getMatricola(),cauzione.getTipologiaCauzione().getCodiceTerminalino(),
                cauzione.getTimestampAcquisto(), ultimaRevisione.map(Revisione::getDataRevisione).orElse(null),
                cauzione.getStatoCauzione().getCodice());
    }

    public List<ValidateResponse> validateEpcTag(ValidateEpcTagRequest request) {
        //NB. Utilizzato da SW SILVANO CATTANEO
        //Elimino TAG duplicati
        Set<String> set = new HashSet<>(request.getEpcTagList());
        request.getEpcTagList().clear();
        request.getEpcTagList().addAll(set);
        if("U".equals(request.getDirection())) return varcoCarica(request);
        if("E".equals(request.getDirection())) return varcoScarica(request);
        throw new InvalidRequestException("Direzione " + request.getDirection() + " non supportata");
    }

    private List<ValidateResponse> varcoCarica(ValidateEpcTagRequest request) {
        List<Cauzione> cauzioneList = cauzioneRepository.getCauzioniListWithStatoAndMagazzinoByEpcTagList(request.getEpcTagList());
        List<ValidateResponse> out = new LinkedList<>();
        for(Cauzione cauzione : cauzioneList){
            List<Bobina> bobineAssociate = bobinaService.getBobineAssociate(cauzione.getId());
            Magazzino magazzino;
            StatoCauzione statoCauzione;
            if(TipoStatoCauzione.valueOf(cauzione.getStatoCauzione().getCodice()).equals(TipoStatoCauzione.OCCUPATO) && bobineAssociate.size() > 0){
                //Caso OCCUPATO
                magazzino = magazzinoService.getByIdbobina(bobineAssociate.get(0).getId());
                statoCauzione = statoCauzioneService.getByTipo(TipoStatoCauzione.OCCUPATO);
            }
            else{
                //Lo metto libero e ci associo il magazzino di carico del varco
                magazzino = varcoService.getVarcoById(request.getGateId()).getMagazzinoCarico();
                statoCauzione = statoCauzioneService.getByTipo(TipoStatoCauzione.LIBERO);
            }
            cauzioneRepository.updateCauzione(cauzione.getId(),magazzino.getId(),statoCauzione.getId());
            out.add(new ValidateResponse(cauzione.getEpcTag(),cauzione.getMatricola(), false,"OK"));
            storicoCauzioneService.aggiungiStorico(cauzione,statoCauzione,magazzino,null,TipoOperazione.CARICO_VARCO,null);
        }
        setTrespoliNonTrovati(request, cauzioneList, out);
        return out;
    }

    private List<ValidateResponse> varcoScarica(ValidateEpcTagRequest request) {
        List<Cauzione> cauzioneList = cauzioneRepository.getCauzioniListWithStatoAndMagazzinoByEpcTagList(request.getEpcTagList());
        List<ValidateResponse> out = new LinkedList<>();
        Magazzino magazzino = varcoService.getVarcoById(request.getGateId()).getMagazzinoScarico();
        for (Cauzione cauzione : cauzioneList){
            List<Bobina> bobineAssociate = bobinaService.getBobineAssociate(cauzione.getId());
            StatoCauzione statoCauzione;
            Magazzino magazzinoCauzioneAttuale = cauzione.getMagazzino();
            TipoStatoCauzione tipoStatoCauzioneAttuale = TipoStatoCauzione.valueOf(cauzione.getStatoCauzione().getCodice());
            if(tipoStatoCauzioneAttuale.equals(TipoStatoCauzione.OCCUPATO) && !magazzinoCauzioneAttuale.isMagazzinoCliente() && bobineAssociate.size() > 0){
                //Qui ho stato OCCUPATO e sono in un magazzino interno, quindi rimango in stato occupato
                statoCauzione = cauzione.getStatoCauzione();
            }
            else {
                statoCauzione = cauzioneDaManutenere(cauzione) ? statoCauzioneService.getByTipo(TipoStatoCauzione.IN_MANUTENZIONE) : statoCauzioneService.getByTipo(TipoStatoCauzione.LIBERO);
                bobinaService.rimuoviBobineAssociateByIdCauzione(cauzione.getId());
            }
            cauzioneRepository.updateCauzione(cauzione.getId(),magazzino.getId(),statoCauzione.getId());
            boolean daRevisionare = TipoStatoCauzione.valueOf(statoCauzione.getCodice()).equals(TipoStatoCauzione.IN_MANUTENZIONE);
            out.add(new ValidateResponse(cauzione.getEpcTag(),cauzione.getMatricola(),daRevisionare,daRevisionare ? "Trespolo da revisionare" : "OK"));
            storicoCauzioneService.aggiungiStorico(cauzione,statoCauzione,magazzino,null,TipoOperazione.SCARICO_VARCO,null);
        }
        setTrespoliNonTrovati(request, cauzioneList, out);
        return out;
    }

    private void setTrespoliNonTrovati(ValidateEpcTagRequest request, List<Cauzione> cauzioneList, List<ValidateResponse> out) {
        //Se non ho processato alcuni trespoli li metto nella response aggiungendoli con errore NON_TROVATO
        List<String> trespoliNonTrovati =
                request.getEpcTagList().stream().filter(x -> cauzioneList.stream().filter(y -> y.getEpcTag().equals(x)).findFirst().isEmpty()).toList();
        trespoliNonTrovati.forEach(x -> out.add(new ValidateResponse(x,"TAG_NON_ASSOCIATO",true,"Il Tag non è associato ad alcun bancale")));
    }

    public ValidateResponse tagInitialize(TagInitializeRequest request) {
        Magazzino magazzino = magazzinoService.getById(request.getSiteId());
        StatoCauzione statoCauzione = statoCauzioneService.getByTipo(TipoStatoCauzione.LIBERO);
        Cauzione cauzione = new Cauzione(request.getEpcTagId(),request.getPalletCode(),request.getPurchaseDate(),tipologiaCauzioneService.getByCodice("ITR#" + request.getEmbyonPalletCode()),magazzino,statoCauzione);
        try {
            cauzioneRepository.save(cauzione);
            storicoCauzioneService.aggiungiStorico(cauzione,statoCauzione,magazzino,null,TipoOperazione.CREAZIONE,null);
            return new ValidateResponse(cauzione.getEpcTag(),cauzione.getMatricola(),false,"OK");
        }
        catch (EntityNotFoundException ex){
            throw ex;
        }
        catch (Exception ex){
            throw new InvalidRequestException("Impossibile creare la cauzione, epcTag duplicato o matricola duplicata");
        }
    }

    public ValidateResponse tagRevision(TagRevisionRequest request) {
        Cauzione cauzione = getSezioneDatiCauzioneByText(request.getEpcTagId()).orElseThrow(() -> new EntityNotFoundException("Cauzione " + request.getEpcTagId() + " non trovata"));
        StatoCauzione statoCauzione = statoCauzioneService.getByTipo(TipoStatoCauzione.LIBERO);
        Magazzino magazzino = magazzinoService.getById(request.getSiteId());
        cauzioneRepository.updateCauzione(cauzione.getId(),request.getSiteId(),statoCauzione.getId());
        storicoCauzioneService.aggiungiStorico(cauzione,statoCauzione,magazzino,null,TipoOperazione.RIPARAZIONE,null);
        return new ValidateResponse(request.getEpcTagId(), cauzione.getMatricola(),false,"OK");
    }

    public ValidateResponse tagUpdateAssociation(TagUpdateAssociationRequest request) {
        Cauzione cauzione = getSezioneDatiCauzioneByText(request.getPalletCode()).orElseThrow(() -> new EntityNotFoundException("Cauzione " + request.getPalletCode() + " non trovata"));
        cauzioneRepository.updateCauzione(request.getPalletCode(),request.getNewEpcTagId(),request.getSiteId());
        cauzione.setEpcTag(request.getNewEpcTagId());
        storicoCauzioneService.aggiungiStorico(cauzione,cauzione.getStatoCauzione(),magazzinoService.getById(request.getSiteId()),null,TipoOperazione.AGGIORNAMENTO_MANUALE,null);
        return new ValidateResponse(request.getNewEpcTagId(),request.getPalletCode(),false,"OK");
    }

    public ValidateResponse tagUpdateTipoTrespolo(TagUpdateTipoTrespolo request) {
        Cauzione cauzione = getSezioneDatiCauzioneByText(request.getEpcTagId()).orElseThrow(() -> new EntityNotFoundException("Cauzione " + request.getEpcTagId() + " non trovata"));
        TipologiaCauzione tipologiaCauzione = tipologiaCauzioneService.getByCodice("ITR#" + request.getTipoTrespolo());
        Magazzino magazzino = magazzinoService.getById(request.getSiteId());
        cauzioneRepository.updateCauzione(request.getEpcTagId(),tipologiaCauzione.getId(), magazzino.getId());
        cauzione.setTipologiaCauzione(tipologiaCauzione);
        storicoCauzioneService.aggiungiStorico(cauzione,cauzione.getStatoCauzione(),magazzino,null,TipoOperazione.AGGIORNAMENTO_MANUALE,null);
        return new ValidateResponse(request.getEpcTagId(),cauzione.getMatricola(),false,"OK");
    }

    @Transactional(readOnly = true)
    public Optional<Revisione> getUltimaRevisione(Long idCauzione) {
        return storicoCauzioneService.getUltimaRevisione(idCauzione);
    }

    @Transactional(readOnly = true)
    public List<Cauzione> getTrespoliImportati(List<String> epcTagList) {
        return cauzioneRepository.getAllByEpcTagList(epcTagList);
    }

    public void ricercaEDisattivaTrespoli() {
        //Trovo i trespoli che hanno ultima movimentazione maggiore o uguale a 5 anni
        List<UltimoStorico> ultimiStorici = storicoCauzioneService.getUltimiStorici();
        List<Long> idCauzioniDaDisattivare =
                ultimiStorici.stream().filter(x -> x.getAnniDiff() >= 5.00).map(UltimoStorico::getIdCauzione).toList();
        cauzioneRepository.disattivaCauzioni(idCauzioniDaDisattivare,statoCauzioneService.getByTipo(TipoStatoCauzione.NON_ATTIVA).getId());
    }
}
