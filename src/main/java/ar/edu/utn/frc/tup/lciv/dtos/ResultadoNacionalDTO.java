package ar.edu.utn.frc.tup.lciv.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResultadoNacionalDTO {
    private List<String> distritos;
    @JsonProperty("votos_escrutados")
    private Long votosEstructurados;
    @JsonProperty("agrupacion_ganadora")
    private String agrupacionGanadora;
    @JsonProperty("resultados_nacionales")
    private List<ResultadosAgrupacionesDTO> resultadosNacionales;
    @JsonProperty("resultados_distritos")
    private List<ResultadoDistritoDTO> resultadosDistritos;
}
