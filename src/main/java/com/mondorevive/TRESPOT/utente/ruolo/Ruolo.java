package com.mondorevive.TRESPOT.utente.ruolo;

import com.mondorevive.TRESPOT.responses.standardSelect.EntitySelect;
import com.mondorevive.TRESPOT.responses.standardSelect.Selectable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NaturalId;

import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "ruolo", uniqueConstraints = {
        @UniqueConstraint(name = "uc_ruolo_codice", columnNames = {"codice"})
})
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@NoArgsConstructor
public class Ruolo implements Selectable {
    @Id
    @SequenceGenerator(name = "ruolo_sequence", sequenceName = "ruolo_sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ruolo_sequence")
    private Integer id;
    @NaturalId
    private String codice;

    public Ruolo(String codice) {
        this.codice = codice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ruolo ruolo = (Ruolo) o;
        return codice.equals(ruolo.codice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codice);
    }

    @Override
    public EntitySelect convertToSelectableEntity() {
        return new EntitySelect((long)this.id,codice);
    }
}
