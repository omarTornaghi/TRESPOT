package com.mondorevive.TRESPOT.initializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mondorevive.TRESPOT.categoriaCauzione.CategoriaCauzione;
import com.mondorevive.TRESPOT.categoriaCauzione.CategoriaCauzioneService;
import com.mondorevive.TRESPOT.cauzione.Cauzione;
import com.mondorevive.TRESPOT.cauzione.CauzioneService;
import com.mondorevive.TRESPOT.cauzione.statoCauzione.StatoCauzione;
import com.mondorevive.TRESPOT.cauzione.statoCauzione.StatoCauzioneService;
import com.mondorevive.TRESPOT.cauzione.statoCauzione.TipoStatoCauzione;
import com.mondorevive.TRESPOT.cauzione.storicoCauzione.StoricoCauzione;
import com.mondorevive.TRESPOT.cauzione.storicoCauzione.StoricoCauzioneService;
import com.mondorevive.TRESPOT.cauzione.storicoCauzione.operazione.Operazione;
import com.mondorevive.TRESPOT.cauzione.storicoCauzione.operazione.OperazioneService;
import com.mondorevive.TRESPOT.cauzione.storicoCauzione.operazione.TipoOperazione;
import com.mondorevive.TRESPOT.magazzino.Magazzino;
import com.mondorevive.TRESPOT.magazzino.MagazzinoService;
import com.mondorevive.TRESPOT.pianoRevisione.revisione.Revisione;
import com.mondorevive.TRESPOT.pianoRevisione.revisione.RevisioneService;
import com.mondorevive.TRESPOT.stabilimento.Stabilimento;
import com.mondorevive.TRESPOT.stabilimento.StabilimentoService;
import com.mondorevive.TRESPOT.stabilimento.sistemaEsterno.SistemaEsterno;
import com.mondorevive.TRESPOT.stabilimento.sistemaEsterno.SistemaEsternoService;
import com.mondorevive.TRESPOT.stabilimento.sistemaEsterno.TipoSistemaEsterno;
import com.mondorevive.TRESPOT.tipologiaCauzione.TipologiaCauzione;
import com.mondorevive.TRESPOT.tipologiaCauzione.TipologiaCauzioneService;
import com.mondorevive.TRESPOT.utente.Utente;
import com.mondorevive.TRESPOT.utente.UtenteService;
import com.mondorevive.TRESPOT.utente.ruolo.Ruolo;
import com.mondorevive.TRESPOT.utente.ruolo.RuoloService;
import com.mondorevive.TRESPOT.utente.ruolo.TipoRuolo;
import com.mondorevive.TRESPOT.utente.utenteStabilimento.UtenteStabilimento;
import com.mondorevive.TRESPOT.utente.utenteStabilimento.UtenteStabilimentoService;
import com.mondorevive.TRESPOT.utils.DateUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceUnit;
import lombok.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Component
@RequiredArgsConstructor
public class Initializer implements ApplicationRunner {
    private final SistemaEsternoService sistemaEsternoService;
    private final RuoloService ruoloService;
    private final UtenteService utenteService;
    private final StatoCauzioneService statoCauzioneService;
    private final CauzioneService cauzioneService;
    private final TipologiaCauzioneService tipologiaCauzioneService;
    private final MagazzinoService magazzinoService;
    private final OperazioneService operazioneService;
    private final StabilimentoService stabilimentoService;
    private final CategoriaCauzioneService categoriaCauzioneService;
    private final StoricoCauzioneService storicoCauzioneService;
    private final RevisioneService revisioneService;
    private final UtenteStabilimentoService utenteStabilimentoService;
    private final PasswordEncoder encoder;
    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    private static class StabilimentoJson {
        private String codice;
        private String descrizione;
        private String codiceSistemaEsterno;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    private static class MagazzinoJson {
        private String descrizione;
        private String codiceStabilimento;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    private static class UtenteJson {
        private String nome;
        private String cognome;
        private String username;
        private String password;
        private String ruolo;
        private String magazzino;
        private String[] stabilimenti;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    private static class CategorieJson {
        private String codice;
        private String descrizione;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    private static class TipoTrespoloJson {
        private String codiceVecchio; //Utilizzato solo per importazione
        private String codice;
        private String descrizione;
        private String numeroMaxBobine;
        private String numeroMaxKg;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    private static class TrespoloJson {
        private String codiceVecchio; //Utilizzato solo per importazione
        private String epcTag;
        private String matricola;
        private String codiceVecchioTipologia;
        private String deposito;
        private String statoTrespolo;
        private String dataAcquisto;
        private String dataUltimaRevisione;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    private static class RevisioneJson{
        private String codiceTrespolo;
        private String dataRevisione;
        private String note;
        private String username;
        private String presenzaTarga;
        private String membrature;
        private String membratureNote;
        private String saldature;
        private String cerniera;
        private String intInforcatura;
        private String intCollisione;
        private String altriControlli;
        private String stabilita;
        private String verificaTag;
        private String conformitaDt;
        private String flagOk;
    }

    @Transactional
    @Override
    public void run(ApplicationArguments args) throws Exception {
        if(devoCaricare(args, "inizializzaSoftware")){
            Arrays.stream(TipoSistemaEsterno.values()).forEach(sistemaEsternoService::crea);
            Arrays.stream(TipoRuolo.values()).forEach(ruoloService::crea);
            Arrays.stream(TipoStatoCauzione.values()).forEach(statoCauzioneService::crea);
            Arrays.stream(TipoOperazione.values()).forEach(operazioneService::crea);
            importaDatiIniziali();
        }
    }

    private void importaDatiIniziali() throws IOException {
        creaStabilimentiInterni();
        creaMagazziniInterni();
        Map<String, Utente> utenteMap = creaUtenti();
        creaCategorie();
        Map<String, TipologiaCauzione> stringTipologiaCauzioneMap = creaTipiTrespolo();
        Map<String, Cauzione> stringCauzioneMap = creaTrespoli(stringTipologiaCauzioneMap, utenteMap);
        creaRevisioni(stringCauzioneMap,utenteMap);
        //EntityManager entityManager = entityManagerFactory.createEntityManager();
        //entityManager.getTransaction().begin();

    }
    private void creaStabilimentiInterni() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        StabilimentoJson[] stabilimentoJsonArray =
                mapper.readValue(StabilimentoJson.class.getClassLoader().getResource("static/importazione" +
                        "/stabilimenti.json"), StabilimentoJson[].class);
        Arrays.stream(stabilimentoJsonArray).toList().forEach(x -> stabilimentoService.importaStabilimento(new Stabilimento(x.codice,x.descrizione, sistemaEsternoService.getByCodice(x.codiceSistemaEsterno))));
    }

    private void creaMagazziniInterni() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        MagazzinoJson[] magazzinoJsons =
                mapper.readValue(MagazzinoJson.class.getClassLoader().getResource("static/importazione" +
                        "/magazzini.json"), MagazzinoJson[].class);
        Arrays.stream(magazzinoJsons).forEach(x -> magazzinoService.importaMagazzino(new Magazzino(x.descrizione,stabilimentoService.getByCodice(x.codiceStabilimento))));
    }

    private Map<String,Utente> creaUtenti() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        UtenteJson[] utenteJsons =
                mapper.readValue(UtenteJson.class.getClassLoader().getResource("static/importazione" +
                        "/utenti.json"), UtenteJson[].class);
        Map<String,Utente>utenteMap = new HashMap<>();
        for(UtenteJson x : utenteJsons){
            Utente utente = new Utente(x.username,encoder.encode(x.password),x.nome,x.cognome,ruoloService.findByName(TipoRuolo.valueOf(x.ruolo)),magazzinoService.getByDescrizione(x.magazzino));
            utenteService.importaUtente(utente);
            //Creo l'associazione con stabilimento di busto arsizio
            UtenteStabilimento utenteStabilimento = new UtenteStabilimento(utente,stabilimentoService.getByCodice("BA"));
            utenteStabilimentoService.importa(utenteStabilimento);
            utenteMap.put(x.username,utente);
        }
        return utenteMap;
    }

    private void creaCategorie() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        CategorieJson[] categorieJsons =
                mapper.readValue(CategorieJson.class.getClassLoader().getResource("static/importazione" +
                        "/categorie.json"), CategorieJson[].class);
        Arrays.stream(categorieJsons).forEach(x -> categoriaCauzioneService.importa(new CategoriaCauzione(x.codice,x.descrizione,null)));
    }

    private Map<String,TipologiaCauzione> creaTipiTrespolo() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        TipoTrespoloJson[] tipoTrespoloJsons =
                mapper.readValue(TipoTrespoloJson.class.getClassLoader().getResource("static/importazione" +
                        "/tipiTrespolo.json"), TipoTrespoloJson[].class);
        Map<String,TipologiaCauzione>map = new HashMap<>();
        for(TipoTrespoloJson x : tipoTrespoloJsons){
            TipologiaCauzione tipologiaCauzione = new TipologiaCauzione(x.getCodice(),x.getDescrizione(),!StringUtils.isBlank(x.numeroMaxBobine) ? Integer.valueOf(x.numeroMaxBobine) : null, !StringUtils.isBlank(x.numeroMaxKg) ? Integer.valueOf(x.numeroMaxKg) : null,categoriaCauzioneService.getByCodice("TRESPOLO"));
            map.put(x.codiceVecchio,tipologiaCauzioneService.importa(tipologiaCauzione));
        }
        return map;
    }

    private Map<String,Cauzione> creaTrespoli(Map<String,TipologiaCauzione>tipiCauzione, Map<String, Utente>utentiMap) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        TrespoloJson[] trespoloJsons = mapper.readValue(TrespoloJson.class.getClassLoader().getResource("static/importazione" +
                "/trespoli.json"), TrespoloJson[].class);
        Map<String,Cauzione>map = new HashMap<>();
        Magazzino fuori = magazzinoService.getByDescrizione("FUORI");
        Magazzino bustoArsizio = magazzinoService.getByDescrizione("BA - BUSTO ARSIZIO");
        StatoCauzione statoCauzioneLibero = statoCauzioneService.getByTipo(TipoStatoCauzione.LIBERO);
        Operazione creazione = operazioneService.getByTipo(TipoOperazione.CREAZIONE);
        for(TrespoloJson trespoloJson : trespoloJsons){
            //Ottengo il tipo cauzione
            TipologiaCauzione tipologiaCauzione = tipiCauzione.get(trespoloJson.codiceVecchioTipologia);
            Magazzino magazzino = trespoloJson.getStatoTrespolo().equals("2") ?  fuori : bustoArsizio;
            //Posso fare così: se in sede lo metto a BUSTO ARSIZIO, altrimenti magazzino FUORI
            LocalDateTime timestampAcquisto = !trespoloJson.dataAcquisto.equals("NULL") ? LocalDate.parse(trespoloJson.dataAcquisto.split(" ")[0]).atStartOfDay() : LocalDate.of(1990,1,1).atStartOfDay();
            Cauzione cauzione = new Cauzione(trespoloJson.epcTag,trespoloJson.matricola,timestampAcquisto,tipologiaCauzione,magazzino,statoCauzioneLibero);
            cauzioneService.salva(cauzione);
            //Creo l'operazione di CREAZIONE
            storicoCauzioneService.importaOperazione(new StoricoCauzione(cauzione.getTimestampAcquisto(),cauzione,statoCauzioneLibero,magazzino,utentiMap.get("admin"),creazione,null));
            map.put(trespoloJson.codiceVecchio,cauzione);
        }
        return map;
    }

    private void creaRevisioni(Map<String, Cauzione> stringCauzioneMap, Map<String, Utente> utenteMap) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        RevisioneJson[] revisioneJsons = mapper.readValue(RevisioneJson.class.getClassLoader().getResource("static/importazione" +
                "/revisioni.json"), RevisioneJson[].class);
        Magazzino bustoArsizio = magazzinoService.getByDescrizione("BA - BUSTO ARSIZIO");
        StatoCauzione statoCauzioneInManutenzione = statoCauzioneService.getByTipo(TipoStatoCauzione.IN_MANUTENZIONE);
        Operazione operazioneRevisione = operazioneService.getByTipo(TipoOperazione.REVISIONE);
        for (RevisioneJson revisioneJson : revisioneJsons){
            //Creo l'oggetto revisione
            LocalDateTime dataRevisione = null;
            try{
                dataRevisione = LocalDate.parse(revisioneJson.dataRevisione.split(" ")[0]).atStartOfDay();
            }
            catch (Exception ignored){
            }
            if(dataRevisione == null) continue;
            Revisione revisione = new Revisione();
            revisione.setDataRevisione(dataRevisione);
            revisione.setConformitaTotale(revisioneJson.flagOk != null && revisioneJson.flagOk.equals("-1"));
            revisione.setTargaPresente(fromNumToStr(revisioneJson.presenzaTarga));
            revisione.setConformitaDisegnoTecnico(fromNumToStr(revisioneJson.conformitaDt));
            revisione.setInterventoMembrature(fromNumToStr(revisioneJson.membrature));
            revisione.setDescrizioneInterventoMembrature(revisioneJson.membratureNote);
            revisione.setInterventoSaldatura(fromNumToStr(revisioneJson.saldature));
            revisione.setCernieraBullonata(fromNumToStr(revisioneJson.cerniera));
            revisione.setCattivoUsoInforcatura(fromNumToStr(revisioneJson.intInforcatura));
            revisione.setCattivoUsoCollisione(fromNumToStr(revisioneJson.intCollisione));
            revisione.setAltroIntervento(revisioneJson.altriControlli);
            revisione.setStabilitaGlobale(fromNumToStr(revisioneJson.stabilita));
            revisione.setFunzionamentoRfid(fromNumToStr(revisioneJson.verificaTag));
            revisione.setUlterioriNote(revisioneJson.note);
            //Creo l'oggetto storico revisione
            Cauzione cauzione = stringCauzioneMap.get(revisioneJson.codiceTrespolo);
            Utente utente = utenteMap.get(revisioneJson.username);
            if(utente == null)utente = utenteMap.get("admin");
            StoricoCauzione storicoCauzione = new StoricoCauzione(dataRevisione,cauzione,statoCauzioneInManutenzione,bustoArsizio,utente,operazioneRevisione,revisione);
            storicoCauzioneService.importaOperazione(storicoCauzione);
            revisioneService.importaRevisione(revisione);
        }
    }

    private String fromNumToStr(String presenzaTarga) {
        if(presenzaTarga == null) return "SI";
        if(presenzaTarga.equals("-1")) return "SI"; else return "NO";
    }

    private boolean devoCaricare(ApplicationArguments args,String parametro){
        if(args.containsOption(parametro)){
            List<String> argsListcA = args.getOptionValues(parametro);
            return argsListcA != null && argsListcA.contains("true");
        }
        return false;
    }

}
