package ar.edu.utn.frc.tup.lciv.services.Impl;

import ar.edu.utn.frc.tup.lciv.Client.RestClient;
import ar.edu.utn.frc.tup.lciv.dtos.CargosDTO;
import ar.edu.utn.frc.tup.lciv.dtos.DistritoDTO;
import ar.edu.utn.frc.tup.lciv.dtos.ResultadoDTO;
import ar.edu.utn.frc.tup.lciv.dtos.ResultadoNacionalDTO;
import ar.edu.utn.frc.tup.lciv.dtos.SeccionDTO;
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
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class EleccionesServiceImplTest {

    @InjectMocks
    private EleccionesServiceImpl eleccionesService;
    @Mock
    private RestClient restClient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllDistritos() {
        Distrito distritoDTO = new Distrito();
        distritoDTO.setDistritoNombre("Cordoba");
        distritoDTO.setDistritoId(1L);
        when(restClient.getAllDistritos()).thenReturn(ResponseEntity.ok(List.of(distritoDTO)));
        List<DistritoDTO> distritoDTOS = eleccionesService.getAllDistritos();
        assertNotNull(distritoDTOS);
    }

    @Test
    void getDistritoById() {
        Distrito distritoDTO = new Distrito();
        distritoDTO.setDistritoNombre("Cordoba");
        distritoDTO.setDistritoId(1L);
        when(restClient.getDistritoById(1L)).thenReturn(ResponseEntity.ok(List.of(distritoDTO)));
        List<DistritoDTO> distritoDTOS = eleccionesService.getDistritoById(1L);
        assertNotNull(distritoDTOS);
    }

    @Test
    void getCargos() {
        Cargos cargos = new Cargos();
        cargos.setCargoId(1L);
        cargos.setCargoNombre("cargo");
        when(restClient.getAllCargosByDistritoAndCargo(1L,1L)).thenReturn(ResponseEntity.ok(List.of(cargos)));
        List<CargosDTO> cargosDTOS = eleccionesService.getCargos(1L,1L);
        assertNotNull(cargosDTOS);
    }

    @Test
    void getSecciones() {
        Seccion seccion = new Seccion();
        seccion.setSeccionId(1L);
        seccion.setSeccionNombre("seccion");
        when(restClient.getAllSeccionByDistritoAndSeccion(1L,1L)).thenReturn(ResponseEntity.ok(List.of(seccion)));
        List<SeccionDTO> seccionDTOS = eleccionesService.getSecciones(1L,1L);
        assertNotNull(seccionDTOS);
    }

    @Test
    void getResultados() {
        Long distritoId = 1L;
        String distritoNombre = "Distrito 1";

        List<Distrito> distritosMock = List.of(new Distrito(distritoId, distritoNombre));
        List<Resultados> resultadosMock = List.of(
                new Resultados(1L, 1L,1L,1L,1L, "Agrupación A", 100L),
                new Resultados(2L, 1L,1L,1L,2L, "Agrupación B", 150L)
        );
        Agrupaciones agrupacionMock = new Agrupaciones(1L, "Agrupación A");
        Agrupaciones agrupacionMock2 = new Agrupaciones(2L, "Agrupación B");

        List<SeccionDTO> seccionesMock = List.of(new SeccionDTO(1L, "Sección 1"));

        when(restClient.getAllDistritos()).thenReturn(ResponseEntity.ok(distritosMock));
        when(restClient.getDistritoById(1L)).thenReturn(ResponseEntity.ok((distritosMock)));
        when(restClient.getResultadoByDistritoId(distritoId)).thenReturn(ResponseEntity.ok(resultadosMock));
        when(restClient.getAgrupacionById(1L)).thenReturn(ResponseEntity.ok(agrupacionMock));
        when(restClient.getAgrupacionById(2L)).thenReturn(ResponseEntity.ok(agrupacionMock2));
        when(restClient.getAllSeccionByDistritoAndSeccion(distritoId, null))
                .thenReturn(ResponseEntity.ok(List.of(new Seccion(1L, "Sección 1",1L))));

        List<ResultadoDTO> resultados = eleccionesService.getResultados(distritoId);

        assertNotNull(resultados);
        assertEquals(1, resultados.size());
        ResultadoDTO resultado = resultados.get(0);

        assertEquals(distritoId, resultado.getId());
        assertEquals(distritoNombre, resultado.getNombre());
        assertEquals(250L, resultado.getVotantes());
        assertEquals("Agrupación B", resultado.getAgrupacionGanadora());
        assertEquals(1, resultado.getSecciones().size());
        assertEquals("Sección 1", resultado.getSecciones().get(0));
    }

    @Test
    void getResultadosNacionales() {
        Distrito distrito1 = new Distrito(1L, "Distrito A");
        List<Distrito> mockDistritos = List.of(distrito1);

        Resultados resultado1 = new Resultados(1L,1L,1L,1L,1L, "POSITIVO", 200L);
        Resultados resultado2 = new Resultados(2L,1L,1L,1L,2L, "POSITIVO",  300L);

        List<Resultados> mockResultadosDistrito1 = Arrays.asList(resultado1, resultado2);

        Agrupaciones agrupacionA = new Agrupaciones(1L, "Agrupación A");
        Agrupaciones agrupacionB = new Agrupaciones(2L, "Agrupación B");

        when(restClient.getAllDistritos()).thenReturn(ResponseEntity.ok(mockDistritos));
        when(restClient.getResultadoByDistritoId(1L)).thenReturn(ResponseEntity.ok(mockResultadosDistrito1));
        when(restClient.getAgrupacionById(1L)).thenReturn(ResponseEntity.ok(agrupacionA));
        when(restClient.getAgrupacionById(2L)).thenReturn(ResponseEntity.ok(agrupacionB));
        when(restClient.getAllSeccionByDistritoAndSeccion(1L, null))
                .thenReturn(ResponseEntity.ok(List.of(new Seccion(1L, "Sección 1",1L))));


        List<ResultadoNacionalDTO> resultadosNacionales = eleccionesService.getResultadosNacionales();

        assertNotNull(resultadosNacionales);
        assertEquals(1, resultadosNacionales.size());

        ResultadoNacionalDTO resultadoNacionalDTO = resultadosNacionales.get(0);

        assertEquals(1, resultadoNacionalDTO.getDistritos().size());
        assertTrue(resultadoNacionalDTO.getDistritos().contains("Distrito A"));

        assertEquals(500L, resultadoNacionalDTO.getVotosEstructurados());

        assertEquals("Agrupación B", resultadoNacionalDTO.getAgrupacionGanadora());

        assertEquals(2, resultadoNacionalDTO.getResultadosNacionales().size());
        assertEquals("Agrupación B", resultadoNacionalDTO.getResultadosNacionales().get(0).getNombre());
        assertEquals(300L, resultadoNacionalDTO.getResultadosNacionales().get(0).getVotos());

    }
}