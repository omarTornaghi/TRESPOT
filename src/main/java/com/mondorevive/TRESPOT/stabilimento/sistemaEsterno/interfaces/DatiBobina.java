package com.mondorevive.TRESPOT.stabilimento.sistemaEsterno.interfaces;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DatiBobina {
    private String codice;
    private String codiceCliente;
    private String ragioneSocialeCliente;

    public DatiBobina(String codice, String codiceCliente, String ragioneSocialeCliente) {
        this.codice = codice;
        this.codiceCliente = codiceCliente;
        this.ragioneSocialeCliente = ragioneSocialeCliente;
    }
}
