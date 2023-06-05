package com.mondorevive.TRESPOT.controllers;

import com.mondorevive.TRESPOT.requests.GruppiUltimaOperazioneRequest;
import com.mondorevive.TRESPOT.statistiche.StatisticheService;
import com.mondorevive.TRESPOT.utils.JwtUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@CrossOrigin(origins = "*", maxAge = 3600,exposedHeaders = {"Content-Disposition"})
@Validated
@RequestMapping("/api/statistica")
@RequiredArgsConstructor
public class StatisticheController {
    private final String CONTROLLER_TAG = "STATISTICA_CONTROLLER - ";
    private final StatisticheService statisticheService;
    private final JwtUtils jwtUtils;
    @GetMapping("/dashboard")
    public ResponseEntity<Object>getDashboardData(@RequestHeader("Authorization")String token){
        log.info(CONTROLLER_TAG + "GET dashboard");
        return ResponseEntity.ok(statisticheService.getDashboardData(jwtUtils.getUsername(token)));
    }

    @GetMapping("/tipologiaCauzioneCliente")
    public ResponseEntity<Object>getStatisticaTipologiaCauzioneCliente(@RequestHeader("Authorization")String token,
                                                                       @RequestParam Long idTipologiaCauzione){
        log.info(CONTROLLER_TAG + "GET tipologiaCauzioneCliente");
        return ResponseEntity.ok(statisticheService.getStatisticaTipologiaCauzioneCliente(idTipologiaCauzione,jwtUtils.getUsername(token)));
    }

    @PostMapping("/cauzioniAttive")
    public ResponseEntity<Object>getStatisticaCauzioniAttive(@RequestBody List<GruppiUltimaOperazioneRequest> requestList){
        log.info(CONTROLLER_TAG + "POST cauzioniAttive");
        return ResponseEntity.ok(statisticheService.getStatisticaCauzioniAttive(requestList));
    }

    @GetMapping("/dettaglioCauzioniAttive")
    public ResponseEntity<Object>getDettaglioCauzioniAttive(@RequestParam Long da, @RequestParam Long a){
        log.info(CONTROLLER_TAG + "GET dettaglioCauzioniAttive");
        return ResponseEntity.ok(statisticheService.getDettaglioCauzioniAttive(da,a));
    }

    @GetMapping("/revisioni")
    public ResponseEntity<Object>getStatisticaRevisioni(@RequestParam String dataInizio, @RequestParam String dataFine){
        log.info(CONTROLLER_TAG + "GET revisioni");
        return ResponseEntity.ok(statisticheService.getStatisticaRevisioniResponse(dataInizio, dataFine));
    }
}
