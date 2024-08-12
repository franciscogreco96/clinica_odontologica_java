package com.example.clinica_odontologica_proyecto_backEnd1.controller;

import com.example.clinica_odontologica_proyecto_backEnd1.model.Paciente;
import com.example.clinica_odontologica_proyecto_backEnd1.repository.impl.PacienteDaoH2;
import com.example.clinica_odontologica_proyecto_backEnd1.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {
    @Autowired
    private  PacienteService pacienteService ;
    @PostMapping("/registrar")
    public ResponseEntity crearPaciente(@RequestBody Paciente paciente) throws SQLException {
        ResponseEntity response = null;
        if (pacienteService.buscarPaciente(paciente.getId())!=null){
            response = new ResponseEntity<>(HttpStatus.CONFLICT);
        }else {
            response = new ResponseEntity<>(pacienteService.crearPaciente(paciente), HttpStatus.OK);
        }
        return response;
    }
    @GetMapping("/{id}")
    public ResponseEntity buscarPaciente(@PathVariable("id") Integer id) throws SQLException {
        ResponseEntity response = null;
        if (pacienteService.buscarPaciente(id) == null){
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else {
            response = new ResponseEntity<>(pacienteService.buscarPaciente(id), HttpStatus.OK);
        }
        return response;
    }
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity eliminarPaciente(@PathVariable("id") Integer id) throws SQLException {
        ResponseEntity response = null;
        if (pacienteService.buscarPaciente(id) == null){
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else {
            pacienteService.eliminarPaciente(id);
            response = new ResponseEntity<>( HttpStatus.NO_CONTENT);
        }
        return response;
    }
    @GetMapping()
    public ResponseEntity listarPacientes() throws SQLException {
        ResponseEntity response = null;
        if(pacienteService.listarPacientes().isEmpty()){
            response=new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }else{

        response = new ResponseEntity<>(pacienteService.listarPacientes(), HttpStatus.OK);
        }
        return response;
    }
    @PutMapping()
    public ResponseEntity actualizarPaciente(@RequestBody Paciente paciente) throws SQLException {
        ResponseEntity response = null;
        if (pacienteService.buscarPaciente(paciente.getId())==null){
            response=new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else{
        response = new ResponseEntity<>(pacienteService.actualizar(paciente), HttpStatus.OK);
    }
        return response;
    }
}
