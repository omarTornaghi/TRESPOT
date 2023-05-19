package com.mondorevive.TRESPOT.pianoRevisione;

import com.mondorevive.TRESPOT.responses.standardSelect.EntitySelect;
import com.mondorevive.TRESPOT.responses.standardSelect.Selectable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;

import java.util.Objects;

@Entity
@Table(name = "piano_revisione", uniqueConstraints = {
        @UniqueConstraint(name = "uc_piano_revisione_codice", columnNames = {"codice"})
})
@NoArgsConstructor
@Getter
@Setter
public class PianoRevisione implements Selectable {
    @Id
    @SequenceGenerator(name = "piano_revisione_sequence", sequenceName = "piano_revisione_sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "piano_revisione_sequence")
    private Long id;
    @NaturalId
    private String codice;
    private String descrizione;

    public PianoRevisione(String codice, String descrizione) {
        this.codice = codice;
        this.descrizione = descrizione;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PianoRevisione that = (PianoRevisione) o;
        return codice.equals(that.codice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codice);
    }

    @Override
    public EntitySelect convertToSelectableEntity() {
        return new EntitySelect(this.id, this.codice + " - " + this.descrizione);
    }
}
