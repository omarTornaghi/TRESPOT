package com.mondorevive.TRESPOT.stabilimento;

import com.mondorevive.TRESPOT.responses.standardSelect.EntitySelect;
import com.mondorevive.TRESPOT.responses.standardSelect.Selectable;
import com.mondorevive.TRESPOT.stabilimento.sistemaEsterno.SistemaEsterno;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.NaturalId;

import java.util.Objects;

@Table(name = "stabilimento", uniqueConstraints = {
        @UniqueConstraint(name = "uc_stabilimento_codice", columnNames = {"codice"})
})
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Stabilimento implements Selectable {
    @Id
    @SequenceGenerator(name = "stabilimento_sequence", sequenceName = "stabilimento_sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "stabilimento_sequence")
    private Long id;

    @NaturalId
    private String codice;
    private String descrizione;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sistema_esterno_id")
    private SistemaEsterno sistemaEsterno;

    public Stabilimento(String codice, String descrizione, SistemaEsterno sistemaEsterno) {
        this.codice = codice;
        this.descrizione = descrizione;
        this.sistemaEsterno = sistemaEsterno;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Stabilimento that = (Stabilimento) o;
        return Objects.equals(codice, that.codice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codice);
    }

    @Override
    public EntitySelect convertToSelectableEntity() {
        String label = StringUtils.isBlank(descrizione) ? this.codice : codice + " - " + descrizione;
        return new EntitySelect(this.id, label);
    }
}
