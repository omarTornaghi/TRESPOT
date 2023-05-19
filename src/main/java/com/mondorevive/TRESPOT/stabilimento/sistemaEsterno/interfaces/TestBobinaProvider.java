package com.mondorevive.TRESPOT.stabilimento.sistemaEsterno.interfaces;

import com.mondorevive.TRESPOT.utils.DateUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class TestBobinaProvider implements BobinaProvider{
    @Override
    public Optional<DatiBobina> getDatiBobinaByText(String text) {
        Random r = new Random();
        List<String> tipi = Arrays.asList("CP0","DP0","KP0");
        String tipoPartita = tipi.get(r.nextInt(0, tipi.size()-1));
        String annoCorrente = String.valueOf(DateUtils.getTimestampCorrente().getYear());
        String numeroPartita = String.format("%04d",r.nextInt(0,9999));
        String codicePartita = tipoPartita + "/" + annoCorrente + "/" + numeroPartita + "/" + "TEST";
        int numeroCliente = r.nextInt(0, 9999);
        String codiceCliente = "C " + String.format("%04d",numeroCliente);
        String ragioneSocialeCliente = "TEST " + numeroCliente;
        return Optional.of(new DatiBobina(codicePartita,codiceCliente,ragioneSocialeCliente));
    }
}
