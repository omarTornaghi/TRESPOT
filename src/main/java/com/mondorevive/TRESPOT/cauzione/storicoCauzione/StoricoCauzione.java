package com.mondorevive.TRESPOT.cauzione.storicoCauzione;

import com.mondorevive.TRESPOT.cauzione.Cauzione;
import com.mondorevive.TRESPOT.cauzione.statoCauzione.StatoCauzione;
import com.mondorevive.TRESPOT.cauzione.storicoCauzione.operazione.Operazione;
import com.mondorevive.TRESPOT.magazzino.Magazzino;
import com.mondorevive.TRESPOT.pianoRevisione.revisione.Revisione;
import com.mondorevive.TRESPOT.utente.Utente;
import com.mondorevive.TRESPOT.utils.DateUtils;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "StoricoCauzione", indexes = {
        @Index(name = "idx_storico_cauzione_cauzione_id", columnList = "cauzione_id")
})
@NoArgsConstructor
@Getter
@Setter
public class StoricoCauzione {
    @Id
    @SequenceGenerator(name = "storico_cauzione_sequence", sequenceName = "storico_cauzione_sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "storico_cauzione_sequence")
    private Long id;
    private LocalDateTime timestampOperazione = DateUtils.getTimestampCorrente();
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cauzione_id")
    private Cauzione cauzione;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stato_cauzione_id")
    private StatoCauzione statoCauzione;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "magazzino_id")
    private Magazzino magazzino;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "utente_id")
    private Utente utente;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "operazione_id")
    private Operazione operazione;
    @OneToOne(mappedBy="storicoCauzione", cascade = CascadeType.ALL)
    private Revisione revisione;

    public StoricoCauzione(Cauzione cauzione, StatoCauzione statoCauzione, Magazzino magazzino, Utente utente,
                           Operazione operazione, Revisione revisione) {
        this.cauzione = cauzione;
        this.statoCauzione = statoCauzione;
        this.magazzino = magazzino;
        this.utente = utente;
        this.operazione = operazione;
        this.revisione = revisione;
        if(revisione != null)revisione.setStoricoCauzione(this);
    }

    //Utilizzato per importazione
    public StoricoCauzione(LocalDateTime timestampOperazione, Cauzione cauzione, StatoCauzione statoCauzione,
                           Magazzino magazzino, Utente utente, Operazione operazione, Revisione revisione) {
        this.timestampOperazione = timestampOperazione;
        this.cauzione = cauzione;
        this.statoCauzione = statoCauzione;
        this.magazzino = magazzino;
        this.utente = utente;
        this.operazione = operazione;
        this.revisione = revisione;
        if(revisione != null) revisione.setStoricoCauzione(this);
    }
}
