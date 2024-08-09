package com.example.clinica_odontologica_proyecto_backEnd1.repository.configuracion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConfiguracionJDBC {
    private String JDBC_DRIVER;
    private String JDBC_URL;
    private String JDBC_USER;
    private String JDBC_PASSWORD;
    private Connection connection;

    public ConfiguracionJDBC(String JDBC_DRIVER, String JDBC_URL, String JDBC_USER, String JDBC_PASSWORD) {
        this.JDBC_DRIVER = JDBC_DRIVER;
        this.JDBC_URL = JDBC_URL;
        this.JDBC_USER = JDBC_USER;
        this.JDBC_PASSWORD = JDBC_PASSWORD;
    }


    public ConfiguracionJDBC(){
        this.JDBC_DRIVER="org.h2.Driver";
        this.JDBC_URL= "jdbc:h2:~/db_clinica_odontologica_proyecto;INIT=RUNSCRIPT FROM 'C:/Users/PC/Desktop/BACK END/CLINICA_ODONTOLOGICA_PROYECTO_BACKEND1/clinica_odontologica_proyecto_backEnd1/src/main/resources/create.sql'";
        this.JDBC_USER="sa";
        this.JDBC_PASSWORD="";
    }

    public Connection conectarConBaseDeDatos() throws SQLException {
        try {
            connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
        }catch (SQLException throwables){
            throwables.printStackTrace();
        }
        return connection;
    }

}
