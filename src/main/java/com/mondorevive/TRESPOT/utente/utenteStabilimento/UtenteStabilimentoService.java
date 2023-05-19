package com.mondorevive.TRESPOT.utente.utenteStabilimento;

import com.mondorevive.TRESPOT.stabilimento.StabilimentoService;
import com.mondorevive.TRESPOT.utente.Utente;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class UtenteStabilimentoService {
    private final UtenteStabilimentoRepository utenteStabilimentoRepository;
    private final StabilimentoService stabilimentoService;
    private void salva(UtenteStabilimento utenteStabilimento){
        utenteStabilimentoRepository.save(utenteStabilimento);
    }

    public void creaAssociazioni(List<Long>idStabilimentiList, Utente utente){
        idStabilimentiList.forEach(x -> salva(new UtenteStabilimento(utente,stabilimentoService.getById(x))));
    }

    public void updateAssociazioniUtenteStabilimento(List<Long> list, Utente utente){
        List<UtenteStabilimento> stabilimentiByUtenteId = getStabilimentiByUtenteId(utente.getId());
        for(UtenteStabilimento us: stabilimentiByUtenteId){
            //Se la lista non contiene questo stabilimento allora elimino l'associazione che contiene
            //lo stabilimento
            if(!list.contains(us.getStabilimento().getId())){
                deleteById(us.getId());
            }
        }
        //Adesso scorro gli stabilimenti che mi ha inviato il fe
        //Se non lo trovo tra i miei vuol dire che va aggiunto
        //Se lo trovo non devo fare niente
        for(Long id: list){
            if(stabilimentiByUtenteId.stream().filter(x -> x.getStabilimento().getId().equals(id)).findFirst().isEmpty()){
                UtenteStabilimento utenteStabilimento = new UtenteStabilimento(utente, stabilimentoService.getById(id));
                salva(utenteStabilimento);
            }
        }
    }

    @Transactional(readOnly = true)
    public List<UtenteStabilimento> getStabilimentiByUtenteId(Long id){
        return utenteStabilimentoRepository.getStabilimentiByUtenteId(id);
    }

    @Transactional(readOnly = true)
    public List<Long> getStabilimentiIdByUtenteId(Long id){
        return utenteStabilimentoRepository.getStabilimentiByUtenteId(id).stream().map(x -> x.getStabilimento().getId()).collect(Collectors.toList());
    }

    public void deleteStabilimentiByUtenteId(Long id){
        utenteStabilimentoRepository.deleteByUtenteId(id);
    }


    public void deleteById(Long id){
        utenteStabilimentoRepository.deleteById(id);
    }
    @Transactional(readOnly = true)
    public List<UtenteStabilimento> getByUsername(String username) {
        return utenteStabilimentoRepository.getByUtenteUsername(username);
    }

    public void importa(UtenteStabilimento utenteStabilimento) {
        salva(utenteStabilimento);
    }
}
