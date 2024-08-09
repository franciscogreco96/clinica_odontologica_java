package com.example.clinica_odontologica_proyecto_backEnd1.repository;

import java.sql.SQLException;
import java.util.List;

public interface IDao <T>{

    public T crear(T t) throws SQLException;
    public void eliminar(Integer id) throws SQLException;
    public T buscar(Integer id) throws SQLException;
    public List<T> listarTodos() throws SQLException;
    T actualizar(T t) throws SQLException;

}
