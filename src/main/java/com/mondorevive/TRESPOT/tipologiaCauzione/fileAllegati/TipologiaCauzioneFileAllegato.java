package com.mondorevive.TRESPOT.tipologiaCauzione.fileAllegati;

import com.mondorevive.TRESPOT.tipologiaCauzione.TipologiaCauzione;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class TipologiaCauzioneFileAllegato {
    @Id
    @SequenceGenerator(name = "tipologia_cauzione_file_allegato_sequence", sequenceName = "tipologia_cauzione_file_allegato_sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tipologia_cauzione_file_allegato_sequence")
    private Long id;
    @Lob
    @Basic(fetch= FetchType.LAZY)
    private byte[] contenuto = null;
    private String fileName;
    private String contentType;
    private Boolean isCopertina;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tipologia_cauzione_id")
    private TipologiaCauzione tipologiaCauzione;

    public TipologiaCauzioneFileAllegato(byte[] contenuto, String fileName, String contentType, Boolean isCopertina,
                                         TipologiaCauzione tipologiaCauzione) {
        this.contenuto = contenuto;
        this.fileName = fileName;
        this.contentType = contentType;
        this.isCopertina = isCopertina;
        this.tipologiaCauzione = tipologiaCauzione;
    }

    public TipologiaCauzioneFileAllegato(TipologiaCauzione tipologiaCauzione, MultipartFile file, Boolean isCopertina) throws IOException {
        this(file.getBytes(),file.getOriginalFilename(),file.getContentType(),isCopertina,tipologiaCauzione);
    }
}
