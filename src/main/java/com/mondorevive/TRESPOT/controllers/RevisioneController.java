package com.mondorevive.TRESPOT.controllers;

import com.mondorevive.TRESPOT.pianoRevisione.revisione.RevisioneService;
import com.mondorevive.TRESPOT.requests.NuovaRevisioneRequest;
import com.mondorevive.TRESPOT.requests.PaginationRequest;
import com.mondorevive.TRESPOT.requests.UpdateRevisioneRequest;
import com.mondorevive.TRESPOT.utils.JwtUtils;
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
@RequestMapping("/api/revisione")
@RequiredArgsConstructor
public class RevisioneController {
    private final String CONTROLLER_TAG = "REVISIONE_CONTROLLER - ";
    private final RevisioneService revisioneService;
    private final JwtUtils jwtUtils;

    @GetMapping("/revisioneById")
    public ResponseEntity<Object> getRevisioneById(@RequestParam Long id){
        log.info(CONTROLLER_TAG + "GET revisioneById");
        return ResponseEntity.ok(revisioneService.getDettaglioById(id));
    }
    @PostMapping("/revisioni")
    public ResponseEntity<Object> getAllCauzioni(@Valid @RequestBody PaginationRequest request){
        log.info(CONTROLLER_TAG + "POST cauzioni");
        return ResponseEntity.ok(revisioneService.getAll(request));
    }
    @PostMapping("/nuovaRevisione")
    public ResponseEntity<Object> creaNuovaRevisione(@RequestHeader("Authorization") String token, @Valid @RequestBody NuovaRevisioneRequest request){
        log.info(CONTROLLER_TAG + "POST nuovaRevisione");
        revisioneService.creaNuovaRevisione(request, jwtUtils.getUsername(token));
        return ResponseEntity.ok(null);
    }

    @PostMapping("/updateRevisione")
    public ResponseEntity<Object>updateRevisione(@Valid @RequestBody UpdateRevisioneRequest request){
        log.info(CONTROLLER_TAG + "POST updateRevisione");
        revisioneService.updateRevisione(request);
        return ResponseEntity.ok(null);
    }

    @PostMapping("deleteRevisioneById")
    public ResponseEntity<Object> deleteRevisioneById(@RequestParam Long id){
        log.info(CONTROLLER_TAG + "POST deleteRevisioneById");
        revisioneService.deleteRevisioneById(id);
        return ResponseEntity.ok(null);
    }
}
