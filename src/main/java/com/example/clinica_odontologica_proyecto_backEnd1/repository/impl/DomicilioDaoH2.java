package com.example.clinica_odontologica_proyecto_backEnd1.repository.impl;

import com.example.clinica_odontologica_proyecto_backEnd1.model.Domicilio;
import com.example.clinica_odontologica_proyecto_backEnd1.repository.IDao;
import com.example.clinica_odontologica_proyecto_backEnd1.repository.configuracion.ConfiguracionJDBC;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository("DomicilioDaoH2")
public class DomicilioDaoH2 implements IDao<Domicilio> {

    private ConfiguracionJDBC configuracionJDBC;
    private static final Logger logger = LogManager.getLogger(DomicilioDaoH2.class);
    public DomicilioDaoH2(){
        this.configuracionJDBC=new ConfiguracionJDBC();
    }

    @Override
    public Domicilio crear(Domicilio domicilio) throws SQLException {
        logger.debug("Creando y guardando un nuevo domicilio");
        Connection connection =null;
        PreparedStatement preparedStatement=null;

        try{
            connection=configuracionJDBC.conectarConBaseDeDatos();
            preparedStatement=connection.prepareStatement("INSERT INTO domicilio (calle, numero, localidad, provincia) VALUES(?,?,?,?)", PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1,domicilio.getCalle());
            preparedStatement.setString(2,domicilio.getNumero());
            preparedStatement.setString(3,domicilio.getLocalidad());
            preparedStatement.setString(4,domicilio.getProvincia());

            preparedStatement.executeUpdate();
            ResultSet keys = preparedStatement.getGeneratedKeys();
            while(keys.next()){
                domicilio.setIdDomicilio(keys.getInt(1));
            }
            preparedStatement.close();
            logger.info("Domicilio creado con exito");
        }

        catch (SQLException throwables){
            throwables.printStackTrace();
            logger.error("Error al ejecutar la creacion de este nuevo domicilio: ", throwables);
        }
        return domicilio;
    }

    @Override
    public void eliminar(Integer id) throws SQLException {
        logger.debug("Eliminando domicilio con id: " + id);
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        try{
            connection=configuracionJDBC.conectarConBaseDeDatos();
            preparedStatement=connection.prepareStatement("DELETE FROM domicilio WHERE id=?");
            preparedStatement.setInt(1,id);

            preparedStatement.executeUpdate();
            logger.info("Domicilio eliminado con exito");

        }catch (SQLException throwables){
            throwables.printStackTrace();
            logger.error("Error al intentar eliminar domicilio: ", throwables);
        }

    }

    @Override
    public Domicilio buscar(Integer id) throws SQLException {
        logger.debug("Buscando domicilio con id: " + id);
        Domicilio domicilio1=null;
        Connection connection = null;
        PreparedStatement preparedStatement=null;
        try{
            connection=configuracionJDBC.conectarConBaseDeDatos();
            preparedStatement=connection.prepareStatement("SELECT * FROM domicilio WHERE id=?");
            preparedStatement.setInt(1,id);

            ResultSet rd =preparedStatement.executeQuery();
            while (rd.next()){
                Integer idDomicilio = rd.getInt(1);
                String calle =rd.getString("calle");
                String numero =rd.getString("numero");
                String localidad =rd.getString("localidad");
                String provincia =rd.getString("provincia");

                domicilio1 = new Domicilio(idDomicilio,calle,numero,localidad,provincia);
            }
            preparedStatement.close();
            logger.info("Domicilio buscado con exito");
        }catch (SQLException throwables){
            logger.error("error al buscar domicilio por id: ", throwables);
        }
        return domicilio1;
    }

    @Override
    public List<Domicilio> listarTodos() throws SQLException {
        logger.debug("Listando todos los domicilios");
        List<Domicilio> listadoDomicilios=new ArrayList<Domicilio>();
        Connection connection = null;
        PreparedStatement preparedStatement=null;
        try{
            connection=configuracionJDBC.conectarConBaseDeDatos();
            preparedStatement=connection.prepareStatement("SELECT * FROM domicilio");

            ResultSet rd =preparedStatement.executeQuery();
            while (rd.next()){
                Integer idDomicilio = rd.getInt(1);
                String calle =rd.getString("calle");
                String numero =rd.getString("numero");
                String localidad =rd.getString("localidad");
                String provincia =rd.getString("provincia");

               Domicilio domicilio1 = new Domicilio(idDomicilio,calle,numero,localidad,provincia);
               listadoDomicilios.add(domicilio1);
            }
            preparedStatement.close();
            logger.info("Listado de todos los domicilios ejecutado con exito");
        }catch (SQLException throwables){
            logger.error("Error al listar todos los domicilios: ", throwables);
        }
        return listadoDomicilios;
    }

    @Override
    public Domicilio actualizar(Domicilio domicilio) {
        return null;
    }
}
