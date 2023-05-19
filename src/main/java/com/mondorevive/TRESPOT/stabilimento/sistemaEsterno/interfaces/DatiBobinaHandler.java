package com.mondorevive.TRESPOT.stabilimento.sistemaEsterno.interfaces;

import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.BeanProcessor;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.util.HashMap;
import java.util.Map;

public class DatiBobinaHandler extends BeanListHandler<DatiBobina> {
    public DatiBobinaHandler(){
        super(DatiBobina.class, new BasicRowProcessor(new BeanProcessor(getColumnsToFieldsMap())));
    }
    public static Map<String, String> getColumnsToFieldsMap() {
        Map<String, String> columnsToFieldsMap = new HashMap<>();
        columnsToFieldsMap.put("CodLotto", "codice");
        /*columnsToFieldsMap.put("Codice_Cliente", "codiceCliente");
        columnsToFieldsMap.put("RagioneSociale_Cliente", "ragioneSocialeCliente");
        columnsToFieldsMap.put("CodArticolo","codArticolo");
        columnsToFieldsMap.put("Lunghezza_Reale","lunghezzaReale");
        columnsToFieldsMap.put("Numero_bancale","numeroBancale");
        columnsToFieldsMap.put("Numero_Bobine","numeroBobine");
        columnsToFieldsMap.put("peso","peso");*/
        return columnsToFieldsMap;
    }
}
