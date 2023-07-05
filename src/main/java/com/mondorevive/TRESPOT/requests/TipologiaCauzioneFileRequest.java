package com.mondorevive.TRESPOT.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TipologiaCauzioneFileRequest extends FileRequest{
    private Boolean isCopertina;
}
