package com.mondorevive.TRESPOT.controllers;

import com.mondorevive.TRESPOT.requests.LoginRequest;
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
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final String CONTROLLER_TAG = "AUTH_CONTROLLER - ";
    private final UtenteService utenteService;

    @PostMapping("/login")
    public ResponseEntity<Object> login(@Valid @RequestBody LoginRequest loginRequest)
    {
        log.info(CONTROLLER_TAG + "LOGIN " + loginRequest);
        return ResponseEntity.ok(utenteService.login(loginRequest));
    }
}
