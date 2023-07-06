package com.mondorevive.TRESPOT.jobs;

import com.mondorevive.TRESPOT.cauzione.CauzioneService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MidnightJob {
    private final CauzioneService cauzioneService;

    @Autowired
    public MidnightJob(CauzioneService cauzioneService) {
        this.cauzioneService = cauzioneService;
    }

    @Scheduled(cron = "0 0 0 * * *", zone = "Europe/Rome")
    public void doWork(){
        log.info("MidnightJob is starting");
        //cauzioneService -> Disattiva trespoli morti
        cauzioneService.ricercaEDisattivaTrespoli();
        //regoleService -> Verificare che trespoli rispettino le regole
    }
}
