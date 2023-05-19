package com.mondorevive.TRESPOT.controllers;

import com.mondorevive.TRESPOT.categoriaCauzione.CategoriaCauzioneService;
import com.mondorevive.TRESPOT.requests.CreaNuovaCategoriaCauzioneRequest;
import com.mondorevive.TRESPOT.requests.UpdateCategoriaCauzioneRequest;
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
@RequestMapping("/api/categoriaCauzione")
@RequiredArgsConstructor
public class CategoriaCauzioneController {
    private final String CONTROLLER_TAG = "CATEGORIE_CAUZIONE_CONTROLLER - ";
    private final CategoriaCauzioneService categoriaCauzioneService;

    @GetMapping("/categorieCauzione")
    public ResponseEntity<Object>getCategorieCauzione(){
        log.info(CONTROLLER_TAG + "GET categorieCauzione");
        return ResponseEntity.ok(categoriaCauzioneService.getAll());
    }
    @GetMapping("/categorieCauzioneSelect")
    public ResponseEntity<Object>getCategorieCauzioneSelect(){
        log.info(CONTROLLER_TAG + "GET categorieCauzioneSelect");
        return ResponseEntity.ok(categoriaCauzioneService.getAllSelect());
    }
    @GetMapping("/categoriaCauzioneById")
    public ResponseEntity<Object>getCategorieCauzioneSelect(@RequestParam Long id){
        log.info(CONTROLLER_TAG + "GET categoriaCauzioneById");
        return ResponseEntity.ok(categoriaCauzioneService.getDettaglioById(id));
    }
    @PostMapping("/nuovaCategoriaCauzione")
    public ResponseEntity<Object>creaNuovaCategoriaCauzione(@Valid @RequestBody CreaNuovaCategoriaCauzioneRequest request){
        log.info(CONTROLLER_TAG + "POST nuovaCategoriaCauzione");
        return ResponseEntity.ok(categoriaCauzioneService.creaNuovaCategoriaCauzione(request));
    }
    @PostMapping("/updateCategoriaCauzione")
    public ResponseEntity<Object>updateCategoriaCauzione(@Valid @RequestBody UpdateCategoriaCauzioneRequest request){
        log.info(CONTROLLER_TAG + "POST updateCategoriaCauzione");
        categoriaCauzioneService.updateCategoriaCauzione(request);
        return ResponseEntity.ok(null);
    }
    @PostMapping("/deleteCategoriaCauzione")
    public ResponseEntity<Object>deleteCategoriaCauzione(@RequestParam Long id){
        log.info(CONTROLLER_TAG + "POST deleteCategoriaCauzione");
        categoriaCauzioneService.deleteCategoriaCauzione(id);
        return ResponseEntity.ok(null);
    }
}
