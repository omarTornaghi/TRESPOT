package com.mondorevive.TRESPOT.stabilimento.sistemaEsterno;

import com.mondorevive.TRESPOT.exceptions.SistemaEsternoNotSupportedException;
import com.mondorevive.TRESPOT.stabilimento.sistemaEsterno.interfaces.BobinaProvider;
import com.mondorevive.TRESPOT.stabilimento.sistemaEsterno.interfaces.EmbyonCaltekBobinaProvider;
import com.mondorevive.TRESPOT.stabilimento.sistemaEsterno.interfaces.EmbyonMondoreviveBobinaProvider;
import com.mondorevive.TRESPOT.stabilimento.sistemaEsterno.interfaces.TestBobinaProvider;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SistemaEsternoProvider {
    @Value("${sistemaEsterno.test}")
    private Boolean testSistemaEsterno;
    @Value("${sistemaEsterno.embyon.mondorevive.connectionString}")
    private String embyonMondoreviveConnectionString;
    @Value("${sistemaEsterno.embyon.mondorevive.user}")
    private String embyonMondoreviveUser;
    @Value("${sistemaEsterno.embyon.mondorevive.password}")
    private String embyonMondorevivePassword;
    @Value("${sistemaEsterno.embyon.caltek.connectionString}")
    private String embyonCaltekConnectionString;
    @Value("${sistemaEsterno.embyon.caltek.user}")
    private String embyonCaltekUser;
    @Value("${sistemaEsterno.embyon.caltek.password}")
    private String embyonCaltekPassword;

    private static HikariDataSource dsEmbyonMondorevive = null;
    private static HikariDataSource dsEmbyonCaltek = null;
    public BobinaProvider getBobinaProvider(TipoSistemaEsterno tipoSistemaEsterno){
        if(testSistemaEsterno)return new TestBobinaProvider();
        switch (tipoSistemaEsterno){
            case EMBYON_MONDOREVIVE -> {
                if(dsEmbyonMondorevive == null){
                    dsEmbyonMondorevive = creaEmbyonDataSource(embyonMondoreviveConnectionString,embyonMondoreviveUser,embyonMondorevivePassword);
                }
                return new EmbyonMondoreviveBobinaProvider(dsEmbyonMondorevive);
            }
            case EMBYON_CALTEK -> {
                if(dsEmbyonCaltek == null) {
                    dsEmbyonCaltek = creaEmbyonDataSource(embyonCaltekConnectionString,embyonCaltekUser,embyonCaltekPassword);
                }
                return new EmbyonCaltekBobinaProvider(dsEmbyonCaltek);
            }
            default -> throw new SistemaEsternoNotSupportedException("Il sistema esterno non è supportato");
        }
    }

    private HikariDataSource creaEmbyonDataSource(String connectionString,String user,String password) {
        HikariDataSource hds = new HikariDataSource();
        hds.setJdbcUrl(connectionString);
        hds.setUsername(user);
        hds.setPassword(password);
        hds.setConnectionTimeout(5000);
        hds.setReadOnly(true);
        return hds;
    }
}
