package com.mondorevive.TRESPOT.bobina.bobinaStoricoCauzione;

import com.mondorevive.TRESPOT.bobina.Bobina;
import com.mondorevive.TRESPOT.cauzione.storicoCauzione.StoricoCauzione;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class BobinaStoricoCauzioneService {
    private final BobinaStoricoCauzioneRepository bobinaStoricoCauzioneRepository;
    private void salva(BobinaStoricoCauzione bobinaStoricoCauzione){
        bobinaStoricoCauzioneRepository.save(bobinaStoricoCauzione);
    }
    @Transactional(readOnly = true)
    public List<BobinaStoricoCauzione> getBobinaStoricoCauzioneByIdStoricoCauzioneList(List<Long> idStoricoCauzioneList) {
        return bobinaStoricoCauzioneRepository.getBobinaStoricoCauzioneByIdStoricoCauzioneList(idStoricoCauzioneList);
    }
    public void salvaBobinaStorico(Bobina bobina, StoricoCauzione storicoCauzione){
        salva(new BobinaStoricoCauzione(bobina,storicoCauzione));
    }
}
