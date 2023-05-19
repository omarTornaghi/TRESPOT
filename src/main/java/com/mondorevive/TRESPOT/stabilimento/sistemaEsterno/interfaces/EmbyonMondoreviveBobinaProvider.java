package com.mondorevive.TRESPOT.stabilimento.sistemaEsterno.interfaces;

import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.util.Optional;

public class EmbyonMondoreviveBobinaProvider extends EmbyonGeneraleBobinaProvider implements BobinaProvider{
    private final HikariDataSource hikariDataSource;
    public EmbyonMondoreviveBobinaProvider(HikariDataSource hikariDataSource) {
        this.hikariDataSource = hikariDataSource;
    }

    @Override
    public Optional<DatiBobina> getDatiBobinaByText(String text) {
        return super.getDatiBobina(hikariDataSource,text);
    }
}
