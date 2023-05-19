package com.mondorevive.TRESPOT.controllers;

import com.mondorevive.TRESPOT.cauzione.CauzioneService;
import com.mondorevive.TRESPOT.requests.*;
import com.mondorevive.TRESPOT.responses.ValidateResponse;
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
@RequestMapping("/api/cauzione")
@RequiredArgsConstructor
public class CauzioneController {
    private final String CONTROLLER_TAG = "CAUZIONE_CONTROLLER - ";
    private final CauzioneService cauzioneService;
    private final JwtUtils jwtUtils;

    @GetMapping("/statiCauzioneSelect")
    public ResponseEntity<Object>getAllStatiCauzioniSelect(){
        log.info(CONTROLLER_TAG + "GET statiCauzioneSelect");
        return ResponseEntity.ok(cauzioneService.getAllStatiCauzioniSelect());
    }

    @GetMapping("/cauzioneById")
    public ResponseEntity<Object>getCauzioneById(@RequestParam Long id){
        log.info(CONTROLLER_TAG + "GET cauzioneById");
        return ResponseEntity.ok(cauzioneService.getDettaglioCauzioneResponse(id));
    }

    @GetMapping("/storicoCauzione")
    public ResponseEntity<Object>getStoricoCauzione(@RequestParam Long id, @RequestParam String dataInizio, @RequestParam String dataFine){
        log.info(CONTROLLER_TAG + "GET storicoCauzione");
        return ResponseEntity.ok(cauzioneService.getStoricoCauzione(id,dataInizio,dataFine));
    }

    @GetMapping("/cauzioneBobineAssociate")
    public ResponseEntity<Object>getCauzioneWithBobineAssociate(@RequestParam String text){
        log.info(CONTROLLER_TAG + "GET cauzioneBobineAssociate");
        return ResponseEntity.ok(cauzioneService.getCauzioneWithBobineAssociate(text));
    }

    @GetMapping("/cauzioneByText")
    public ResponseEntity<Object>getSezioneByText(@RequestParam String text){
        log.info(CONTROLLER_TAG + "GET cauzioneByText");
        return ResponseEntity.ok(cauzioneService.getCauzioneByText(text));
    }

    @GetMapping("/getInfoCauzione")
    public ResponseEntity<Object>getInfoCauzione(@RequestHeader("Authorization")String token, @RequestParam String text, @RequestParam String operazione, @RequestParam(required = false)Long idMagazzino){
        log.info(CONTROLLER_TAG + "GET getInfoCauzione");
        return ResponseEntity.ok(cauzioneService.getInfoCauzione(text,operazione, jwtUtils.getUsername(token),idMagazzino));
    }

    @GetMapping("/infoBobina")
    public ResponseEntity<Object>getInfoBobina(@RequestHeader("Authorization")String token, @RequestParam String text){
        log.info(CONTROLLER_TAG + "GET infoBobina");
        return ResponseEntity.ok(cauzioneService.getInfoBobina(text, jwtUtils.getUsername(token)));
    }

    @GetMapping("/getTagInfo")
    public ResponseEntity<Object>getTagInfo(@RequestParam String epcTag){
        log.info(CONTROLLER_TAG + "GET getTagInfo");
        log.info("epcTag = " + epcTag);
        return ResponseEntity.ok(cauzioneService.getTagInfo(epcTag));
    }
    @PostMapping("/cauzioni")
    public ResponseEntity<Object>getAllCauzioni(@Valid @RequestBody PaginationRequest request){
        log.info(CONTROLLER_TAG + "POST cauzioni");
        return ResponseEntity.ok(cauzioneService.getAll(request));
    }

    @PostMapping("/nuovaCauzione")
    public ResponseEntity<Object>creaNuovaCauzione(@RequestHeader("Authorization")String token,@Valid @RequestBody CreaNuovaCauzioneRequest request){
        log.info(CONTROLLER_TAG + "POST nuovaCauzione");
        return ResponseEntity.ok(cauzioneService.creaNuovaCauzione(request,jwtUtils.getUsername(token)));
    }
    @PostMapping("/updateCauzione")
    public ResponseEntity<Object>updateCauzione(@RequestHeader("Authorization")String token, @Valid @RequestBody UpdateCauzioneRequest request){
        log.info(CONTROLLER_TAG + "POST updateCauzione");
        cauzioneService.updateCauzione(request, jwtUtils.getUsername(token));
        return ResponseEntity.ok(null);
    }
    @PostMapping("/deleteCauzione")
    public ResponseEntity<Object>deleteCauzione(@RequestParam Long id){
        log.info(CONTROLLER_TAG + "POST deleteCauzione");
        cauzioneService.deleteCauzioneById(id);
        return ResponseEntity.ok(null);
    }
    @PostMapping("/associaBobine")
    public ResponseEntity<Object>associaBobine(@RequestHeader("Authorization")String token,@Valid @RequestBody AssociaBobineRequest request){
        log.info(CONTROLLER_TAG + "POST associaBobine");
        cauzioneService.associaBobine(request,jwtUtils.getUsername(token));
        return ResponseEntity.ok(null);
    }
    @PostMapping("/mettiInManutenzione")
    public ResponseEntity<Object>mettiInManutenzione(@RequestHeader("Authorization")String token, @Valid @RequestBody List<MettiInManutenzioneRequest> request){
        log.info(CONTROLLER_TAG + "POST mettiInManutenzione");
        cauzioneService.mettiInManutenzione(request,jwtUtils.getUsername(token));
        return ResponseEntity.ok(null);
    }

    @PostMapping("/spostaAMagazzino")
    public ResponseEntity<Object>spostaAMagazzino(@RequestHeader("Authorization")String token, @Valid @RequestBody SpostamentoRequest request){
        log.info(CONTROLLER_TAG + "POST spostaAMagazzino");
        cauzioneService.spostaAMagazzino(request, jwtUtils.getUsername(token));
        return ResponseEntity.ok(null);
    }

    @PostMapping("/mandaACliente")
    public ResponseEntity<Object>mandaACliente(@RequestHeader("Authorization")String token, @Valid @RequestBody SpostamentoRequest request){
        log.info(CONTROLLER_TAG + "POST spostaAMagazzino");
        cauzioneService.mandaACliente(request, jwtUtils.getUsername(token));
        return ResponseEntity.ok(null);
    }

    @PostMapping("/scaricaELibera")
    public ResponseEntity<Object>scaricaELibera(@RequestHeader("Authorization")String token, @Valid @RequestBody List<ScaricaRequest> request){
        log.info(CONTROLLER_TAG + "POST scaricaELibera");
        cauzioneService.scaricaELibera(request,jwtUtils.getUsername(token));
        return ResponseEntity.ok(null);
    }

    @PostMapping("/scarica")
    public ResponseEntity<Object>scarica(@RequestHeader("Authorization")String token, @Valid @RequestBody List<ScaricaRequest> request){
        log.info(CONTROLLER_TAG + "POST scaricaELibera");
        cauzioneService.scarica(request,jwtUtils.getUsername(token));
        return ResponseEntity.ok(null);
    }

    //EP Silvano
    @PostMapping("/validateEpcTag")
    public ResponseEntity<Object> validateEpcTag(@RequestBody ValidateEpcTagRequest response){
        log.info(CONTROLLER_TAG + "POST validateEpcTag");
        return ResponseEntity.ok(cauzioneService.validateEpcTag(response));
    }
}
