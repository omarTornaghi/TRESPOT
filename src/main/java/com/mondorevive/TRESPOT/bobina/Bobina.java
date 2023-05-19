package com.mondorevive.TRESPOT.bobina;

import com.mondorevive.TRESPOT.cauzione.Cauzione;
import com.mondorevive.TRESPOT.cliente.Cliente;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;

import java.util.Objects;

@Entity
@Table(name = "Bobina", uniqueConstraints = {
        @UniqueConstraint(name = "uc_bobina_codice", columnNames = {"codice"})
})
@NoArgsConstructor
@Getter
@Setter
public class Bobina {
    @Id
    @SequenceGenerator(name = "bobina_sequence", sequenceName = "bobina_sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bobina_sequence")
    private Long id;
    @NaturalId
    private String codice;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cauzione_id")
    private Cauzione cauzione;

    public Bobina(String codice, Cliente cliente) {
        this.codice = codice;
        this.cliente = cliente;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bobina bobina = (Bobina) o;
        return codice.equals(bobina.codice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codice);
    }
}
