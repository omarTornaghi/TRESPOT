package com.mondorevive.TRESPOT.utente.utenteStabilimento;

import com.mondorevive.TRESPOT.stabilimento.Stabilimento;
import com.mondorevive.TRESPOT.utente.Utente;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class UtenteStabilimento {
    @Id
    @SequenceGenerator(name = "utente_stabilimento_sequence", sequenceName = "utente_stabilimento_sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "utente_stabilimento_sequence")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "utente_id")
    private Utente utente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stabilimento_id")
    private Stabilimento stabilimento;

    public UtenteStabilimento(Utente utente, Stabilimento stabilimento) {
        this.utente = utente;
        this.stabilimento = stabilimento;
    }
}
