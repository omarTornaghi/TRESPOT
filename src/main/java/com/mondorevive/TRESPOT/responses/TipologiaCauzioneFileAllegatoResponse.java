package com.mondorevive.TRESPOT.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class TipologiaCauzioneFileAllegatoResponse extends FileResponse{
    private Boolean isCopertina;

    public TipologiaCauzioneFileAllegatoResponse(Long id, String filename, Boolean isCopertina) {
        super(id, filename);
        this.isCopertina = isCopertina;
    }
}
