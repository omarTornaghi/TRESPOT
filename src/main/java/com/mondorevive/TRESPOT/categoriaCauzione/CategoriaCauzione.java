package com.mondorevive.TRESPOT.categoriaCauzione;

import com.mondorevive.TRESPOT.pianoRevisione.PianoRevisione;
import com.mondorevive.TRESPOT.responses.standardSelect.EntitySelect;
import com.mondorevive.TRESPOT.responses.standardSelect.Selectable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;

import java.util.Objects;

@Entity
@Table(name = "categoria_cauzione", uniqueConstraints = {
        @UniqueConstraint(name = "uc_categoria_cauzione_codice", columnNames = {"codice"})
})
@NoArgsConstructor
@Getter
@Setter
public class CategoriaCauzione implements Selectable {
    @Id
    @SequenceGenerator(name = "categoria_cauzione_sequence", sequenceName = "categoria_cauzione_sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "categoria_cauzione_sequence")
    private Long id;
    @NaturalId
    private String codice;
    private String descrizione;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="piano_revisione_id")
    private PianoRevisione pianoRevisione;

    public CategoriaCauzione(String codice, String descrizione, PianoRevisione pianoRevisione) {
        this.codice = codice;
        this.descrizione = descrizione;
        this.pianoRevisione = pianoRevisione;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CategoriaCauzione that = (CategoriaCauzione) o;
        return codice.equals(that.codice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codice);
    }

    @Override
    public EntitySelect convertToSelectableEntity() {
        return new EntitySelect(this.id,this.codice + " - " + descrizione);
    }
}
