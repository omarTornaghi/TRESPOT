package com.mondorevive.TRESPOT.cauzione.storicoCauzione;

import com.mondorevive.TRESPOT.cauzione.Cauzione;
import com.mondorevive.TRESPOT.cauzione.statoCauzione.StatoCauzione;
import com.mondorevive.TRESPOT.cauzione.storicoCauzione.operazione.Operazione;
import com.mondorevive.TRESPOT.magazzino.Magazzino;
import com.mondorevive.TRESPOT.pianoRevisione.revisione.Revisione;
import com.mondorevive.TRESPOT.pojo.UltimoStorico;
import com.mondorevive.TRESPOT.responses.DettaglioCauzioniAttive;
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

@NamedNativeQuery(name = "get_ultima_operazione",
        query = "select \n" +
                "distinct on (sc.cauzione_id) sc.cauzione_id AS cauzione_id,\n" +
                "sc.timestamp_operazione as timestampOperazione,\n" +
                "c.timestamp_acquisto as timestampAcquisto,\n" +
                "DATE_PART('year', AGE(CURRENT_DATE, sc.timestamp_operazione)) AS years\n" +
                "from storico_cauzione sc inner join cauzione c on sc.cauzione_id = c.id\n" +
                "order by sc.cauzione_id, sc.timestamp_operazione desc;",
        resultSetMapping = "get_ultima_operazione_mapping")
@SqlResultSetMapping(
        name = "get_ultima_operazione_mapping",
        classes = @ConstructorResult(
                targetClass = UltimoStorico.class,
                columns = {
                        @ColumnResult(name = "cauzione_id", type = Long.class),
                        @ColumnResult(name = "timestampOperazione", type = LocalDateTime.class),
                        @ColumnResult(name = "timestampAcquisto", type = LocalDateTime.class),
                        @ColumnResult(name = "years", type = Double.class),
                }))

@NamedNativeQuery(name = "get_dettaglio_stato_cauzioni",
        query = "WITH cauzione_ultima_op AS (\n" +
                "    select \n" +
                "\tdistinct on (sc.cauzione_id) sc.cauzione_id,\n" +
                "\tsc.timestamp_operazione,\n" +
                "\tc.timestamp_acquisto,\n" +
                "\ttc.id as tipologia_id,\n" +
                "\ttc.codice as tipologia_codice,\n" +
                "\ttc.descrizione as tipologia_descrizione,\n" +
                "\tDATE_PART('year', AGE(CURRENT_DATE, sc.timestamp_operazione)) AS years\n" +
                "\tfrom storico_cauzione sc \n" +
                "\tinner join cauzione c on sc.cauzione_id = c.id\n" +
                "\tinner join tipologia_cauzione tc on c.tipologia_cauzione_id = tc.id\n" +
                "\torder by sc.cauzione_id, sc.timestamp_operazione desc\n" +
                ")\n" +
                "SELECT\n" +
                "    tipologia_id,tipologia_codice,tipologia_descrizione,count(*) as count\n" +
                "FROM \n" +
                "    cauzione_ultima_op\n" +
                "WHERE years BETWEEN :da AND :a\n" +
                "GROUP BY tipologia_id,tipologia_codice,tipologia_descrizione\n" +
                "ORDER BY count DESC;",
        resultSetMapping = "get_dettaglio_stato_cauzioni_mapping")
@SqlResultSetMapping(
        name = "get_dettaglio_stato_cauzioni_mapping",
        classes = @ConstructorResult(
                targetClass = DettaglioCauzioniAttive.class,
                columns = {
                        @ColumnResult(name = "tipologia_id", type = Long.class),
                        @ColumnResult(name = "tipologia_codice", type = String.class),
                        @ColumnResult(name = "tipologia_descrizione", type = String.class),
                        @ColumnResult(name = "count", type = Long.class),
                }))
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
