package com.mondorevive.TRESPOT.stabilimento.sistemaEsterno.interfaces;

import java.util.Optional;

public interface BobinaProvider {
    Optional<DatiBobina> getDatiBobinaByText(String text);
}
