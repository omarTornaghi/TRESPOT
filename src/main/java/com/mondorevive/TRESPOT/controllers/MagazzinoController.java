package com.mondorevive.TRESPOT.controllers;

import com.mondorevive.TRESPOT.magazzino.MagazzinoService;
import com.mondorevive.TRESPOT.requests.CreaNuovoMagazzinoRequest;
import com.mondorevive.TRESPOT.requests.UpdateMagazzinoRequest;
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
@RequestMapping("/api/magazzino")
@RequiredArgsConstructor
public class MagazzinoController {
    private final String CONTROLLER_TAG = "MAGAZZINO_CONTROLLER - ";
    private final MagazzinoService magazzinoService;

    @GetMapping("/magazziniInterni")
    public ResponseEntity<Object>getMagazziniInterni(){
        log.info(CONTROLLER_TAG + "GET magazziniInterni");
        return ResponseEntity.ok(magazzinoService.getMagazziniInterni());
    }

    @GetMapping("/magazziniClienti")
    public ResponseEntity<Object>getMagazziniClienti(){
        log.info(CONTROLLER_TAG + "GET magazziniClienti");
        return ResponseEntity.ok(magazzinoService.getMagazziniClienti());
    }

    @GetMapping("/magazziniSelect")
    public ResponseEntity<Object>getMagazziniSelect(){
        log.info(CONTROLLER_TAG + "GET magazziniClienti");
        return ResponseEntity.ok(magazzinoService.getMagazziniSelect());
    }
    @GetMapping("/magazzini")
    public ResponseEntity<Object>getAll(){
        log.info(CONTROLLER_TAG + "GET magazzini");
        return ResponseEntity.ok(magazzinoService.getAll());
    }
    @GetMapping("/magazzinoById")
    public ResponseEntity<Object>getById(@RequestParam Long id){
        log.info(CONTROLLER_TAG + "GET magazzinoById");
        return ResponseEntity.ok(magazzinoService.getDettaglioMagazzino(id));
    }
    @GetMapping("/cauzioniByIdMagazzino")
    public ResponseEntity<Object>getCauzioniByIdMagazzino(@RequestParam Long id){
        log.info(CONTROLLER_TAG + "GET cauzioniByIdMagazzino");
        return ResponseEntity.ok(magazzinoService.getDettaglioCauzioniMagazzino(id));
    }
    @PostMapping("/nuovoMagazzino")
    public ResponseEntity<Object>creaNuovoMagazzino(@Valid @RequestBody CreaNuovoMagazzinoRequest creaNuovoMagazzinoRequest){
        log.info(CONTROLLER_TAG + "POST nuovoMagazzino");
        return ResponseEntity.ok(magazzinoService.creaNuovoMagazzino(creaNuovoMagazzinoRequest));
    }

    @PostMapping("/updateMagazzino")
    public ResponseEntity<Object>updateMagazzino(@Valid @RequestBody UpdateMagazzinoRequest request){
        log.info(CONTROLLER_TAG + "POST updateMagazzino");
        magazzinoService.updateMagazzino(request);
        return ResponseEntity.ok(null);
    }

    @PostMapping("/deleteMagazzino")
    public ResponseEntity<Object>deleteMagazzino(@RequestParam Long id){
        log.info(CONTROLLER_TAG + "POST deleteMagazzino");
        magazzinoService.deleteMagazzino(id);
        return ResponseEntity.ok(null);
    }
}
