package ar.edu.utn.frc.tup.lciv.dtos;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResultadoDTO {
    private Long id;
    private String nombre;
    private Long votantes;
    private List<String> secciones;
    @JsonProperty("porcentaje_padron_nacional")
    private Double porcentajePadronNacional;
    @JsonProperty("agrupacion_ganadora")
    private String agrupacionGanadora;
    @JsonProperty("resultados_agrupaciones")
    private List<ResultadosAgrupacionesDTO> resultadosAgrupaciones;
}
