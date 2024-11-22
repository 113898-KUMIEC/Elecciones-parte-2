package ar.edu.utn.frc.tup.lciv.repository;

import ar.edu.utn.frc.tup.lciv.entities.CargoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CargoRepository extends JpaRepository<CargoEntity, Long> {
    List<CargoEntity> findByIdDistrito(Long idDistrito);
}
