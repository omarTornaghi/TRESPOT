package com.mondorevive.TRESPOT.cauzione;

import com.mondorevive.TRESPOT.cauzione.statoCauzione.StatoCauzione;
import com.mondorevive.TRESPOT.magazzino.Magazzino;
import com.mondorevive.TRESPOT.tipologiaCauzione.TipologiaCauzione;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "cauzione", indexes = {
        @Index(name = "idx_cauzione_tipologia_cauzione_id", columnList = "tipologia_cauzione_id"),
        @Index(name = "idx_cauzione_magazzino_id", columnList = "magazzino_id"),
        @Index(name = "idx_cauzione_stato_cauzione_id", columnList = "stato_cauzione_id")
}, uniqueConstraints = {
        @UniqueConstraint(name = "uc_cauzione_epc_tag", columnNames = {"epcTag"}),
        @UniqueConstraint(name = "uc_cauzione_matricola", columnNames = {"matricola"})
})
@NoArgsConstructor
@Getter
@Setter
public class Cauzione {
    @Id
    @SequenceGenerator(name = "cauzione_sequence", sequenceName = "cauzione_sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cauzione_sequence")
    private Long id;
    private String epcTag;
    private String matricola;
    private LocalDateTime timestampAcquisto;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tipologia_cauzione_id")
    private TipologiaCauzione tipologiaCauzione;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "magazzino_id")
    private Magazzino magazzino;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stato_cauzione_id")
    private StatoCauzione statoCauzione;

    public Cauzione(String epcTag, String matricola, LocalDateTime timestampAcquisto,
                    TipologiaCauzione tipologiaCauzione, Magazzino magazzino, StatoCauzione statoCauzione) {
        this.epcTag = epcTag;
        this.matricola = matricola;
        this.timestampAcquisto = timestampAcquisto;
        this.tipologiaCauzione = tipologiaCauzione;
        this.magazzino = magazzino;
        this.statoCauzione = statoCauzione;
    }
}
