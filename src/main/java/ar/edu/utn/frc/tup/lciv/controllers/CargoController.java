package ar.edu.utn.frc.tup.lciv.controllers;

import ar.edu.utn.frc.tup.lciv.entities.CargoEntity;
import ar.edu.utn.frc.tup.lciv.services.CargoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("cargos/")
public class CargoController {
    @Autowired
    private CargoService cargoService;

    @PostMapping("")
    public ResponseEntity<List<CargoEntity>> saveCargo(@RequestParam Long idDistrito){
        return ResponseEntity.ok(cargoService.saveAllCargos(idDistrito));
    }
    @GetMapping("")
    public ResponseEntity<List<CargoEntity>> getAllCargo(){
        return ResponseEntity.ok(cargoService.getAllCargos());
    }
}
