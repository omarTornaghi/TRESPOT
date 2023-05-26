package com.mondorevive.TRESPOT.tipologiaCauzione;

import com.mondorevive.TRESPOT.categoriaCauzione.CategoriaCauzione;
import com.mondorevive.TRESPOT.responses.standardSelect.EntitySelect;
import com.mondorevive.TRESPOT.responses.standardSelect.Selectable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;

import java.util.Objects;

@Entity
@Table(name = "tipologia_cauzione", uniqueConstraints = {
        @UniqueConstraint(name = "uc_tipologia_cauzione_codice", columnNames = {"codice"})
})
@NoArgsConstructor
@Getter
@Setter
public class TipologiaCauzione implements Selectable {
    @Id
    @SequenceGenerator(name = "tipologia_cauzione_sequence", sequenceName = "tipologia_cauzione_sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tipologia_cauzione_sequence")
    private Long id;
    @NaturalId
    private String codice;
    private String descrizione;
    private Integer numeroCauzioniMassimo;
    private Integer numeroKgMassimo;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_cauzione_id")
    private CategoriaCauzione categoriaCauzione;

    public TipologiaCauzione(String codice, String descrizione, Integer numeroCauzioniMassimo, Integer numeroKgMassimo, CategoriaCauzione categoriaCauzione) {
        this.codice = codice;
        this.descrizione = descrizione;
        this.numeroCauzioniMassimo = numeroCauzioniMassimo;
        this.numeroKgMassimo = numeroKgMassimo;
        this.categoriaCauzione = categoriaCauzione;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TipologiaCauzione that = (TipologiaCauzione) o;
        return codice.equals(that.codice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codice);
    }

    @Override
    public EntitySelect convertToSelectableEntity() {
        return new EntitySelect(id, codice + " - " + descrizione);
    }
    public String getCodiceTerminalino() {
       return this.codice.replace("ITR#", "");
    }
}
