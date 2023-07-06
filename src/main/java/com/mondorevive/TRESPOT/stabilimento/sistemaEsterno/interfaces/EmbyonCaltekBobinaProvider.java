package com.mondorevive.TRESPOT.stabilimento.sistemaEsterno.interfaces;


import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

public class EmbyonCaltekBobinaProvider extends EmbyonGeneraleBobinaProvider implements BobinaProvider{
    private final HikariDataSource hikariDataSource;
    public EmbyonCaltekBobinaProvider(HikariDataSource hikariDataSource) {
        this.hikariDataSource = hikariDataSource;
    }

    @Override
    public Optional<DatiBobina> getDatiBobinaByText(String text) throws SQLException {
        return super.getDatiBobina(hikariDataSource,text);
    }
}
