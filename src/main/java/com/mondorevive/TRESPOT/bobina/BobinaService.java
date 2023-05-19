package com.mondorevive.TRESPOT.bobina;

import com.mondorevive.TRESPOT.cauzione.Cauzione;
import com.mondorevive.TRESPOT.cliente.Cliente;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class BobinaService {
    private final BobinaRepository bobinaRepository;

    private void salvaBobina(Bobina bobina){
        bobinaRepository.save(bobina);
    }

    @Transactional(readOnly = true)
    public List<Bobina> getBobineAssociate(Long id) {
        //Questo metodo ottiene le bobine associate al momento alla cauzione
        return bobinaRepository.getBobineAssociate(id);
    }

    public void rimuoviBobineAssociateByIdCauzione(Long id) {
        bobinaRepository.rimuoviBobineAssociateByIdCauzione(id);
    }

    public Optional<Bobina> getBobinaByText(String text) {
        return bobinaRepository.getBobinaByText(text);
    }

    public Bobina creaBobina(String codice, Cliente cliente) {
        Bobina bobina = new Bobina(codice, cliente);
        salvaBobina(bobina);
        return bobina;
    }

    public void associaBobineACauzione(List<Long> idBobineList, Cauzione cauzione) {
        bobinaRepository.associaBobineACauzione(idBobineList,cauzione.getId());
    }

    @Transactional(readOnly = true)
    public Bobina getById(Long id) {
        return bobinaRepository.findBobinaById(id).orElseThrow(EntityNotFoundException::new);
    }
}
