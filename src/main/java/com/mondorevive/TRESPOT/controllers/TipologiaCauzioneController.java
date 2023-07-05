package com.mondorevive.TRESPOT.controllers;

import com.mondorevive.TRESPOT.requests.CreaNuovaTipologiaCauzioneRequest;
import com.mondorevive.TRESPOT.requests.UpdateTipologiaCauzioneRequest;
import com.mondorevive.TRESPOT.tipologiaCauzione.TipologiaCauzioneService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@RestController
@CrossOrigin(origins = "*", maxAge = 3600,exposedHeaders = {"Content-Disposition"})
@Validated
@RequestMapping("/api/tipologiaCauzione")
@RequiredArgsConstructor
public class TipologiaCauzioneController {
    private final String CONTROLLER_TAG = "TIPOLOGIA_CAUZIONE_CONTROLLER - ";
    private final TipologiaCauzioneService tipologiaCauzioneService;

    @GetMapping("/tipologieCauzione")
    public ResponseEntity<Object>getAll(){
        log.info(CONTROLLER_TAG + "GET tipologieCauzione");
        return ResponseEntity.ok(tipologiaCauzioneService.getAll());
    }

    @GetMapping("/tipologieCauzioneSelect")
    public ResponseEntity<Object>getAllSelect(){
        log.info(CONTROLLER_TAG + "GET tipologieCauzioneSelect");
        return ResponseEntity.ok(tipologiaCauzioneService.getAllSelect());
    }

    @GetMapping("/tipologiaCauzioneById")
    public ResponseEntity<Object>getAll(@RequestParam Long id){
        log.info(CONTROLLER_TAG + "GET tipologiaCauzioneById");
        return ResponseEntity.ok(tipologiaCauzioneService.getDettaglioById(id));
    }

    @GetMapping("/getTypeList")
    public ResponseEntity<Object>getAllTypes(){
        log.info(CONTROLLER_TAG + "GET getTypeList");
        return ResponseEntity.ok(tipologiaCauzioneService.getTypeList());
    }

    @GetMapping("/fileAllegato")
    public ResponseEntity<byte[]>getFileAllegato(Long id){
        log.info(CONTROLLER_TAG + "GET fileAllegato");
        return tipologiaCauzioneService.getFileAllegato(id);
    }

    @GetMapping("/indice")
    public ResponseEntity<Object>getIndice(){
        log.info(CONTROLLER_TAG + "GET getIndice");
        return ResponseEntity.ok(tipologiaCauzioneService.getIndice());
    }
    @GetMapping("/copertina")
    public ResponseEntity<byte[]>getCopertina(@RequestParam String codice){
        log.info(CONTROLLER_TAG + "GET copertina");
        return tipologiaCauzioneService.getCopertina(codice.replace("@", "#"));
    }
    @GetMapping("/infoPortale")
    public ResponseEntity<Object>getInfoPortale(@RequestParam String codice){
        log.info(CONTROLLER_TAG + "GET infoPortale");
        return ResponseEntity.ok(tipologiaCauzioneService.getInfoPortale(codice.replace("@", "#")));
    }
    @PostMapping("/nuovaTipologiaCauzione")
    public ResponseEntity<Object>salvaNuovaTipologiaCauzione(@Valid @RequestBody CreaNuovaTipologiaCauzioneRequest request){
        log.info(CONTROLLER_TAG + "POST nuovaTipologiaCauzione");
        return ResponseEntity.ok(tipologiaCauzioneService.creaNuovaTipologiaCauzione(request));
    }

    @PostMapping("/updateTipologiaCauzione")
    public ResponseEntity<Object>updateTipologiaCauzione(@Valid @RequestPart("dati")UpdateTipologiaCauzioneRequest request,
                                                         @RequestPart(name = "files", required = false) MultipartFile[] files) throws IOException {
        log.info(CONTROLLER_TAG + "POST updateTipologiaCauzione");
        tipologiaCauzioneService.updateTipologiaCauzione(request, files);
        return ResponseEntity.ok(null);
    }

    @PostMapping("/deleteTipologiaCauzione")
    public ResponseEntity<Object>deleteById(@RequestParam Long id){
        log.info(CONTROLLER_TAG + "POST deleteTipologiaCauzione");
        tipologiaCauzioneService.deleteTipologiaCauzione(id);
        return ResponseEntity.ok(null);
    }

    @PostMapping("/deleteFile")
    public ResponseEntity<Object>deleteFile(@RequestParam Long id){
        log.info(CONTROLLER_TAG + "POST deleteFile");
        tipologiaCauzioneService.deleteFileAllegato(id);
        return ResponseEntity.ok(null);
    }
}
