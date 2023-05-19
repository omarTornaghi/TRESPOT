package com.mondorevive.TRESPOT.controllers;

import com.mondorevive.TRESPOT.cliente.ClienteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@CrossOrigin(origins = "*", maxAge = 3600,exposedHeaders = {"Content-Disposition"})
@Validated
@RequestMapping("/api/cliente")
@RequiredArgsConstructor
public class ClienteController {
    private final String CONTROLLER_TAG = "CLIENTE_CONTROLLER - ";
    private final ClienteService clienteService;

    @GetMapping("/clientiSelect")
    public ResponseEntity<Object> getClientiSelect(){
        log.info(CONTROLLER_TAG + "GET clientiSelect");
        return ResponseEntity.ok(clienteService.getClientiSelect());
    }
}
