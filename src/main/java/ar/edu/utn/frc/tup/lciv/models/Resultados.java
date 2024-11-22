package ar.edu.utn.frc.tup.lciv.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Resultados {
    private Long id;
    private Long distritoId;
    private Long seccionId;
    private Long cargoId;
    private Long agrupacionId;
    private String votosTipo;
    private Long votosCantidad;

}
