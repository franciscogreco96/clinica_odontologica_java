package com.example.clinica_odontologica_proyecto_backEnd1;


import com.example.clinica_odontologica_proyecto_backEnd1.model.Odontologo;
import com.example.clinica_odontologica_proyecto_backEnd1.repository.impl.OdontologoDaoH2;
import com.example.clinica_odontologica_proyecto_backEnd1.service.OdontologoService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;


import java.sql.SQLException;

public class OdontologoServiceTest {
    private static final Logger logger = LogManager.getLogger(OdontologoServiceTest.class);
    private static OdontologoService odontologoService = new OdontologoService( new OdontologoDaoH2());

    @BeforeClass
    public static void cargarDataSetOdontologos() throws SQLException{
    Odontologo odontologo1 = new Odontologo("Luciano", "Cuenca",1111);
    Odontologo odontologo2 = new Odontologo("Olivia", "Escalante",33333);
    odontologoService.crearOdontologo(odontologo1);
    odontologoService.crearOdontologo(odontologo2);
}
@Test
    public void buscarOdontologoTest() throws SQLException {
    Assert.assertTrue(odontologoService.buscarOdontologo(40) != null);
}
@Test
    public void eliminarOdontologoTest() throws SQLException {
        odontologoService.eliminarOdontologo(2);
        Assert.assertTrue(odontologoService.buscarOdontologo(1000)==null);
}
@Test
    public void traerTodosLosOdontologos() throws SQLException {
    Assert.assertTrue(odontologoService.listarTodosOdontologos().size()!=0);
    }

@Test
public void actualizarOodntologo() throws SQLException{
        Assert.assertNotNull(odontologoService.actualizar(new Odontologo(3,"Javier","Schiavi",909090)));
}

}
