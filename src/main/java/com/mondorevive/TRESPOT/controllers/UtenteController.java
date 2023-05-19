package com.mondorevive.TRESPOT.controllers;

import com.mondorevive.TRESPOT.requests.CreaNuovoUtenteRequest;
import com.mondorevive.TRESPOT.requests.UpdateProfiloRequest;
import com.mondorevive.TRESPOT.requests.UpdateUtenteRequest;
import com.mondorevive.TRESPOT.utente.UtenteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@CrossOrigin(origins = "*", maxAge = 3600,exposedHeaders = {"Content-Disposition"})
@Validated
@RequestMapping("/api/utente")
@RequiredArgsConstructor
public class UtenteController {
    private final String CONTROLLER_TAG = "UTENTE_CONTROLLER - ";
    private final UtenteService utenteService;

    @GetMapping("/utenti")
    public ResponseEntity<Object>getAll(){
        log.info(CONTROLLER_TAG + "GET utenti");
        return ResponseEntity.ok(utenteService.getAll());
    }
    @GetMapping("/utentiSelect")
    public ResponseEntity<Object>getAllSelect(){
        log.info(CONTROLLER_TAG + "GET utentiSelect");
        return ResponseEntity.ok(utenteService.getAllSelect());
    }
    @GetMapping("/profilo")
    public ResponseEntity<Object>getProfilo(@RequestParam String username){
        log.info(CONTROLLER_TAG + "GET profilo");
        return ResponseEntity.ok(utenteService.getProfiloUtente(username));
    }

    @GetMapping("/ruoli")
    public ResponseEntity<Object>getAllRuoli(){
        return ResponseEntity.ok(utenteService.getAllRuoli());
    }

    @GetMapping("/utenteById")
    public ResponseEntity<Object> getUtenteById(@RequestParam Long id){
        return ResponseEntity.ok(utenteService.getDettagliUtente(id));
    }

    @PostMapping ("/nuovoUtente")
    public ResponseEntity<Object>creaNuovoUtente(@Valid @RequestBody CreaNuovoUtenteRequest request){
        log.info(CONTROLLER_TAG + "POST nuovoUtente");
        return ResponseEntity.ok(utenteService.creaNuovoUtente(request));
    }

    @PostMapping("/updateUtente")
    public ResponseEntity<Object> updateUtente(@Valid @RequestBody UpdateUtenteRequest request){
        log.info(CONTROLLER_TAG + "POST updateUtente");
        utenteService.updateUtente(request);
        return ResponseEntity.ok(null);
    }

    @PostMapping("/updateProfilo")
    public ResponseEntity<Object>updateProfilo(@Valid @RequestBody UpdateProfiloRequest request){
        log.info(CONTROLLER_TAG + "POST updateProfilo");
        utenteService.updateProfilo(request);
        return ResponseEntity.ok(null);
    }

    @PostMapping("/deleteUtente")
    public ResponseEntity<Object> deleteUtente(@RequestParam Long id){
        log.info(CONTROLLER_TAG + "POST deleteUtente");
        utenteService.deleteUtente(id);
        return ResponseEntity.ok(null);
    }
}
