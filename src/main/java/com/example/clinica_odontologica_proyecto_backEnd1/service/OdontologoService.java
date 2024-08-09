package com.example.clinica_odontologica_proyecto_backEnd1.service;



import com.example.clinica_odontologica_proyecto_backEnd1.model.Odontologo;
import com.example.clinica_odontologica_proyecto_backEnd1.repository.IDao;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service

public class OdontologoService {

    // ------------------- atributos odontologoService (solo usa la IDao para setear que tipo de subclase dao usar)-------

    private IDao<Odontologo> odontologoIDao;

    //-----------------------constructor odontologoService ----------------------
    public OdontologoService() {
    }

    public OdontologoService(IDao<Odontologo> odontologoIDao) {
        this.odontologoIDao = odontologoIDao;
    }

    // ----------------------------- getters y setters odontologoSerice -------------------

    public IDao<Odontologo> getOdontologoIDao() {
        return odontologoIDao;
    }

    public void setOdontologoIDao(IDao<Odontologo> odontologoIDao) {
        this.odontologoIDao = odontologoIDao;
    }

    // ---------------------metodos odontologoService usando IDao para su ejecucion------------------------

    public Odontologo crearOdontologo(Odontologo odontologo) throws SQLException{
        odontologoIDao.crear(odontologo);
        System.out.println(odontologo);
        return odontologo;
    }
    public void eliminarOdontologo(Integer id) throws SQLException {
        odontologoIDao.eliminar(id);
    }
    public Odontologo buscarOdontologo(Integer id) throws SQLException {
        Odontologo odontologo = odontologoIDao.buscar(id);
        return odontologo;
    }
    public List<Odontologo> listarTodosOdontologos() throws SQLException {
        List<Odontologo> listarTodos = odontologoIDao.listarTodos();
        System.out.println(listarTodos);
        return listarTodos;
    }

    public Odontologo actualizar(Odontologo odontologo) throws SQLException {
        Odontologo odontologo1 = odontologoIDao.actualizar(odontologo);
        System.out.println(odontologo1);
       return odontologo1;
    }
}
