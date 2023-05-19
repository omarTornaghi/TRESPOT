package com.mondorevive.TRESPOT.utente;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mondorevive.TRESPOT.magazzino.Magazzino;
import com.mondorevive.TRESPOT.responses.standardSelect.EntitySelect;
import com.mondorevive.TRESPOT.responses.standardSelect.Selectable;
import com.mondorevive.TRESPOT.utente.ruolo.Ruolo;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;

import java.util.Objects;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "utente", uniqueConstraints = {
        @UniqueConstraint(name = "uc_utente_username_attivo", columnNames = {"username", "attivo"})
})

public class Utente implements Selectable {
    @Id
    @SequenceGenerator(name = "utente_sequence", sequenceName = "utente_sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "utente_sequence")
    private Long id;
    @NaturalId
    private String username;
    @JsonIgnore
    private String password;
    private String nome;
    private String cognome;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ruolo_id")
    private Ruolo ruolo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "magazzino_id")
    private Magazzino magazzino;

    private Boolean attivo = true;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Utente utente = (Utente) o;
        return username.equals(utente.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }

    public Utente(String username, String password, String nome, String cognome, Ruolo ruolo, Magazzino magazzino) {
        this.username = username;
        this.password = password;
        this.nome = nome;
        this.cognome = cognome;
        this.ruolo = ruolo;
        this.magazzino = magazzino;
    }

    @Override
    public EntitySelect convertToSelectableEntity() {
        return new EntitySelect(this.id,this.username);
    }
}
