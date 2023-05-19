package com.mondorevive.TRESPOT.stabilimento.sistemaEsterno;

import com.mondorevive.TRESPOT.responses.standardSelect.EntitySelect;
import com.mondorevive.TRESPOT.responses.standardSelect.Selectable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;

import java.util.List;
import java.util.Objects;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "sistema_esterno", uniqueConstraints = {
        @UniqueConstraint(name = "uc_sistema_esterno_codice", columnNames = {"codice"})
})
public class SistemaEsterno implements Selectable {
    @Id
    @SequenceGenerator(name = "sistema_esterno_sequence", sequenceName = "sistema_esterno_sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sistema_esterno_sequence")
    private Integer id;
    @NaturalId
    private String codice;

    public SistemaEsterno(String codice) {
        this.codice = codice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SistemaEsterno that = (SistemaEsterno) o;
        return codice.equals(that.codice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codice);
    }

    @Override
    public EntitySelect convertToSelectableEntity() {
        return new EntitySelect((long) this.id, this.codice);
    }
}
