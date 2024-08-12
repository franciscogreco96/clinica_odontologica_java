package com.example.clinica_odontologica_proyecto_backEnd1.controller;

import com.example.clinica_odontologica_proyecto_backEnd1.model.Odontologo;
import com.example.clinica_odontologica_proyecto_backEnd1.repository.impl.OdontologoDaoH2;
import com.example.clinica_odontologica_proyecto_backEnd1.service.OdontologoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/odontologos")

public class OdontologoController {

    @Autowired
    private OdontologoService odontologoService ;

    @PostMapping("/registrar")
    public ResponseEntity registrarOdontologo(@RequestBody Odontologo odontologo) throws SQLException {
        ResponseEntity response = null;
        response = new ResponseEntity<>(odontologoService.crearOdontologo(odontologo), HttpStatus.CREATED);
        return response;
    }
    @GetMapping("/{id}")
    public ResponseEntity buscarOdontologo(@PathVariable("id") Integer id) throws SQLException {
        ResponseEntity response = null;
        if(odontologoService.buscarOdontologo(id) == null){
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else{
            response = new ResponseEntity<>(odontologoService.buscarOdontologo(id), HttpStatus.OK);
        }
        return response;
    }
    @DeleteMapping("eliminar/{id}")
    public ResponseEntity eliminarOdontologo(@PathVariable("id") Integer id) throws SQLException {
        ResponseEntity response = null;
        if(odontologoService.buscarOdontologo(id) == null){
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else{
        odontologoService.eliminarOdontologo(id);
            response = new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return response;
    }
    @GetMapping()
    public ResponseEntity listarOdontologos() throws SQLException {
        ResponseEntity response = null;
        List<Odontologo> odontologos = odontologoService.listarTodosOdontologos();
        if(odontologos.isEmpty()){
            response=new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }else{
        response=new ResponseEntity<>(odontologos,HttpStatus.OK);
    }
        return response;
    }
    @PutMapping()
    public ResponseEntity<Odontologo> actualizarOdontologo(@RequestBody Odontologo odontologo) throws SQLException {
        ResponseEntity response = null;
        if (odontologoService.buscarOdontologo(odontologo.getId()) == null){
            response=new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else{

        response = new ResponseEntity<>(odontologoService.actualizar(odontologo) ,HttpStatus.CREATED);
    }
        return response;
    }
}
