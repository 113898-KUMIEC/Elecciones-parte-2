package ar.edu.utn.frc.tup.lciv.services;

import ar.edu.utn.frc.tup.lciv.dtos.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface EleccionesService {
    List<DistritoDTO> getAllDistritos();

    List<DistritoDTO> getDistritoById(Long id);
    List<CargosDTO> getCargos(Long idDistrito, Long idCargo);
    List<SeccionDTO> getSecciones(Long idDistrito, Long idSeccion);
    List<ResultadoDTO> getResultados(Long distritoId);
    List<ResultadoNacionalDTO> getResultadosNacionales();
}
