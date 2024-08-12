package com.example.clinica_odontologica_proyecto_backEnd1.repository.impl;

import com.example.clinica_odontologica_proyecto_backEnd1.model.Turno;
import com.example.clinica_odontologica_proyecto_backEnd1.repository.IDao;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Repository("TurnoIDaoH2")

public class TurnoListRepository implements IDao<Turno> {
    private List<Turno> turnos;

    public TurnoListRepository() {
        this.turnos = new ArrayList<>();
    }

    @Override
    public Turno crear(Turno turno) throws SQLException {
        turnos.add(turno);
        return turno;
    }

    @Override
    public void eliminar(Integer id) throws SQLException {
        Iterator<Turno> iterator = turnos.iterator();
        while (iterator.hasNext()) {
            Turno turno = iterator.next();
            if ( turno.getId() == id) {
                iterator.remove();
            }
        }
    }




    @Override
    public Turno buscar(Integer id) throws SQLException {
        Turno turno1 = null;
        for(Turno turno : turnos){
            if (turno.getId()==id){
                turno1=turno;
            }
        }
        return turno1;
    }

    @Override
    public List<Turno> listarTodos() throws SQLException {
        return turnos;
    }

    @Override
    public Turno actualizar(Turno turno) throws SQLException {
        eliminar(turno.getId());
        turnos.add(turno);
        return turno;
    }
}
