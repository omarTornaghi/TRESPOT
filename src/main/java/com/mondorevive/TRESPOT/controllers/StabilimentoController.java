package com.mondorevive.TRESPOT.controllers;

import com.mondorevive.TRESPOT.requests.CreaNuovoStabilimentoRequest;
import com.mondorevive.TRESPOT.requests.UpdateStabilimentoRequest;
import com.mondorevive.TRESPOT.stabilimento.StabilimentoService;
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
@RequestMapping("/api/stabilimento")
@RequiredArgsConstructor
public class StabilimentoController {
    private final String CONTROLLER_TAG = "STABILIMENTO_CONTROLLER - ";

    private final StabilimentoService stabilimentoService;

    @GetMapping("/stabilimenti")
    public ResponseEntity<Object> getAll(){
        log.info(CONTROLLER_TAG + "GET Stabilimenti");
        return ResponseEntity.ok(stabilimentoService.getAll());
    }

    @GetMapping("/stabilimentiSelect")
    public ResponseEntity<Object> getAllStabilimentiSelect(){
        log.info(CONTROLLER_TAG + "GET stabilimentiSelect");
        return ResponseEntity.ok(stabilimentoService.getAllSelectable());
    }

    @GetMapping("/stabilimentoById")
    public ResponseEntity<Object> getById(@RequestParam Long id){
        log.info(CONTROLLER_TAG + "GET stabilimentoById");
        return ResponseEntity.ok(stabilimentoService.getById(id));
    }

    @GetMapping("/sistemiEsterni")
    public ResponseEntity<Object> getSistemiEsterni(){
        log.info(CONTROLLER_TAG + "GET sistemiEsterni");
        return ResponseEntity.ok(stabilimentoService.getAllSistemiEsterni());
    }

    @PostMapping("/nuovoStabilimento")
    public ResponseEntity<Object> creaNuovoStabilimento(@Valid @RequestBody CreaNuovoStabilimentoRequest request){
        log.info(CONTROLLER_TAG + "POST nuovoStabilimento");
        return ResponseEntity.ok(stabilimentoService.creaNuovoStabilimento(request));
    }

    @PostMapping("/updateStabilimento")
    public ResponseEntity<Object> updateStabilimento(@Valid @RequestBody UpdateStabilimentoRequest request){
        log.info(CONTROLLER_TAG + "POST updateStabilimento");
        stabilimentoService.updateStabilimento(request);
        return ResponseEntity.ok(null);
    }

    @PostMapping("/deleteById")
    public ResponseEntity<Object> deleteStabilimento(@RequestParam Long id){
        log.info(CONTROLLER_TAG + "POST deleteStabilimento");
        stabilimentoService.deleteStabilimento(id);
        return ResponseEntity.ok(null);
    }
}
