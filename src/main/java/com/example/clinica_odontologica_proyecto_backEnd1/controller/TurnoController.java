package com.example.clinica_odontologica_proyecto_backEnd1.controller;

import com.example.clinica_odontologica_proyecto_backEnd1.model.Turno;
import com.example.clinica_odontologica_proyecto_backEnd1.repository.impl.OdontologoDaoH2;
import com.example.clinica_odontologica_proyecto_backEnd1.repository.impl.PacienteDaoH2;
import com.example.clinica_odontologica_proyecto_backEnd1.repository.impl.TurnoListRepository;
import com.example.clinica_odontologica_proyecto_backEnd1.service.OdontologoService;
import com.example.clinica_odontologica_proyecto_backEnd1.service.PacienteService;
import com.example.clinica_odontologica_proyecto_backEnd1.service.TurnoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController

@RequestMapping("/turnos")
public class TurnoController {
    private TurnoService turnoService = new TurnoService(new TurnoListRepository());
    private PacienteService pacienteService = new PacienteService(new PacienteDaoH2());
    private OdontologoService odontologoService = new OdontologoService(new OdontologoDaoH2());

    @PostMapping("/registrar")
    public ResponseEntity<Turno> registrarTurno(@RequestBody Turno turno) throws SQLException {
        ResponseEntity response = null;
        if(pacienteService.buscarPaciente(turno.getPaciente().getId()) !=null && odontologoService.buscarOdontologo(turno.getOdontologo().getId()) != null){
            response = new ResponseEntity<>(turnoService.guardarTurno(turno), HttpStatus.OK);
        }else{
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return response;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Turno> buscarTurno(@PathVariable("id") Integer id) throws SQLException {
        ResponseEntity response = null;
        if (turnoService.buscarTurno(id)!= null){
            response = new ResponseEntity<>(turnoService.buscarTurno(id), HttpStatus.OK);
        }else{
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return response;
    }

    @GetMapping
    public ResponseEntity<List<Turno>> listadoTurnos() throws SQLException {
        ResponseEntity response = null;
        if (turnoService.listarTurnos().isEmpty()){
            response=new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }else{
            response = new ResponseEntity<>(turnoService.listarTurnos(),HttpStatus.OK);
        }
        return response;
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity eliminarTurno(@PathVariable("id") Integer id) throws SQLException{
        ResponseEntity response = null;
        if(turnoService.buscarTurno(id)!= null){
            turnoService.eliminarTurno(id);
            response = new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }else{
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return response;
    }

    @PutMapping("/actualizar")
    public ResponseEntity<Turno> actualizarTurno(@RequestBody Turno turno) throws SQLException {
        ResponseEntity response = null;
        if (turnoService.buscarTurno(turno.getId())!= null){
            response = new ResponseEntity<>(turnoService.actualizarTurno(turno), HttpStatus.OK);
        }else{
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return response;
    }
}
