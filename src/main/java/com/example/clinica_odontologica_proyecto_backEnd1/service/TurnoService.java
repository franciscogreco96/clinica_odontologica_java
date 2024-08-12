package com.example.clinica_odontologica_proyecto_backEnd1.service;

import com.example.clinica_odontologica_proyecto_backEnd1.model.Turno;
import com.example.clinica_odontologica_proyecto_backEnd1.repository.IDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service

public class TurnoService {

    @Autowired
    @Qualifier("TurnoIDaoH2")
    private IDao<Turno> turnoIDao;


    public Turno guardarTurno(Turno turno) throws SQLException {
       Turno turno1 = this.turnoIDao.crear(turno);
       return turno1;
    }
    public Turno buscarTurno(Integer id) throws SQLException {
        return this.turnoIDao.buscar(id);
    }

    public List<Turno> listarTurnos() throws SQLException {
        return this.turnoIDao.listarTodos();
    }
    public void eliminarTurno(Integer id) throws SQLException {
        if(this.turnoIDao.buscar(id)!= null){
            this.turnoIDao.eliminar(id);
        }
    }

    public Turno actualizarTurno(Turno turno) throws SQLException {
       return turnoIDao.actualizar(turno);
    }
}
