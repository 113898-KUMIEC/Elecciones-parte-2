package ar.edu.utn.frc.tup.lciv.services;

import ar.edu.utn.frc.tup.lciv.entities.CargoEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CargoService {

    List<CargoEntity> saveAllCargos(Long idDistrito);

    List<CargoEntity> getAllCargos();
}
