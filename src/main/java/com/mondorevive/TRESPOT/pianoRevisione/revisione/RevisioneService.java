package com.mondorevive.TRESPOT.pianoRevisione.revisione;

import com.mondorevive.TRESPOT.cauzione.CauzioneService;
import com.mondorevive.TRESPOT.cauzione.PageResponse;
import com.mondorevive.TRESPOT.cauzione.storicoCauzione.StoricoCauzione;
import com.mondorevive.TRESPOT.cauzione.storicoCauzione.StoricoCauzioneService;
import com.mondorevive.TRESPOT.pagination.PaginationService;
import com.mondorevive.TRESPOT.requests.*;
import com.mondorevive.TRESPOT.responses.DettaglioRevisioneResponse;
import com.mondorevive.TRESPOT.responses.GetAllRevisioniResponse;
import com.mondorevive.TRESPOT.responses.SezioneDatiCauzioneResponse;
import com.mondorevive.TRESPOT.utente.Utente;
import com.mondorevive.TRESPOT.utente.UtenteService;
import com.mondorevive.TRESPOT.utils.DateUtils;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.constraints.NotNull;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class RevisioneService {
    private final RevisioneRepository revisioneRepository;
    private final PaginationRevisioneRepository paginationRevisioneRepository;
    private final PaginationService paginationService;
    private final CauzioneService cauzioneService;
    private final StoricoCauzioneService storicoCauzioneService;
    private final UtenteService utenteService;

    public RevisioneService(RevisioneRepository revisioneRepository,
                            PaginationRevisioneRepository paginationRevisioneRepository,
                            PaginationService paginationService, @Lazy @NotNull CauzioneService cauzioneService,
                            @Lazy @NotNull StoricoCauzioneService storicoCauzioneService, UtenteService utenteService) {
        this.revisioneRepository = revisioneRepository;
        this.paginationRevisioneRepository = paginationRevisioneRepository;
        this.paginationService = paginationService;
        this.cauzioneService = cauzioneService;
        this.storicoCauzioneService = storicoCauzioneService;
        this.utenteService = utenteService;
    }

    private void salva(Revisione revisione) {
        revisioneRepository.save(revisione);
    }

    @Transactional(readOnly = true)
    public Revisione getById(Long id){
        return revisioneRepository.findRevisioneById(id).orElseThrow(() -> new EntityNotFoundException("Revisione con id " + id + " non trovata"));
    }

    @Transactional(readOnly = true)
    public Optional<Revisione> getUltimaRevisione(Long idCauzione) {
        List<Revisione> ultimaRevisione = revisioneRepository.getUltimaRevisione(idCauzione);
        return ultimaRevisione.size() > 0 ? Optional.of(ultimaRevisione.get(0)) :  Optional.empty();
    }

    public PageResponse<GetAllRevisioniResponse> getAll(PaginationRequest request) {
        Specification<Revisione> specification = Specification.where(RevisioneSpecifications.epcTagContains(request.getText()))
                .or(RevisioneSpecifications.matricolaContains(request.getText()));
        for(FiltroRequest filtro : request.getFiltri()){
            switch (filtro.getColonna()) {
                case "operatore" ->
                        specification = specification.and(Specification.where(RevisioneSpecifications.idOperatoreEqual(filtro.getValore())));
                case "conformitaTotale" ->
                        specification = specification.and(Specification.where(RevisioneSpecifications.conformitaTotaleEqual(filtro.getValore())));
                case "dataInizio" -> {
                    LocalDateTime dataInizio = DateUtils.getDataInizioDataFine(filtro.getValore(), filtro.getValore()).getDataInizio();
                    System.out.println("dInizio=" + dataInizio.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                    specification = specification.and(Specification.where(RevisioneSpecifications.dataRevisioneDopo(dataInizio)));
                }
                case "dataFine" -> {
                    LocalDateTime dataFine = DateUtils.getDataInizioDataFine(filtro.getValore(), filtro.getValore()).getDataFine();
                    System.out.println("dFine=" +dataFine.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                    specification = specification.and(Specification.where(RevisioneSpecifications.dataRevisionePrima(dataFine)));
                }
            }
        }
        if(request.getOrdinamento() == null || request.getOrdinamento().getColonna() == null) request.setOrdinamento(new OrdinamentoRequest("dataRevisione", "DESC"));
        PageResponse<Revisione> page = paginationService.getPage(specification, request, paginationRevisioneRepository);
        return new PageResponse<>(page, buildContent(page.getContent()));
    }

    private List<GetAllRevisioniResponse> buildContent(List<Revisione> content) {
        return content.stream().map(x -> new GetAllRevisioniResponse(x.getId(), x.getDataRevisione(),
                x.getStoricoCauzione().getCauzione().getEpcTag(),
                x.getStoricoCauzione().getCauzione().getMatricola(),
                x.getStoricoCauzione().getUtente().getUsername(),
                x.getConformitaTotale())).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public DettaglioRevisioneResponse getDettaglioById(Long id) {
        DettaglioRevisioneResponse dettaglioById = revisioneRepository.getDettaglioById(id);
        Optional<Revisione> ultimaRevisione =
                cauzioneService.getUltimaRevisione(dettaglioById.getDatiCauzione().getIdCauzione());
        SezioneDatiCauzioneResponse datiCauzione = dettaglioById.getDatiCauzione();
        datiCauzione.setDataUltimaRevisione(ultimaRevisione.map(Revisione::getDataRevisione).orElse(null));
        dettaglioById.setDatiCauzione(datiCauzione);
        return dettaglioById;
    }

    public void creaNuovaRevisione(NuovaRevisioneRequest request, String username) {
        //Allora in teoria devo: creare un nuovo oggetto revisione
        Revisione revisione = new Revisione(request.getDataRevisione() != null ? request.getDataRevisione() : DateUtils.getTimestampCorrente(),request.getMancaAggiornamento(),request.getConformitaTotale(),
                request.getTargaPresente(),request.getConformitaDisegnoTecnico(),request.getInterventoMembrature(),
                request.getDescrizioneInterventoMembrature(),request.getInterventoSaldatura(),request.getCernieraBullonata(),
                request.getCattivoUsoInforcatura(),request.getCattivoUsoCollisione(),request.getAltroIntervento(),request.getStabilitaGlobale(),
                request.getFunzionamentoRfid(),request.getUlterioriNote());
        cauzioneService.aggiungiNuovaRevisione(request.getIdCauzione(),revisione, username);

    }

    public void importaRevisione(Revisione revisione) {
        salva(revisione);
    }

    public void deleteByIdCauzione(Long id) {
        revisioneRepository.deleteByIdCauzione(id);
    }

    public void deleteRevisioneById(Long id) {
        Revisione byId = getById(id);
        StoricoCauzione storicoCauzione = byId.getStoricoCauzione();
        revisioneRepository.deleteById(id);
        storicoCauzioneService.deleteById(storicoCauzione.getId());
    }

    public void updateRevisione(UpdateRevisioneRequest request) {
        revisioneRepository.updateRevisione(request.getId(),request.getDataRevisione(),
                request.getMancaAggiornamento(),request.getConformitaTotale(),
                request.getTargaPresente(),request.getConformitaDisegnoTecnico(),
                request.getInterventoMembrature(),request.getDescrizioneInterventoMembrature(),
                request.getInterventoSaldatura(),request.getCernieraBullonata(),
                request.getCattivoUsoInforcatura(),request.getCattivoUsoCollisione(),
                request.getAltroIntervento(),request.getStabilitaGlobale(),
                request.getFunzionamentoRfid(),request.getUlterioriNote());
    }
}
