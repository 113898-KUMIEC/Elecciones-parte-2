package ar.edu.utn.frc.tup.lciv.Client;

import ar.edu.utn.frc.tup.lciv.models.Agrupaciones;
import ar.edu.utn.frc.tup.lciv.models.Cargos;
import ar.edu.utn.frc.tup.lciv.models.Distrito;
import ar.edu.utn.frc.tup.lciv.models.Resultados;
import ar.edu.utn.frc.tup.lciv.models.Seccion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.when;

class RestClientTest {

    @InjectMocks
    private RestClient restClient;

    @Mock
    private RestTemplate restTemplate;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllDistritos() {
        Distrito distrito = new Distrito();
        distrito.setDistritoId(1L);
        distrito.setDistritoNombre("d");

        List<Distrito> list = List.of(distrito);

        when(restTemplate.exchange(
                any(String.class),
                eq(HttpMethod.GET),
                isNull(),
                any(ParameterizedTypeReference.class)
        )).thenReturn(new ResponseEntity<>(list, HttpStatus.OK));
        ResponseEntity<List<Distrito>> result = restClient.getAllDistritos();

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(1, Objects.requireNonNull(result.getBody()).size());
        assertEquals("d", result.getBody().get(0).getDistritoNombre());
    }

    @Test
    void getDistritoById() {
        Distrito distrito = new Distrito();
        distrito.setDistritoId(1L);
        distrito.setDistritoNombre("d");

        List<Distrito> list = List.of(distrito);

        when(restTemplate.exchange(
                any(String.class),
                eq(HttpMethod.GET),
                isNull(),
                any(ParameterizedTypeReference.class)
        )).thenReturn(new ResponseEntity<>(list, HttpStatus.OK));
        ResponseEntity<List<Distrito>> result = restClient.getDistritoById(1L);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(1, Objects.requireNonNull(result.getBody()).size());
        assertEquals("d", result.getBody().get(0).getDistritoNombre());
    }

    @Test
    void getAllCargosByDistritoAndCargo() {
        Cargos cargo = new Cargos();
        cargo.setDistritoId(1L);
        cargo.setCargoNombre("c");
        cargo.setCargoId(1L);

        List<Cargos> list = List.of(cargo);

        when(restTemplate.exchange(
                any(String.class),
                eq(HttpMethod.GET),
                isNull(),
                any(ParameterizedTypeReference.class)
        )).thenReturn(new ResponseEntity<>(list, HttpStatus.OK));
        ResponseEntity<List<Cargos>> result = restClient.getAllCargosByDistritoAndCargo(1L,1L);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(1, Objects.requireNonNull(result.getBody()).size());
        assertEquals("c", result.getBody().get(0).getCargoNombre());
    }

    @Test
    void getAllSeccionByDistritoAndSeccion() {
        Seccion seccion = new Seccion();
        seccion.setSeccionId(1L);
        seccion.setDistritoId(1L);
        seccion.setSeccionNombre("s");

        List<Seccion> list = List.of(seccion);

        when(restTemplate.exchange(
                any(String.class),
                eq(HttpMethod.GET),
                isNull(),
                any(ParameterizedTypeReference.class)
        )).thenReturn(new ResponseEntity<>(list, HttpStatus.OK));

        ResponseEntity<List<Seccion>> result = restClient.getAllSeccionByDistritoAndSeccion(1L,1L);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(1, Objects.requireNonNull(result.getBody()).size());
        assertEquals("s", result.getBody().get(0).getSeccionNombre());
    }

    @Test
    void getResultadoByDistritoId() {
        Resultados resultados = new Resultados();
        resultados.setDistritoId(1L);
        resultados.setSeccionId(1L);
        resultados.setCargoId(1L);
        resultados.setAgrupacionId(1L);
        resultados.setVotosCantidad(1L);
        resultados.setId(1L);

        List<Resultados> list = List.of(resultados);

        when(restTemplate.exchange(
                any(String.class),
                eq(HttpMethod.GET),
                isNull(),
                any(ParameterizedTypeReference.class)
        )).thenReturn(new ResponseEntity<>(list, HttpStatus.OK));

        ResponseEntity<List<Resultados>> result = restClient.getResultadoByDistritoId(1L);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(1, Objects.requireNonNull(result.getBody()).size());
        assertEquals(1L, result.getBody().get(0).getDistritoId());
    }

    @Test
    void getAgrupacionById() {
        Agrupaciones agrupacion = new Agrupaciones();
        agrupacion.setAgrupacionNombre("a");
        agrupacion.setAgrupacionId(1L);


        when(restTemplate.exchange(
                any(String.class),
                eq(HttpMethod.GET),
                isNull(),
                any(ParameterizedTypeReference.class)
        )).thenReturn(new ResponseEntity<>(agrupacion, HttpStatus.OK));

        ResponseEntity<Agrupaciones> result = restClient.getAgrupacionById(1L);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("a", result.getBody().getAgrupacionNombre());
    }
}