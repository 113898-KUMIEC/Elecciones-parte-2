package ar.edu.utn.frc.tup.lciv.controllers;

import ar.edu.utn.frc.tup.lciv.dtos.*;
import ar.edu.utn.frc.tup.lciv.services.EleccionesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController()
@RequestMapping("elecciones/")
public class EleccionesController {
    @Autowired
    private EleccionesService eleccionesService;

    @GetMapping("distritos")
    public ResponseEntity<List<DistritoDTO>> getDistritos(@RequestParam(required = false) Long id) {
        try {
            if (id == null){
                return ResponseEntity.ok(eleccionesService.getAllDistritos());
            }else {
                return ResponseEntity.ok(eleccionesService.getDistritoById(id));
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }
    @GetMapping("/distritos/{idDistrito}/cargos/{idCargo}")
    public ResponseEntity<List<CargosDTO>> getDistritosConCargo(
            @PathVariable Long idDistrito,
            @PathVariable Long idCargo) {
        try {
            return ResponseEntity.ok(eleccionesService.getCargos(idDistrito, idCargo));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @GetMapping("/distritos/{idDistrito}/cargos")
    public ResponseEntity<List<CargosDTO>> getDistritosSinCargo(
            @PathVariable Long idDistrito) {
        try {
            return ResponseEntity.ok(eleccionesService.getCargos(idDistrito, null));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @GetMapping("/distritos/{idDistrito}/secciones/{idSeccion}")
    public ResponseEntity<List<SeccionDTO>> getDistritoConSeccion(
            @PathVariable Long idDistrito,
            @PathVariable Long idSeccion) {
        try {
            return ResponseEntity.ok(eleccionesService.getSecciones(idDistrito, idSeccion));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @GetMapping("/distritos/{idDistrito}/secciones")
    public ResponseEntity<List<SeccionDTO>> getDistritosSinSeccion(
            @PathVariable Long idDistrito) {
        try {
            return ResponseEntity.ok(eleccionesService.getSecciones(idDistrito, null));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @GetMapping("/distritos/{idDistrito}/resultados")
    public ResponseEntity<List<ResultadoDTO>> getResultadoByDistrito(
            @PathVariable Long idDistrito) {
        try {
            return ResponseEntity.ok(eleccionesService.getResultados(idDistrito));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }
    @GetMapping("/resultados")
    public ResponseEntity<List<ResultadoNacionalDTO>> getResultadoNacional() {
        try {
            return ResponseEntity.ok(eleccionesService.getResultadosNacionales());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }
}
