package com.mondorevive.TRESPOT.varco;

import com.mondorevive.TRESPOT.magazzino.Magazzino;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;

import java.util.Objects;

@Entity
@Table(name = "varco", uniqueConstraints = {
        @UniqueConstraint(name = "uc_varco_codice", columnNames = {"codice"})
})
@Getter
@Setter
@NoArgsConstructor
public class Varco {
    @Id
    @SequenceGenerator(name = "varco_sequence", sequenceName = "varco_sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "varco_sequence")
    private Long id;
    @NaturalId
    private String codice;
    private String descrizione;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "magazzino_carico_id")
    private Magazzino magazzinoCarico;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "magazzino_scarico_id")
    private Magazzino magazzinoScarico;

    public Varco(String codice, String descrizione, Magazzino magazzinoCarico, Magazzino magazzinoScarico) {
        this.codice = codice;
        this.descrizione = descrizione;
        this.magazzinoCarico = magazzinoCarico;
        this.magazzinoScarico = magazzinoScarico;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Varco varco = (Varco) o;
        return codice.equals(varco.codice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codice);
    }
}
