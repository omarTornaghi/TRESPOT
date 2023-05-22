package com.mondorevive.TRESPOT.controllers;

import com.mondorevive.TRESPOT.requests.NuovoVarcoRequest;
import com.mondorevive.TRESPOT.requests.UpdateVarcoRequest;
import com.mondorevive.TRESPOT.varco.VarcoService;
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
@RequestMapping("/api/varco")
@RequiredArgsConstructor
public class VarcoController {
    private final String CONTROLLER_TAG = "VARCO_CONTROLLER - ";
    private final VarcoService varcoService;

    @GetMapping("/varchi")
    public ResponseEntity<Object>getAllVarchi(){
        log.info(CONTROLLER_TAG + "GET varchi");
        return ResponseEntity.ok(varcoService.getAll());
    }

    @GetMapping("/varcoById")
    public ResponseEntity<Object>getVarcoById(@RequestParam Long id){
        log.info(CONTROLLER_TAG + "GET varcoById");
        return ResponseEntity.ok(varcoService.getDetailsById(id));
    }


    @PostMapping("/nuovoVarco")
    public ResponseEntity<Object>nuovoVarco(@RequestBody @Valid NuovoVarcoRequest request){
        log.info(CONTROLLER_TAG + "POST nuovoVarco");
        return ResponseEntity.ok(varcoService.creaNuovoVarco(request));
    }

    @PostMapping("/updateVarco")
    public ResponseEntity<Object>updateVarco(@RequestBody @Valid UpdateVarcoRequest request){
        log.info(CONTROLLER_TAG + "POST updateVarco");
        varcoService.updateVarco(request);
        return ResponseEntity.ok(null);
    }
    //EP Silvano
    @PostMapping("/setGateState")
    public ResponseEntity<Object>setGateState(){
        log.info(CONTROLLER_TAG + "POST setGateState");
        return ResponseEntity.ok(true);
    }

    @PostMapping("/deleteVarco")
    public ResponseEntity<Object>deleteVarco(@RequestParam Long id){
        log.info(CONTROLLER_TAG + "POST deleteVarco");
        varcoService.deleteById(id);
        return ResponseEntity.ok(null);
    }
}
