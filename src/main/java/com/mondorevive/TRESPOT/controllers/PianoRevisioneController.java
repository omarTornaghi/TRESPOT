package com.mondorevive.TRESPOT.controllers;

import com.mondorevive.TRESPOT.pianoRevisione.PianoRevisioneService;
import com.mondorevive.TRESPOT.requests.CreaNuovoPianoRevisioneRequest;
import com.mondorevive.TRESPOT.requests.UpdatePianoRevisioneRequest;
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
@RequestMapping("/api/pianoRevisione")
@RequiredArgsConstructor
public class PianoRevisioneController {
    private final String CONTROLLER_TAG = "PIANO_REVISIONE_CONTROLLER - ";
    private final PianoRevisioneService pianoRevisioneService;

    @GetMapping("/pianiRevisione")
    public ResponseEntity<Object>getPianiRevisione(){
        log.info(CONTROLLER_TAG + "GET pianiRevisione");
        return ResponseEntity.ok(pianoRevisioneService.getAll());
    }

    @GetMapping("/pianiRevisioneSelect")
    public ResponseEntity<Object>getPianiRevisioneSelect(){
        log.info(CONTROLLER_TAG + "GET pianiRevisioneSelect");
        return ResponseEntity.ok(pianoRevisioneService.getAllSelect());
    }
    @GetMapping("/pianoRevisioneById")
    public ResponseEntity<Object>getDettaglioPianoRevisione(@RequestParam Long id){
        log.info(CONTROLLER_TAG + "GET pianoRevisioneById");
        return ResponseEntity.ok(pianoRevisioneService.getDettaglioById(id));
    }

    @PostMapping("/nuovoPianoRevisione")
    public ResponseEntity<Object>salvaNuovoPianoRevisione(@Valid @RequestBody CreaNuovoPianoRevisioneRequest request){
        log.info(CONTROLLER_TAG + "POST nuovoPianoRevisione");
        return ResponseEntity.ok(pianoRevisioneService.salvaNuovoPianoRevisione(request));
    }

    @PostMapping("/updatePianoRevisione")
    public ResponseEntity<Object>salvaNuovoPianoRevisione(@Valid @RequestBody UpdatePianoRevisioneRequest request){
        log.info(CONTROLLER_TAG + "POST updatePianoRevisione");
        pianoRevisioneService.updatePianoRevisione(request);
        return ResponseEntity.ok(null);
    }

    @PostMapping("/deletePianoRevisione")
    public ResponseEntity<Object>salvaNuovoPianoRevisione(@RequestParam Long id){
        log.info(CONTROLLER_TAG + "POST deletePianoRevisione");
        pianoRevisioneService.deleteById(id);
        return ResponseEntity.ok(null);
    }
}
