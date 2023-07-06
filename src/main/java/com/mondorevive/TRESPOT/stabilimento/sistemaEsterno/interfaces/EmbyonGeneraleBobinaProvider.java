package com.mondorevive.TRESPOT.stabilimento.sistemaEsterno.interfaces;

import com.zaxxer.hikari.HikariDataSource;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public abstract class EmbyonGeneraleBobinaProvider{
    private static List<DatiBobina> mapResultToDatiBobinaList(String codiceBobina, ResultSet rs) throws SQLException {
        List<DatiBobina>out = new LinkedList<>();
        while (rs.next()) {
            out.add(new DatiBobina(codiceBobina, rs.getString("CODCLIDEST"), rs.getString("DSCCONTO1")));
        }
        return out;
    }

    private static String aggiungiZeri(String numId, int i) {
        StringBuilder stringBuilder = new StringBuilder(numId);
        while (stringBuilder.length() < i){
            stringBuilder.insert(0, "0");
        }
        return stringBuilder.toString();
    }

    protected Optional<DatiBobina> getDatiBobina(HikariDataSource dataSource, String text) {
        if(text.isBlank()) return Optional.empty();
        text = text.replace("-", "/");
        //TEXT E' tipo 23/CP02557/00006
        //Devo creare il codice odl
        String[] split = text.split("/");
        String anno = "20" + split[0];
        String tipoCommessa = split[1].substring(0,3);
        String numeroCommessa = aggiungiZeri(split[1].substring(3), 10);
        final String codiceOdl = tipoCommessa + "/" + anno + "/" + numeroCommessa;
        try {
            if (dataSource != null) {
                Connection conn = dataSource.getConnection();
                String query = "SELECT * FROM PERS_OT_VISTAPRGPROD WHERE RIFORDINE LIKE '" + codiceOdl + "%' ORDER BY " +
                        "RIFORDINE";
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                List<DatiBobina> out = mapResultToDatiBobinaList(text, rs);
                if(out.size() == 0) return Optional.empty();
                return Optional.of(out.get(0));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return Optional.empty();
    }
}
