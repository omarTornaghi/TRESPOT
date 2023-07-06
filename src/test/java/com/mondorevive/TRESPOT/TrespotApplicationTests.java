package com.mondorevive.TRESPOT;

import com.mondorevive.TRESPOT.cauzione.CauzioneService;
import com.mondorevive.TRESPOT.utils.DateUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;

@SpringBootTest
class TrespotApplicationTests {
	@Autowired
	private CauzioneService cauzioneService;

	@Test
	void contextLoads() {
	}

	@Test
	void differenzeAnni(){
		LocalDateTime from = LocalDateTime.of(2023,3,1,3,3);
		LocalDateTime to = DateUtils.getTimestampCorrente();
		long years = ChronoUnit.YEARS.between(from, to);
		System.out.println("years = " + years);
	}

	@Test
	void dodiciMesiFa(){
		LocalDateTime now = DateUtils.getTimestampCorrente();
		LocalDateTime dataInizio = now.minusMonths(12).withDayOfMonth(1).toLocalDate().atStartOfDay();
		LocalDateTime dataFine = now.with(TemporalAdjusters.lastDayOfMonth()).toLocalDate().atStartOfDay();
		System.out.println(dataInizio.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		System.out.println(dataFine.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
	}

	@Test
	void testDisattivazioneAutomatica(){
		cauzioneService.ricercaEDisattivaTrespoli();
	}
}
