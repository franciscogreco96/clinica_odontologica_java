package com.example.clinica_odontologica_proyecto_backEnd1;

import com.example.clinica_odontologica_proyecto_backEnd1.model.Domicilio;
import com.example.clinica_odontologica_proyecto_backEnd1.repository.IDao;
import com.example.clinica_odontologica_proyecto_backEnd1.repository.impl.DomicilioDaoH2;
import com.example.clinica_odontologica_proyecto_backEnd1.service.DomicilioService;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.SQLException;

public class DomicilioServiceTest {
    public static final Logger logger = LogManager.getLogger(DomicilioServiceTest.class);
    public static DomicilioService domicilioService = new DomicilioService(new DomicilioDaoH2());

    @BeforeClass
    public static void cargarDataSetDomicilios() throws SQLException {
        Domicilio dom1 = new Domicilio("Rafael nuñez","9897","Cordoba", "Cordoba");
        Domicilio dom2 = new Domicilio("Chacabuco","2020","Santiago del estero", "Santiago del estero");
        Domicilio dom3 = new Domicilio("La cordillera","7032","Resistencia", "Formosa");

        domicilioService.crear(dom1);
        domicilioService.crear(dom2);
        domicilioService.crear(dom3);
    }
    @Test
    public void buscarDomicilioTest() throws SQLException {
        Assert.assertTrue(domicilioService.buscar(109) != null);
    }
    @Test
    public void eliminarDomicilio() throws SQLException {
        domicilioService.eliminar(109);
        Assert.assertTrue(domicilioService.buscar(3) ==null);
    }
    @Test
    public void listarTodosLosDomicilios() throws SQLException {
        Assert.assertTrue(domicilioService.listarTodos().size()!=0);
    }
}
