package com.mondorevive.TRESPOT.statistiche;

import com.mondorevive.TRESPOT.cauzione.statoCauzione.StatoCauzioneService;
import com.mondorevive.TRESPOT.cauzione.statoCauzione.TipoStatoCauzione;
import com.mondorevive.TRESPOT.cauzione.storicoCauzione.StoricoCauzione;
import com.mondorevive.TRESPOT.cauzione.storicoCauzione.operazione.OperazioneService;
import com.mondorevive.TRESPOT.cauzione.storicoCauzione.operazione.TipoOperazione;
import com.mondorevive.TRESPOT.responses.*;
import com.mondorevive.TRESPOT.stabilimento.Stabilimento;
import com.mondorevive.TRESPOT.utente.UtenteService;
import com.mondorevive.TRESPOT.utils.DateUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
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
    public StatisticaCauzioniAttiveResponse getStatisticaCauzioniAttive() {
        StatisticaCauzioniAttiveResponse response = new StatisticaCauzioniAttiveResponse();
        response.setAcquistiCauzioniDataList(statisticheCauzioneRepository.getAcquistiCauzioniData());
        System.out.println("TEST1");
        List<StoricoCauzione> ultimiStorici = statisticheCauzioneRepository.getUltimiStorici();
        System.out.println("TEST2");
        Map<Long,Boolean> check = new HashMap<>();
        for(StoricoCauzione s : ultimiStorici){
            if(check.containsKey(s.getCauzione().getId())) throw new RuntimeException(("Esiste già la cauzione"));
            check.put(s.getCauzione().getId(),true);
        }
        return response;
    }
}
