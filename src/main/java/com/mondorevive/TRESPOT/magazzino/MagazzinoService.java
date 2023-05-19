package com.mondorevive.TRESPOT.magazzino;

import com.mondorevive.TRESPOT.cauzione.Cauzione;
import com.mondorevive.TRESPOT.cauzione.CauzioneService;
import com.mondorevive.TRESPOT.cliente.Cliente;
import com.mondorevive.TRESPOT.cliente.ClienteService;
import com.mondorevive.TRESPOT.exceptions.exceptions.InvalidRequestException;
import com.mondorevive.TRESPOT.pojo.UltimoCaricoCauzione;
import com.mondorevive.TRESPOT.requests.CreaNuovoMagazzinoRequest;
import com.mondorevive.TRESPOT.requests.UpdateMagazzinoRequest;
import com.mondorevive.TRESPOT.responses.DettaglioCauzioniMagazzinoResponse;
import com.mondorevive.TRESPOT.responses.DettaglioMagazzinoResponse;
import com.mondorevive.TRESPOT.responses.GetAllMagazziniResponse;
import com.mondorevive.TRESPOT.responses.standardSelect.EntitySelect;
import com.mondorevive.TRESPOT.stabilimento.StabilimentoService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Transactional
public class MagazzinoService {
    private final MagazzinoRepository magazzinoRepository;
    private final StabilimentoService stabilimentoService;
    private final ClienteService clienteService;
    private final CauzioneService cauzioneService;

    public MagazzinoService(MagazzinoRepository magazzinoRepository, StabilimentoService stabilimentoService,
                            ClienteService clienteService, @Lazy @NotNull CauzioneService cauzioneService) {
        this.magazzinoRepository = magazzinoRepository;
        this.stabilimentoService = stabilimentoService;
        this.clienteService = clienteService;
        this.cauzioneService = cauzioneService;
    }

    private void salva(Magazzino nuovo) {
        magazzinoRepository.save(nuovo);
    }

    @Transactional(readOnly = true)
    public Magazzino getById(Long id){
        return magazzinoRepository.getMagazzinoById(id).orElseThrow(EntityNotFoundException::new);
    }
    @Transactional(readOnly = true)
    public List<EntitySelect> getMagazziniInterni() {
        List<Magazzino>magazziniInterniList = magazzinoRepository.getMagazziniInterni();
        return magazziniInterniList.stream().map(x -> new EntitySelect(x.getId(), x.getDescrizione())).collect(Collectors.toList());
    }
    @Transactional(readOnly = true)
    public List<EntitySelect> getMagazziniClienti() {
        List<Magazzino>magazziniClientiList = magazzinoRepository.getMagazziniClienti();
        return magazziniClientiList.stream().map(x -> new EntitySelect(x.getId(), x.getDescrizione())).collect(Collectors.toList());
    }
    @Transactional(readOnly = true)
    public DettaglioMagazzinoResponse getDettaglioMagazzino(Long id) {
        Magazzino magazzinoById = magazzinoRepository.getDettaglioById(id);
        return new DettaglioMagazzinoResponse(magazzinoById.getId(),magazzinoById.getDescrizione(),
                magazzinoById.getCliente() != null ? magazzinoById.getCliente().getId() : null,
                magazzinoById.getCliente() != null ? magazzinoById.getCliente().getCodice() : null,
                magazzinoById.getStabilimento() != null ? magazzinoById.getStabilimento().getId() : null,
                magazzinoById.getStabilimento() != null ? magazzinoById.getStabilimento().getCodice() : null);
    }

    @Transactional(readOnly = true)
    public List<DettaglioCauzioniMagazzinoResponse> getDettaglioCauzioniMagazzino(Long id){
        List<Cauzione>cauzioneList = cauzioneService.getCauzioneListByIdMagazzino(id);
        //Secondo step: per ogni cauzioni trovo l'ultima operazione di carico per il mio magazzino
        List<UltimoCaricoCauzione>ultimoCaricoCauzioneList = cauzioneService.getUltimiCarichi(cauzioneList.stream().map(Cauzione::getId).collect(Collectors.toList()));
        List<DettaglioCauzioniMagazzinoResponse>cauzioniResponseList = new LinkedList<>();
        for(Cauzione cauzione : cauzioneList){
            UltimoCaricoCauzione ultimoCaricoCauzione =
                    ultimoCaricoCauzioneList.stream().filter(x -> x.getId().equals(cauzione.getId())).findFirst().orElse(null);
            cauzioniResponseList.add(new DettaglioCauzioniMagazzinoResponse(cauzione.getId(),cauzione.getEpcTag(),cauzione.getMatricola(),cauzione.getTipologiaCauzione().getCodice(),cauzione.getTipologiaCauzione().getDescrizione(),ultimoCaricoCauzione != null ? ultimoCaricoCauzione.getTimestampOperazione() : null));
        }
        return cauzioniResponseList;
    }

    public Long creaNuovoMagazzino(CreaNuovoMagazzinoRequest creaNuovoMagazzinoRequest) {
        if(creaNuovoMagazzinoRequest.getIdCliente() == null && creaNuovoMagazzinoRequest.getIdStabilimento() == null) throw new InvalidRequestException("Cliente e stabilimento non specificati");
        Magazzino nuovo = new Magazzino(creaNuovoMagazzinoRequest.getDescrizione());
        if(creaNuovoMagazzinoRequest.getIdStabilimento() == null)
            nuovo.setCliente(clienteService.getById(creaNuovoMagazzinoRequest.getIdCliente()));
        else
            nuovo.setStabilimento(stabilimentoService.getById(creaNuovoMagazzinoRequest.getIdStabilimento()));
        salva(nuovo);
        return nuovo.getId();
    }

    public void updateMagazzino(UpdateMagazzinoRequest request){
        if(request.getIdCliente() == null && request.getIdStabilimento() == null) throw new InvalidRequestException("Cliente e stabilimento non specificati");
        magazzinoRepository.updateMagazzino(request.getId(), request.getDescrizione(), request.getIdCliente(), request.getIdStabilimento());
    }

    public void deleteMagazzino(Long id){
        magazzinoRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<GetAllMagazziniResponse> getAll() {
        return magazzinoRepository.getAll().stream().map(x -> new GetAllMagazziniResponse(x.getId(),x.getDescrizione(),
                x.getCliente() != null ? x.getCliente().getId() : null,
                x.getCliente() != null ? x.getCliente().getCodice() : null,
                x.getStabilimento() != null ? x.getStabilimento().getId() : null,
                x.getStabilimento() != null ? x.getStabilimento().getCodice() : null)
        ).collect(Collectors.toList());
    }

    public List<EntitySelect> getMagazziniSelect() {
        return magazzinoRepository.getAll().stream().map(x -> new EntitySelect(x.getId(),x.getDescrizione())).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Magazzino getByIdbobina(Long idBobina){
        return magazzinoRepository.getByIdBobina(idBobina);
    }

    public void creaMagazzino(String codiceCliente,Cliente cliente) {
        salva(new Magazzino(codiceCliente,cliente));
    }

    public void importaMagazzino(Magazzino magazzino) {
        salva(magazzino);
    }

    @Transactional(readOnly = true)
    public Magazzino getByDescrizione(String magazzino) {
        return magazzinoRepository.getByDescrizione(magazzino);
    }
}
