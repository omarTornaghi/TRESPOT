package com.mondorevive.TRESPOT.cliente;

import com.mondorevive.TRESPOT.responses.standardSelect.EntitySelect;
import com.mondorevive.TRESPOT.responses.standardSelect.Selectable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;

import java.util.Objects;

@Entity
@Table(name = "cliente", uniqueConstraints = {
        @UniqueConstraint(name = "uc_cliente_codice", columnNames = {"codice"})
})
@NoArgsConstructor
@Getter
@Setter
public class Cliente implements Selectable {
    @Id
    @SequenceGenerator(name = "cliente_sequence", sequenceName = "cliente_sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cliente_sequence")
    private Long id;
    @NaturalId
    private String codice; //CODICE - DESCRIZIONE
    private String descrizione;

    public Cliente(String codice, String descrizione) {
        this.codice = codice;
        this.descrizione = descrizione;
    }

    @Override
    public EntitySelect convertToSelectableEntity() {
        return new EntitySelect(id, codice);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cliente cliente = (Cliente) o;
        return codice.equals(cliente.codice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codice);
    }
}
