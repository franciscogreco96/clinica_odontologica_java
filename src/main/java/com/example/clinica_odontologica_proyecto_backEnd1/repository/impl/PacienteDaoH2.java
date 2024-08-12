package com.example.clinica_odontologica_proyecto_backEnd1.repository.impl;


import com.example.clinica_odontologica_proyecto_backEnd1.model.Domicilio;
import com.example.clinica_odontologica_proyecto_backEnd1.model.Paciente;
import com.example.clinica_odontologica_proyecto_backEnd1.repository.IDao;
import com.example.clinica_odontologica_proyecto_backEnd1.repository.configuracion.ConfiguracionJDBC;
import com.example.clinica_odontologica_proyecto_backEnd1.util.Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository("PacienteDaoH2")

public class PacienteDaoH2 implements IDao<Paciente> {
    private static final Logger logger = LogManager.getLogger(PacienteDaoH2.class);
    private ConfiguracionJDBC configuracionJDBC;
    @Autowired
    @Qualifier("DomicilioDaoH2")
    private IDao<Domicilio> domicilioDaoH2;

    public PacienteDaoH2() {
        this.configuracionJDBC=new ConfiguracionJDBC();
    }

    @Override
    public Paciente crear(Paciente paciente) throws SQLException {
        logger.debug("Creando paciente");
        Connection connection = null;
        PreparedStatement preparedStatement=null;
        try {
        connection=configuracionJDBC.conectarConBaseDeDatos();
        /* como primer paso debemos guardar el domicilio del paciente
        * ya que necesitamos el ID del domicilio que se generara en la base de datos para
        * luego insertar ese id en el campo domicilio_id de la tabla pacientes que es la foreign key*/

            Domicilio domicilio = domicilioDaoH2.crear(paciente.getDomicilio());
            paciente.getDomicilio().setIdDomicilio(domicilio.getIdDomicilio());
            // crear una sentencia especificando que el ID lo autoincrementa la base de datos y que nos devuelva esa key es decir ID
           preparedStatement= connection.prepareStatement("INSERT INTO paciente(nombre, apellido, dni, fecha_ingreso, domicilio_id)VALUES(?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            // no le vamos a pasar el ID ya que hicimos que fuera autoincremental en la base de datos
            // preparedStatement.setInt(1, paceinte.getId())
            preparedStatement.setString(1, paciente.getNombre());
            preparedStatement.setString(2, paciente.getApellido());
            preparedStatement.setString(3, paciente.getDni());
            // hay que convertir el LocalDate en sql.Date ya que son dos clases diferentes en java
            preparedStatement.setDate(4, Util.utilDateToSqlDate(paciente.getFechaIngreso()));
            // tenemos que pasarle la clave foranea del Id del domicilio es decir el campo domicilio_id
            preparedStatement.setInt(5, paciente.getDomicilio().getIdDomicilio());

            //Ejecutar una sentencia SQL y obtener los ID que se autogeneraron en la base de datos
            preparedStatement.executeUpdate();
            ResultSet keys = preparedStatement.getGeneratedKeys();
            if(keys.next()){
                paciente.setId(keys.getInt(1));
            }
            preparedStatement.close();
            logger.info("Paciente creado con exito");

        }catch (SQLException throwables){
            logger.error("Error al crear Paciente", throwables);
            throwables.printStackTrace();
        }
        return paciente;

    }

    @Override
    public void eliminar(Integer id) throws SQLException {
        logger.info("Eliminando paciente con id: " + id);
        Connection connection = null;
        PreparedStatement preparedStatement=null;
        try {
            connection=configuracionJDBC.conectarConBaseDeDatos();
            preparedStatement= connection.prepareStatement("DELETE FROM paciente WHERE id=?");
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
            preparedStatement.close();
            logger.info("Paciente eliminado con exito");

        }catch (SQLException throwables){
            logger.error("Error al eliminar Paciente: ", throwables);
            throwables.printStackTrace();
        }
    }

    @Override
    public Paciente buscar(Integer id) throws SQLException {
        logger.info("Buscando paciente con id: " + id);
        Paciente paciente = null;
        Connection connection = null;
        PreparedStatement preparedStatement=null;
        try {
            connection=configuracionJDBC.conectarConBaseDeDatos();
            preparedStatement= connection.prepareStatement("SELECT * FROM paciente WHERE id=?");
            preparedStatement.setInt(1, id);

            ResultSet rd =preparedStatement.executeQuery();
            while (rd.next()){
                Integer idPaciente = rd.getInt("id");
                String nombre = rd.getString("nombre");
                String apellido = rd.getString("apellido");
                String dni = rd.getString("dni");
                Date fecha_ingreso= rd.getDate("fecha_ingreso");
                Integer idDomicilio=rd.getInt("domicilio_id");

                //Con el idDomicilio traemos el domicilio de la tabla domicilio a traves de DAO de Domicilios
                Domicilio domicilio = domicilioDaoH2.buscar(idDomicilio);
                paciente= new Paciente(idPaciente,nombre,apellido,dni, fecha_ingreso.toLocalDate(),domicilio);
            }
            preparedStatement.close();
            logger.info("Paciente buscado con exito");

        }catch (SQLException throwables){
            logger.error("Error al buscar Paciente", throwables);
            throwables.printStackTrace();
        }
        return paciente;
    }

    @Override
    public List<Paciente> listarTodos() throws SQLException {
        logger.debug("Listando todos los pacientes");
      Connection connection = null;
      PreparedStatement preparedStatement = null;
      List<Paciente>listadoPacientes = new ArrayList<Paciente>();
      try{
          connection=configuracionJDBC.conectarConBaseDeDatos();

          // Crear una sentencia
          preparedStatement = connection.prepareStatement("SELECT * FROM paciente");
          // Ejecutar una sentencia SQL
          ResultSet rd =preparedStatement.executeQuery();
          logger.info("Pacientes listados correctamente");
          //Obtener resultados
          while (rd.next()){
              Integer idPaciente = rd.getInt("id");
              String nombre = rd.getString("nombre");
              String apellido = rd.getString("apellido");
              String dni = rd.getString("dni");
              Date fechaIngreso = rd.getDate("fecha_ingreso");
              Integer idDomicilio = rd.getInt("domicilio_id");
              //Con el domicilio_id traemos el domicilio de la tabla domicilio a traves de DAO de Domicilios
              Domicilio domicilio = domicilioDaoH2.buscar(idDomicilio);
              Paciente paciente=new Paciente(idPaciente,nombre,apellido,dni, fechaIngreso.toLocalDate(),domicilio);
              listadoPacientes.add(paciente);

          }
      }catch (SQLException throwables){
          throwables.printStackTrace();
          logger.error("Error al listar todos los pacientes: ", throwables);
      }
      return listadoPacientes;
    }

    @Override
    public Paciente actualizar(Paciente paciente) {
        logger.debug("Actualizando paciente");

        Connection connection = null;
        PreparedStatement preparedStatement=null;
        try {
            connection = configuracionJDBC.conectarConBaseDeDatos();
            Domicilio domicilio = domicilioDaoH2.crear(paciente.getDomicilio());
            paciente.getDomicilio().setIdDomicilio(domicilio.getIdDomicilio());

            preparedStatement=connection.prepareStatement("UPDATE paciente SET nombre = ?, apellido = ?, dni = ?, fecha_ingreso = ?, domicilio_id = ? WHERE id =?");
            // No le vamos a pasar el id ya que pusimos que sea autoincremental en la base de datos
            // preparedStatement.setInt(1, odontologo.getId());
            preparedStatement.setString(1,paciente.getNombre());
            preparedStatement.setString(2,paciente.getApellido());
            preparedStatement.setString(3,paciente.getDni());
            preparedStatement.setDate(4,Util.utilDateToSqlDate(paciente.getFechaIngreso()));
            preparedStatement.setInt(5,domicilio.getIdDomicilio());
            preparedStatement.setInt(6, paciente.getId());

            preparedStatement.executeUpdate();
            preparedStatement.close();
            logger.info("Paciente actualizado con exito");


        }catch (SQLException throwables){
            logger.error("Error al actualizar paciente", throwables.fillInStackTrace());
            throwables.printStackTrace();
        }
        return paciente;
    }
}
