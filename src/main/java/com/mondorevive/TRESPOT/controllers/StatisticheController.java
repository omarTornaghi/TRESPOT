package com.mondorevive.TRESPOT.controllers;

import com.mondorevive.TRESPOT.statistiche.StatisticheService;
import com.mondorevive.TRESPOT.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/cauzioniAttive")
    public ResponseEntity<Object>getStatisticaCauzioniAttive(){
        log.info(CONTROLLER_TAG + "GET cauzioniAttive");
        return ResponseEntity.ok(statisticheService.getStatisticaCauzioniAttive());
    }
}
