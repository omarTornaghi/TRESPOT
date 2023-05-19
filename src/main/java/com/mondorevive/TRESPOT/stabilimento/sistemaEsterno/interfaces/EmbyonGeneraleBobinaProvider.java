package com.mondorevive.TRESPOT.stabilimento.sistemaEsterno.interfaces;

import com.zaxxer.hikari.HikariDataSource;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public abstract class EmbyonGeneraleBobinaProvider{
    private static List<DatiBobina> mapResultToDatiBobinaList(ResultSet rs) throws SQLException {
        List<DatiBobina>out = new LinkedList<>();
        while (rs.next()) {
            out.add(new DatiBobina(rs.getString("CodLotto"), rs.getString("Codice_Cliente"),rs.getString("RagioneSociale_Cliente")));
        }
        return out;
    }
    protected Optional<DatiBobina> getDatiBobina(HikariDataSource dataSource, String text) {
        try {
            if (dataSource != null) {
                Connection conn = dataSource.getConnection();
                String query = "SELECT * FROM Pers_OT_VistaPartite WHERE CodLotto LIKE '%" + text + "%'";
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                List<DatiBobina> out = mapResultToDatiBobinaList(rs);
                if(out.size() == 0) return Optional.empty();
                return Optional.of(out.get(0));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return Optional.empty();
    }
}
