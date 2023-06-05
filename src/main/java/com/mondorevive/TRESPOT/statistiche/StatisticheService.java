package com.mondorevive.TRESPOT.statistiche;

import com.mondorevive.TRESPOT.cauzione.statoCauzione.StatoCauzioneService;
import com.mondorevive.TRESPOT.cauzione.statoCauzione.TipoStatoCauzione;
import com.mondorevive.TRESPOT.cauzione.storicoCauzione.StoricoCauzione;
import com.mondorevive.TRESPOT.cauzione.storicoCauzione.operazione.OperazioneService;
import com.mondorevive.TRESPOT.cauzione.storicoCauzione.operazione.TipoOperazione;
import com.mondorevive.TRESPOT.pojo.DataInizioDataFine;
import com.mondorevive.TRESPOT.pojo.DatiRevisioni;
import com.mondorevive.TRESPOT.pojo.UltimoStorico;
import com.mondorevive.TRESPOT.requests.GruppiUltimaOperazioneRequest;
import com.mondorevive.TRESPOT.responses.*;
import com.mondorevive.TRESPOT.stabilimento.Stabilimento;
import com.mondorevive.TRESPOT.utente.UtenteService;
import com.mondorevive.TRESPOT.utils.DateUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class StatisticheService {
    private final StatisticheCauzioneRepository statisticheCauzioneRepository;
    private final StatoCauzioneService statoCauzioneService;
    private final UtenteService utenteService;
    private final OperazioneService operazioneService;
    public GetDashboardDataResponse getDashboardData(String username) {
        List<Long> idStabilimentiList =
                utenteService.getStabilimentiUtente(username).stream().map(Stabilimento::getId).toList();
        GetDashboardDataResponse response = new GetDashboardDataResponse();
        response.setCauzioniTotali(statisticheCauzioneRepository.countCauzioni());
        response.setCauzioniMagazzino(statisticheCauzioneRepository.countCauzioniLibere(idStabilimentiList,statoCauzioneService.getByTipo(TipoStatoCauzione.LIBERO).getId()));
        response.setCauzioniClienti(statisticheCauzioneRepository.countCauzioniFuori(idStabilimentiList));
        response.setCauzioniInManutenzione(statisticheCauzioneRepository.countCauzioniManutenzione(idStabilimentiList,statoCauzioneService.getByTipo(TipoStatoCauzione.IN_MANUTENZIONE).getId()));
        response.setCauzioniInRiparazione(statisticheCauzioneRepository.countCauzioniRiparazione(idStabilimentiList,statoCauzioneService.getByTipo(TipoStatoCauzione.IN_RIPARAZIONE).getId()));
        //Due query per ottenere il grafico
        //Prima query: ottengo le revisioni fatte nei 12 mesi precedenti ad oggi
        //Ottengo prima il periodo
        LocalDateTime now = DateUtils.getTimestampCorrente();
        LocalDateTime dataInizio = now.minusMonths(12).withDayOfMonth(1).toLocalDate().atStartOfDay();
        LocalDateTime dataFine = now.with(TemporalAdjusters.lastDayOfMonth()).toLocalDate().atStartOfDay();
        response.setDataRevisioni(statisticheCauzioneRepository.getRevisioniChartData(dataInizio,
                dataFine, idStabilimentiList));
        //raggruppo per mese e conto revisioni con flag
        //Seconda query: ottengo le operazioni fatte nei 12 mesi precedenti ad oggi
        //raggruppo per mese e conto tipo operozione carico e carico manuale e scarico e scarico manuale
        response.setDataOperazioni(statisticheCauzioneRepository.getOperazioniChartData(dataInizio,dataFine,
                operazioneService.getByTipo(TipoOperazione.CARICO_VARCO).getId(),
                operazioneService.getByTipo(TipoOperazione.CARICO_MANUALE).getId(),
                operazioneService.getByTipo(TipoOperazione.SCARICO_VARCO).getId(),
                operazioneService.getByTipo(TipoOperazione.SCARICO_MANUALE).getId(),
                idStabilimentiList
        ));
        response.setDataTipologieCauzione(statisticheCauzioneRepository.getTipologieCauzioneChartData());
        return response;
    }

    @Transactional(readOnly = true)
    public StatisticaTipologiaCauzione getStatisticaTipologiaCauzioneCliente(Long idTipologiaCauzione, String username) {
        StatisticaTipologiaCauzione response = new StatisticaTipologiaCauzione();
        List<Long> idStabilimentiList =
                utenteService.getStabilimentiUtente(username).stream().map(Stabilimento::getId).toList();
        response.setTotale(statisticheCauzioneRepository.countCauzioni(idTipologiaCauzione));
        response.setDisponibili(statisticheCauzioneRepository.countCauzioniDisponibili(idStabilimentiList, idTipologiaCauzione, statoCauzioneService.getByTipo(TipoStatoCauzione.LIBERO).getId()));
        response.setFuori(statisticheCauzioneRepository.countCauzioniFuori(idStabilimentiList,idTipologiaCauzione));
        response.setInManutenzione(statisticheCauzioneRepository.countCauzioniByStato(idStabilimentiList,idTipologiaCauzione, statoCauzioneService.getByTipo(TipoStatoCauzione.IN_MANUTENZIONE).getId()));
        response.setInRiparazione(statisticheCauzioneRepository.countCauzioniByStato(idStabilimentiList,idTipologiaCauzione,statoCauzioneService.getByTipo(TipoStatoCauzione.IN_RIPARAZIONE).getId()));
        response.setTipologieCauzioneMagazzinoInternoList(statisticheCauzioneRepository.getTipologieCauzioneMagazzinoInterno(idTipologiaCauzione,
                statoCauzioneService.getByTipo(TipoStatoCauzione.LIBERO).getId(),
                statoCauzioneService.getByTipo(TipoStatoCauzione.OCCUPATO).getId(),
                statoCauzioneService.getByTipo(TipoStatoCauzione.IN_MANUTENZIONE).getId(),
                statoCauzioneService.getByTipo(TipoStatoCauzione.IN_RIPARAZIONE).getId()));
        response.setTipologieCauzioneClienteList(statisticheCauzioneRepository.getStatisticaTipologiaCauzioneCliente(idTipologiaCauzione));
        return response;
    }

    @Transactional(readOnly = true)
    public StatisticaCauzioniAttiveResponse getStatisticaCauzioniAttive(List<GruppiUltimaOperazioneRequest>requestList) {
        StatisticaCauzioniAttiveResponse response = new StatisticaCauzioniAttiveResponse();
        response.setAcquistiCauzioniDataList(statisticheCauzioneRepository.getAcquistiCauzioniData());
        Map<String,Long>map = new HashMap<>();
        List<UltimoStorico> ultimiStorici = statisticheCauzioneRepository.getUltimiStorici();
        for(UltimoStorico ultimoStorico : ultimiStorici){
            //In che range è?
            String key;
            Optional<GruppiUltimaOperazioneRequest> first =
                    requestList.stream().filter(x -> x.getDa() <= ultimoStorico.getAnniDiff().intValue() & x.getA() >= ultimoStorico.getAnniDiff().intValue()).findFirst();
            if(first.isEmpty()) key = "altro";
            else key = first.get().getDa() + " - " + first.get().getA();
            Long count = map.get(key);
            if(count == null)
                map.put(key, 1L);
            else map.put(key,count+1);
        }
        //Trasformo la mappa in response
        List<StatisticaUltimaOperazioneResponse>out = new LinkedList<>();
        for (var entry : map.entrySet()) {
            out.add(new StatisticaUltimaOperazioneResponse(entry.getKey(),entry.getValue()));
        }
        out.sort(Comparator.comparing(StatisticaUltimaOperazioneResponse::getLabel));
        response.setCauzioniConUltimaOperazione(out);
        return response;
    }

    @Transactional(readOnly = true)
    public List<DettaglioCauzioniAttive> getDettaglioCauzioniAttive(Long da, Long a) {
        return statisticheCauzioneRepository.getDettaglioCauzioniAttive(da,a);
    }

    @Transactional(readOnly = true)
    public StatisticaRevisioniResponse getStatisticaRevisioniResponse(String dataInizio, String dataFine){
        DataInizioDataFine dataInizioDataFine = DateUtils.getDataInizioDataFine(dataInizio, dataFine);
        StatisticaRevisioniResponse response = new StatisticaRevisioniResponse();
        response.setCauzioniTotali(statisticheCauzioneRepository.countCauzioni());
        DatiRevisioni datiRevisioni =
                statisticheCauzioneRepository.getDatiRevisioni(dataInizioDataFine.getDataInizio(),
                        dataInizioDataFine.getDataFine());
        response.setCauzioniRevisionate(datiRevisioni.getTotale());
        response.setRevisioniOk(datiRevisioni.getOk());
        response.setRevisioniKo(datiRevisioni.getKo());
        response.setAndamentiRevisioni(statisticheCauzioneRepository.getAndamentiRevisioni(dataInizioDataFine.getDataInizio(),
                dataInizioDataFine.getDataFine()));
        return response;
    }
}
