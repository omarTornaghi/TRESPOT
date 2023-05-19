package com.mondorevive.TRESPOT.cauzione.statoCauzione;

import com.mondorevive.TRESPOT.responses.standardSelect.EntitySelect;
import com.mondorevive.TRESPOT.responses.standardSelect.Selectable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;

import java.util.Objects;

@Entity
@Table(name = "StatoCauzione", uniqueConstraints = {
        @UniqueConstraint(name = "uc_stato_cauzione_codice", columnNames = {"codice"})
})
@NoArgsConstructor
@Getter
@Setter
public class StatoCauzione implements Selectable {
    @Id
    @SequenceGenerator(name = "cauzione_sequence", sequenceName = "cauzione_sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cauzione_sequence")
    private Long id;
    @NaturalId
    private String codice;

    public StatoCauzione(String codice) {
        this.codice = codice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StatoCauzione that = (StatoCauzione) o;
        return codice.equals(that.codice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codice);
    }

    @Override
    public EntitySelect convertToSelectableEntity() {
        return new EntitySelect(id,codice);
    }
}
