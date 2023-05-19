package com.mondorevive.TRESPOT.magazzino;

import com.mondorevive.TRESPOT.cliente.Cliente;
import com.mondorevive.TRESPOT.stabilimento.Stabilimento;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Magazzino {
    @Id
    @SequenceGenerator(name = "magazzino_sequence", sequenceName = "magazzino_sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "magazzino_sequence")
    private Long id;
    private String descrizione;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stabilimento_id")
    private Stabilimento stabilimento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    public Magazzino(String descrizione) {
        this.descrizione = descrizione;
    }

    public Magazzino(String descrizione, Cliente cliente) {
        this.descrizione = descrizione;
        this.cliente = cliente;
    }

    //Utilizzato per importazione
    public Magazzino(String descrizione, Stabilimento stabilimento){
        this.descrizione = descrizione;
        this.stabilimento = stabilimento;
    }

    public boolean isMagazzinoCliente() {
        return stabilimento == null;
    }
}
