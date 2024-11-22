package ar.edu.utn.frc.tup.lciv.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResultadosAgrupacionesDTO {
    private String nombre;
    private Integer posicion;
    private Long votos;
    private String porcentaje;
}
