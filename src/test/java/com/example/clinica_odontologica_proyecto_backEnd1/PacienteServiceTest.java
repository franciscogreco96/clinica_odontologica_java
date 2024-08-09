package com.example.clinica_odontologica_proyecto_backEnd1;

import com.example.clinica_odontologica_proyecto_backEnd1.model.Domicilio;
import com.example.clinica_odontologica_proyecto_backEnd1.model.Paciente;
import com.example.clinica_odontologica_proyecto_backEnd1.repository.impl.PacienteDaoH2;
import com.example.clinica_odontologica_proyecto_backEnd1.service.PacienteService;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class PacienteServiceTest {
    public static final Logger logger = LogManager.getLogger(PacienteServiceTest.class);

    public static final PacienteService pacienteService = new PacienteService(new PacienteDaoH2());

    @BeforeClass
    public static void  cargarDataSetPacientes() throws SQLException {
        Domicilio dom1 = new Domicilio("Rayomnd Poincare","8171","Villa dolores", "Cordoba");
        Domicilio dom2 = new Domicilio("La catedral","7777","Moron", "BsAs");

        Paciente paciente1 = new Paciente("Ernesto","Olmedo", "39497736", LocalDate.of(2019,12,13),dom1);
        Paciente paciente2 = new Paciente("Sebastian","Puebla", "20909871", LocalDate.of(1999,01,31),dom2);

        pacienteService.crearPaciente(paciente1);
        pacienteService.crearPaciente(paciente2);
    }

    @Test
    public void buscarPaciente() throws SQLException {
        Assert.assertTrue(pacienteService.buscarPaciente(13)!=null);
    }
    @Test
    public void eliminarPaciente() throws SQLException {
        pacienteService.eliminarPaciente(13);
        Assert.assertTrue(pacienteService.buscarPaciente(13)==null);
    }
    @Test
    public void listarPacientes() throws SQLException {
        List<Paciente> pacientes = pacienteService.listarPacientes();
        Assert.assertTrue(pacientes.size()!=0);
    }

    @Test
    public void actualizarPaciente() throws SQLException{
        Assert.assertNotNull(pacienteService.actualizar(new Paciente(3,"Patricio","Oliva",
                "45678909",LocalDate.of(2024,07,30),new Domicilio("Tristan Malbran",
                "1234", "Las rosas","Catamarca"))));




    }

}
