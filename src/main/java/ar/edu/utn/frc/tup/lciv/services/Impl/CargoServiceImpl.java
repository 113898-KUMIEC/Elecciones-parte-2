package ar.edu.utn.frc.tup.lciv.services.Impl;

import ar.edu.utn.frc.tup.lciv.Client.RestClient;
import ar.edu.utn.frc.tup.lciv.entities.CargoEntity;
import ar.edu.utn.frc.tup.lciv.models.Cargos;
import ar.edu.utn.frc.tup.lciv.repository.CargoRepository;
import ar.edu.utn.frc.tup.lciv.services.CargoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class CargoServiceImpl implements CargoService {

    @Autowired
    private RestClient restClient;
    @Autowired
    private CargoRepository cargoRepository;
    @Override
    public List<CargoEntity> saveAllCargos(Long idDistrito) {
        List<Cargos> cargos = restClient.getAllCargosByDistritoAndCargo(idDistrito,null).getBody();
        List<CargoEntity> cargoEntities = cargoRepository.findByIdDistrito(idDistrito);
        if (cargoEntities.isEmpty()){
            for (Cargos c: Objects.requireNonNull(cargos)){
                CargoEntity cargoEntity = new CargoEntity();
                cargoEntity.setNombre(c.getCargoNombre());
                cargoEntity.setIdDistrito(c.getDistritoId());
                cargoRepository.save(cargoEntity);
                cargoEntities.add(cargoEntity);
            }
        }
        return cargoEntities;
    }

    @Override
    public List<CargoEntity> getAllCargos() {
        return cargoRepository.findAll();
    }
}
