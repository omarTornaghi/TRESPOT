package com.mondorevive.TRESPOT.pianoRevisione.frequenzaRevisione;

import com.mondorevive.TRESPOT.pianoRevisione.PianoRevisione;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class FrequenzaRevisione {
    @Id
    @SequenceGenerator(name = "frequenza_revisione_sequence", sequenceName = "frequenza_revisione_sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "frequenza_revisione_sequence")
    private Long id;
    private Integer daAnni;
    private Integer aAnni;
    private Integer frequenza;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "piano_revisione_id")
    private PianoRevisione pianoRevisione;

    public FrequenzaRevisione(Integer daAnni, Integer aAnni, Integer frequenza, PianoRevisione pianoRevisione) {
        this.daAnni = daAnni;
        this.aAnni = aAnni;
        this.frequenza = frequenza;
        this.pianoRevisione = pianoRevisione;
    }
}
