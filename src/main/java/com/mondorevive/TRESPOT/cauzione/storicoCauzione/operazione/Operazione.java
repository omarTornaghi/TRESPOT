package com.mondorevive.TRESPOT.cauzione.storicoCauzione.operazione;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;

import java.util.Objects;

@Entity
@Table(name = "Operazione", uniqueConstraints = {
        @UniqueConstraint(name = "uc_operazione_codice", columnNames = {"codice"})
})
@NoArgsConstructor
@Getter
@Setter
public class Operazione {
    @Id
    @SequenceGenerator(name = "operazione_sequence", sequenceName = "operazione_sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "operazione_sequence")
    private Long id;
    @NaturalId
    private String codice;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Operazione that = (Operazione) o;
        return codice.equals(that.codice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codice);
    }

    public Operazione(String codice) {
        this.codice = codice;
    }
}
