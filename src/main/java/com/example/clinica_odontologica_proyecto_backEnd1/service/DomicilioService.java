package com.example.clinica_odontologica_proyecto_backEnd1.service;

import com.example.clinica_odontologica_proyecto_backEnd1.model.Domicilio;
import com.example.clinica_odontologica_proyecto_backEnd1.repository.IDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service

public class DomicilioService {
    @Autowired
    @Qualifier("DomicilioDaoH2")
    private IDao<Domicilio> domicilioIDao;

    public void setDomicilioIDao(IDao<Domicilio> domicilioIDao) {
        this.domicilioIDao = domicilioIDao;
    }

    public Domicilio crear(Domicilio domicilio) throws SQLException {
        this.domicilioIDao.crear(domicilio);
        System.out.println(domicilio);
        return domicilio;
    }

    public void eliminar(Integer id) throws SQLException {
        this.domicilioIDao.eliminar(id);
    }

    public Domicilio buscar(Integer id) throws SQLException {
        Domicilio domicilio1= this.domicilioIDao.buscar(id);
        System.out.println(domicilio1);
        return domicilio1;
    }
    public List<Domicilio>listarTodos() throws SQLException {
        List<Domicilio>domicilios = this.domicilioIDao.listarTodos();
        System.out.println(domicilios);
        return domicilios;
    }
}
