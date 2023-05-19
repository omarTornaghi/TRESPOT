package com.mondorevive.TRESPOT.bobina.bobinaStoricoCauzione;

import com.mondorevive.TRESPOT.bobina.Bobina;
import com.mondorevive.TRESPOT.cauzione.storicoCauzione.StoricoCauzione;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class BobinaStoricoCauzione {
    @Id
    @SequenceGenerator(name = "bobina_storico_cauzione_sequence", sequenceName = "bobina_storico_cauzione_sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bobina_storico_cauzione_sequence")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bobina_id")
    private Bobina bobina;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "storico_cauzione_id")
    private StoricoCauzione storicoCauzione;

    public BobinaStoricoCauzione(Bobina bobina, StoricoCauzione storicoCauzione) {
        this.bobina = bobina;
        this.storicoCauzione = storicoCauzione;
    }
}
