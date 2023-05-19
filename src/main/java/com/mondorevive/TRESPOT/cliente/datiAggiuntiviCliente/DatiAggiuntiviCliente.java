package com.mondorevive.TRESPOT.cliente.datiAggiuntiviCliente;

import com.mondorevive.TRESPOT.cliente.Cliente;
import com.mondorevive.TRESPOT.stabilimento.sistemaEsterno.SistemaEsterno;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class DatiAggiuntiviCliente {
    @Id
    @SequenceGenerator(name = "dati_aggiuntivi_cliente_sequence", sequenceName = "dati_aggiuntivi_cliente_sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dati_aggiuntivi_cliente_sequence")
    private Long id;
    private String codice;
    private String ragioneSociale;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="cliente_id")
    private Cliente cliente;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sistema_esterno_id")
    private SistemaEsterno sistemaEsterno;

    public DatiAggiuntiviCliente(String codice, String ragioneSociale, Cliente cliente, SistemaEsterno sistemaEsterno) {
        this.codice = codice;
        this.ragioneSociale = ragioneSociale;
        this.cliente = cliente;
        this.sistemaEsterno = sistemaEsterno;
    }
}
