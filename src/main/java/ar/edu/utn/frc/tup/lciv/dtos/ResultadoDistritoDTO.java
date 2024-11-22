package ar.edu.utn.frc.tup.lciv.dtos;

import ar.edu.utn.frc.tup.lciv.models.Seccion;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResultadoDistritoDTO {
    private Long id;
    private String nombre;
    private List<String> secciones;
    @JsonProperty("votos_escrutados")
    private Long votosEstructurados;
    @JsonProperty("porcentaje_padron_nacional")
    private Double porcentajePadronNacional;
    @JsonProperty("agrupacion_ganadora")
    private String agrupacionGanadora;
    @JsonProperty("resultados_agrupaciones")
    private List<ResultadosAgrupacionesDTO> resultadosAgrupaciones;
}
