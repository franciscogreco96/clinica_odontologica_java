package com.example.clinica_odontologica_proyecto_backEnd1.service;


import com.example.clinica_odontologica_proyecto_backEnd1.model.Paciente;
import com.example.clinica_odontologica_proyecto_backEnd1.repository.IDao;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service

public class PacienteService {
    private IDao<Paciente> pacienteIDao;

    public PacienteService() {
    }

    public PacienteService(IDao<Paciente> pacienteIDao)  {
        this.pacienteIDao = pacienteIDao;
    }

    public IDao<Paciente> getPacienteIDao() {
        return pacienteIDao;
    }

    public void setPacienteIDao(IDao<Paciente> pacienteIDao) {
        this.pacienteIDao = pacienteIDao;
    }

    public Paciente crearPaciente(Paciente paciente) throws SQLException {
        Paciente paciente1 =pacienteIDao.crear(paciente);
        System.out.println(paciente);
        return paciente1;
    }

    public void eliminarPaciente(Integer id) throws SQLException {
        pacienteIDao.eliminar(id);
    }

    public Paciente buscarPaciente(Integer id) throws SQLException {
        Paciente paciente = pacienteIDao.buscar(id);
        System.out.println(paciente);
        return paciente;
    }

    public List<Paciente> listarPacientes() throws SQLException {
        List<Paciente> pacientesListado = pacienteIDao.listarTodos();
        System.out.println(pacientesListado);
        return pacientesListado;
    }
    public Paciente actualizar(Paciente paciente) throws SQLException {
        Paciente paciente1 = pacienteIDao.actualizar(paciente);
        System.out.println(paciente1);
        return paciente1;
    }


}
