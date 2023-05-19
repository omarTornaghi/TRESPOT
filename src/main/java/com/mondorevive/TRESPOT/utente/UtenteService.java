package com.mondorevive.TRESPOT.utente;

import com.mondorevive.TRESPOT.magazzino.MagazzinoService;
import com.mondorevive.TRESPOT.requests.*;
import com.mondorevive.TRESPOT.responses.DettaglioUtenteResponse;
import com.mondorevive.TRESPOT.responses.GetAllUtentiResponse;
import com.mondorevive.TRESPOT.responses.JwtResponse;
import com.mondorevive.TRESPOT.responses.ProfiloUtenteResponse;
import com.mondorevive.TRESPOT.responses.standardSelect.EntitySelect;
import com.mondorevive.TRESPOT.stabilimento.Stabilimento;
import com.mondorevive.TRESPOT.utente.ruolo.Ruolo;
import com.mondorevive.TRESPOT.utente.ruolo.RuoloService;
import com.mondorevive.TRESPOT.utente.utenteStabilimento.UtenteStabilimento;
import com.mondorevive.TRESPOT.utente.utenteStabilimento.UtenteStabilimentoService;
import com.mondorevive.TRESPOT.utils.JwtUtils;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class UtenteService{
    private final UtenteRepository utenteRepository;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder encoder;
    private final RuoloService ruoloService;
    private final UtenteStabilimentoService utenteStabilimentoService;
    private final MagazzinoService magazzinoService;

    private Utente getUserByIdWithRuolo(Long id) {
        return utenteRepository.getUtenteWithRuolo(id).orElseThrow(() -> new EntityNotFoundException(id.toString()));
    }
    public JwtResponse login(LoginRequest request) {
        Utente userByUsername = findUserByUsername(request.getUsername());

        //Verifico la password
        boolean passwordCorretta = encoder.matches(request.getPassword(), userByUsername.getPassword());
        if(!passwordCorretta) throw new AuthenticationServiceException("Credenziali errate");

        String jwt = jwtUtils.generateJwtToken(userByUsername.getUsername());

        return new JwtResponse(jwt, userByUsername.getId(), userByUsername.getUsername(), userByUsername.getRuolo().getId(),userByUsername.getRuolo().getCodice(),
                userByUsername.getMagazzino().getId(),
                userByUsername.getMagazzino().getDescrizione(),
                userByUsername.getNome(), userByUsername.getCognome());
    }

    public void salva(Utente utente) {
        utenteRepository.save(utente);
    }

    @Transactional(readOnly = true)
    public List<GetAllUtentiResponse>getAll(){
        return utenteRepository.getAll().stream().map(x -> new GetAllUtentiResponse(x.getId(),x.getUsername(),x.getNome(),x.getCognome(),x.getRuolo().getCodice(),x.getAttivo())).collect(Collectors.toList());
    }

    public List<EntitySelect> getAllSelect() {
        return utenteRepository.getAll().stream().map(Utente::convertToSelectableEntity).collect(Collectors.toList());
    }
    @Transactional(readOnly = true)
    public Utente findUserByUsername(String username) {
        return utenteRepository.findUserByUsername(username).orElseThrow(() -> new EntityNotFoundException(username));
    }

    @Transactional(readOnly = true)
    public boolean utenteExistsByUsername(String username) {
        return utenteRepository.existsByUsernameAndAttivo(username, true);
    }

    public void cambiaPassword(String token, CambiaPasswordRequest req) {
        utenteRepository.updatePassword(jwtUtils.getUsername(token), encoder.encode(req.getPassword()));
    }

    @Transactional(readOnly = true)
    public List<EntitySelect> getAllRuoli() {
        return ruoloService.getAll().stream().map(Ruolo::convertToSelectableEntity).collect(Collectors.toList());
    }

    public Long creaNuovoUtente(CreaNuovoUtenteRequest request) {
        Ruolo ruolo = ruoloService.findById(request.getIdRuolo());
        Utente utente = new Utente(request.getUsername(), encoder.encode(request.getPassword()), request.getNome(),request.getCognome(), ruolo,magazzinoService.getById(request.getIdMagazzino()) );
        salva(utente);
        utenteStabilimentoService.creaAssociazioni(request.getIdStabilimentiList(),utente);
        return utente.getId();
    }

    @Transactional(readOnly = true)
    public DettaglioUtenteResponse getDettagliUtente(Long id) {
        DettaglioUtenteResponse dettaglioUtenteResponse = new DettaglioUtenteResponse();
        Utente utente = getUserByIdWithRuolo(id);
        dettaglioUtenteResponse.setId(utente.getId());
        dettaglioUtenteResponse.setUsername(utente.getUsername());
        dettaglioUtenteResponse.setNome(utente.getNome());
        dettaglioUtenteResponse.setCognome(utente.getCognome());
        dettaglioUtenteResponse.setIdRuolo(utente.getRuolo().getId());
        dettaglioUtenteResponse.setCodiceRuolo(utente.getRuolo().getCodice());
        dettaglioUtenteResponse.setIdMagazzino(utente.getMagazzino() != null ? utente.getMagazzino().getId() : null);
        dettaglioUtenteResponse.setIdStabilimentiList(utenteStabilimentoService.getStabilimentiIdByUtenteId(id));
        return dettaglioUtenteResponse;
    }

    public void updateUtente(UpdateUtenteRequest request) {
        if (request.getPassword() != null && !StringUtils.isBlank(request.getPassword()))
            utenteRepository.aggiornaUtenteEPassword(request.getId(), request.getUsername(),
                    encoder.encode(request.getPassword()), request.getNome(), request.getCognome(), request.getIdRuolo(), request.getIdMagazzino());
        else
            utenteRepository.aggiornaUtente(request.getId(), request.getUsername(), request.getNome(), request.getCognome(),request.getIdRuolo(), request.getIdMagazzino());
        utenteStabilimentoService.updateAssociazioniUtenteStabilimento(request.getIdStabilimentiList(),getUserByIdWithRuolo(request.getId()));
    }

    public void deleteUtente(Long id){
        Utente utente = getUserByIdWithRuolo(id);
        String usernameFittizio = MessageFormat.format("{0} CANCELLATO - {1}", utente.getUsername(),
                LocalDateTime.now().toString());
        utenteRepository.nascondiUtente(id, usernameFittizio);
        utenteStabilimentoService.deleteStabilimentiByUtenteId(id);

    }

    @Transactional(readOnly = true)
    public List<Stabilimento> getStabilimentiUtente(String username) {
        return utenteStabilimentoService.getByUsername(username).stream().map(UtenteStabilimento::getStabilimento).collect(Collectors.toList());
    }
    @Transactional(readOnly = true)
    public ProfiloUtenteResponse getProfiloUtente(String username) {
        Utente utente = findUserByUsername(username);
        return new ProfiloUtenteResponse(utente.getId(),utente.getUsername(),utente.getNome(),utente.getCognome());
    }

    public void updateProfilo(UpdateProfiloRequest request) {
        utenteRepository.aggiornaProfilo(request.getId(),request.getUsername(),request.getNome(),request.getCognome());
        if(StringUtils.isNotBlank(request.getPassword()))utenteRepository.aggiornaPassword(request.getId(), encoder.encode(request.getPassword()));

    }

    public void importaUtente(Utente utente) {
        utenteRepository.save(utente);
    }
}
