package ar.edu.utn.frc.tup.lciv.Client;

import ar.edu.utn.frc.tup.lciv.dtos.DistritoDTO;
import ar.edu.utn.frc.tup.lciv.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.xml.transform.Result;
import java.util.List;

@Service
public class RestClient {

    @Autowired
    RestTemplate restTemplate;

    private static final String URL = "http://server:8080";

    public ResponseEntity<List<Distrito>> getAllDistritos(){
        return restTemplate.exchange(URL + "/distritos", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Distrito>>() {});
    }
    public ResponseEntity<List<Distrito>> getDistritoById(Long id){
        return restTemplate.exchange(URL + "/distritos?distritoId=" + id, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Distrito>>() {});
    }


    public ResponseEntity<List<Cargos>> getAllCargosByDistritoAndCargo(Long idDistrito, Long idCargo){
        if (idCargo == null){
            return restTemplate.exchange(URL + "/cargos?distritoId="+idDistrito, HttpMethod.GET, null,
                    new ParameterizedTypeReference<List<Cargos>>() {});
        }else {
            return restTemplate.exchange(URL + "/cargos?distritoId=" + idDistrito + "&cargoId=" + idCargo, HttpMethod.GET, null,
                    new ParameterizedTypeReference<List<Cargos>>() {});
        }
    }
    public ResponseEntity<List<Seccion>> getAllSeccionByDistritoAndSeccion(Long idDistrito, Long idSeccion){
        if (idSeccion == null){
            return restTemplate.exchange(URL + "/secciones?distritoId="+idDistrito, HttpMethod.GET, null,
                    new ParameterizedTypeReference<List<Seccion>>() {});
        }else {
            return restTemplate.exchange(URL + "/secciones?seccionId=" + idSeccion + "&distritoId=" + idDistrito, HttpMethod.GET, null,
                    new ParameterizedTypeReference<List<Seccion>>() {});
        }
    }

    public ResponseEntity<List<Resultados>> getResultadoByDistritoId(Long id){
        return restTemplate.exchange(URL + "/resultados?districtId=" + id, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Resultados>>() {});
    }
    public ResponseEntity<Agrupaciones> getAgrupacionById(Long id){
        return restTemplate.exchange(URL + "/agrupaciones/" + id, HttpMethod.GET, null,
                new ParameterizedTypeReference<Agrupaciones>() {});
    }
}
