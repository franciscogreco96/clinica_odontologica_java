package com.example.clinica_odontologica_proyecto_backEnd1.repository.impl;


import com.example.clinica_odontologica_proyecto_backEnd1.model.Odontologo;
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

@Repository

public class OdontologoDaoH2 implements IDao<Odontologo> {
    // ---------------------- sobreescribo todos los metodos de la interfaz IDao para ejecutarlos de manera personalizada para odontologos--------
    private ConfiguracionJDBC configuracionJDBC;
    private static final Logger logger = LogManager.getLogger(OdontologoDaoH2.class);

    public OdontologoDaoH2(){
        this.configuracionJDBC=new ConfiguracionJDBC();
    }


    @Override
    public Odontologo crear(Odontologo odontologo) {
     // 1 importo el jar de H2 a este programa java y luego creo el driver
        logger.debug("creando y guardando un nuevo odontologo");
        Connection connection = null;
        PreparedStatement preparedStatement=null;
        try {
            connection = configuracionJDBC.conectarConBaseDeDatos();

            preparedStatement=connection.prepareStatement("INSERT INTO odontologo (nombre, apellido, numero_matricula) VALUES(?,?,?)",PreparedStatement.RETURN_GENERATED_KEYS);
            // No le vamos a pasar el id ya que pusimos que sea autoincremental en la base de datos
            // preparedStatement.setInt(1, odontologo.getId());
            preparedStatement.setString(1,odontologo.getNombre());
            preparedStatement.setString(2,odontologo.getApellido());
            preparedStatement.setInt(3,odontologo.getNumeroMatricula());

            preparedStatement.executeUpdate();
            ResultSet keys = preparedStatement.getGeneratedKeys();
            if(keys.next()){
                odontologo.setId(keys.getInt(1));
            }
            preparedStatement.close();
            logger.info("Odontologo creado con exito");


        }catch (SQLException throwables){
                logger.error("Error al crear el usuario", throwables);
                throwables.printStackTrace();

        }
        return odontologo;
    }

    @Override
    public void eliminar(Integer id) {
        logger.debug("Borrando odontologo con id: " + id);
        Connection connection = null;
        PreparedStatement preparedStatement=null;
        try {
            connection = configuracionJDBC.conectarConBaseDeDatos();
            preparedStatement=connection.prepareStatement("DELETE FROM odontologo WHERE id=?");
            preparedStatement.setInt(1,id);

            preparedStatement.executeUpdate();
            preparedStatement.close();
            logger.info("Odontologo eliminado con exito");


        }catch (SQLException throwables){
            logger.error("Error al intentar eliminar odontologo", throwables);
            throwables.printStackTrace();
        }
    }

    @Override
    public Odontologo buscar(Integer id)  {
        logger.debug("Buscando odontologo con id: " +id);
        Odontologo odontologo =null;
        Connection connection = null;
        PreparedStatement preparedStatement=null;
        try {
            connection = configuracionJDBC.conectarConBaseDeDatos();

            preparedStatement=connection.prepareStatement("SELECT * FROM odontologo WHERE id=?");
            preparedStatement.setInt(1,id);

            ResultSet rd = preparedStatement.executeQuery();
            while (rd.next()){
                Integer idOdontologo = rd.getInt("id");
                String nombre = rd.getString("nombre");
                String apellido = rd.getString("apellido");
                Integer numero_matricula = rd.getInt("numero_matricula");

                odontologo= new Odontologo(idOdontologo,nombre,apellido,numero_matricula);
            }
            preparedStatement.close();
            logger.info("Odontologo buscado con exito");


        }catch (SQLException throwables){
            logger.error("Error al buscar odontologo", throwables.fillInStackTrace());
            throwables.printStackTrace();
        }
        System.out.println(odontologo);
        return odontologo;
    }

    @Override
    public List<Odontologo> listarTodos()  {
        logger.debug("Buscando todos los odontologos");
        Connection connection = null;
        PreparedStatement preparedStatement=null;
        List<Odontologo> listaOdontologos = new ArrayList<>();
        try {
            connection = configuracionJDBC.conectarConBaseDeDatos();

            preparedStatement=connection.prepareStatement("SELECT * FROM odontologo");
            ResultSet rd = preparedStatement.executeQuery();
            while (rd.next()){
                Integer idOdontologo = rd.getInt("id");
                String nombre = rd.getString("nombre");
                String apellido = rd.getString("apellido");
                Integer numero_matricula = rd.getInt("numero_matricula");
                Odontologo odontologo= new Odontologo(idOdontologo,nombre,apellido,numero_matricula);
                listaOdontologos.add(odontologo);
            }

            preparedStatement.close();
            logger.info("Se listaron todos los odontologos con exito");


        }catch (SQLException throwables){
            logger.error("Error al listar todos los odontologos", throwables);
            throwables.printStackTrace();
        }
        return listaOdontologos;
    }

    @Override
    public Odontologo actualizar(Odontologo odontologo) {
       logger.debug("Actualizando odontologo");

        Connection connection = null;
        PreparedStatement preparedStatement=null;
        try {
            connection = configuracionJDBC.conectarConBaseDeDatos();

            preparedStatement=connection.prepareStatement("UPDATE odontologo SET nombre = ?, apellido = ?, numero_matricula = ? WHERE id =?");
            // No le vamos a pasar el id ya que pusimos que sea autoincremental en la base de datos
            // preparedStatement.setInt(1, odontologo.getId());
            preparedStatement.setString(1,odontologo.getNombre());
            preparedStatement.setString(2,odontologo.getApellido());
            preparedStatement.setInt(3,odontologo.getNumeroMatricula());
            preparedStatement.setInt(4,odontologo.getId());

            preparedStatement.executeUpdate();
            preparedStatement.close();
            logger.info("Odontologo actualizado con exito");


        }catch (SQLException throwables){
            logger.error("Error al buscar odontologo", throwables.fillInStackTrace());
            throwables.printStackTrace();
        }
        return odontologo;
    }
}
