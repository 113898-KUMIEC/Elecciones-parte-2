package ar.edu.utn.frc.tup.lciv.controllers;

import ar.edu.utn.frc.tup.lciv.dtos.CargosDTO;
import ar.edu.utn.frc.tup.lciv.dtos.DistritoDTO;
import ar.edu.utn.frc.tup.lciv.dtos.ResultadoDTO;
import ar.edu.utn.frc.tup.lciv.dtos.ResultadoNacionalDTO;
import ar.edu.utn.frc.tup.lciv.dtos.SeccionDTO;
import ar.edu.utn.frc.tup.lciv.models.Distrito;
import ar.edu.utn.frc.tup.lciv.services.EleccionesService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EleccionesController.class)
class EleccionesControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private EleccionesService eleccionesService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetDistritosWithId() throws Exception {
        DistritoDTO distrito = new DistritoDTO();
        distrito.setId(1L);
        distrito.setNombre("d");

        when(eleccionesService.getDistritoById(1L)).thenReturn(List.of(distrito));

        mockMvc.perform(get("/elecciones/distritos")
                        .param("id", "1") // Conversión explícita a String
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].nombre").value("d"));
    }

    @Test
    void testGetDistritosWithoutId() throws Exception {
        DistritoDTO distrito1 = new DistritoDTO();
        distrito1.setId(1L);
        distrito1.setNombre("Distrito 1");

        DistritoDTO distrito2 = new DistritoDTO();
        distrito2.setId(2L);
        distrito2.setNombre("Distrito 2");

        when(eleccionesService.getAllDistritos()).thenReturn(List.of(distrito1, distrito2));

        mockMvc.perform(get("/elecciones/distritos")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].nombre").value("Distrito 1"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].nombre").value("Distrito 2"));
    }

    @Test
    void getDistritosConCargo() throws Exception {
        CargosDTO cargosDTO = new CargosDTO();
        cargosDTO.setId(1L);
        cargosDTO.setNombre("c");

        when(eleccionesService.getCargos(1L,1L)).thenReturn(List.of(cargosDTO));
        mockMvc.perform(get("/elecciones/distritos/1/cargos/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].nombre").value("c"));
    }

    @Test
    void getDistritosSinCargo() throws Exception {
        CargosDTO cargosDTO = new CargosDTO();
        cargosDTO.setId(1L);
        cargosDTO.setNombre("c");

        when(eleccionesService.getCargos(1L,null)).thenReturn(List.of(cargosDTO));
        mockMvc.perform(get("/elecciones/distritos/1/cargos")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].nombre").value("c"));
    }

    @Test
    void getDistritoConSeccion() throws Exception {
        SeccionDTO seccionDTO = new SeccionDTO();
        seccionDTO.setId(1L);
        seccionDTO.setNombre("s");

        when(eleccionesService.getSecciones(1L,1L)).thenReturn(List.of(seccionDTO));
        mockMvc.perform(get("/elecciones/distritos/1/secciones/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].nombre").value("s"));
    }

    @Test
    void getDistritosSinSeccion() throws Exception {
        SeccionDTO seccionDTO = new SeccionDTO();
        seccionDTO.setId(1L);
        seccionDTO.setNombre("s");

        when(eleccionesService.getSecciones(1L,null)).thenReturn(List.of(seccionDTO));
        mockMvc.perform(get("/elecciones/distritos/1/secciones")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].nombre").value("s"));
    }

    @Test
    void getResultadoByDistrito() throws Exception {
        ResultadoDTO resultadoDTO = new ResultadoDTO();
        resultadoDTO.setId(1L);
        resultadoDTO.setNombre("r");

        when(eleccionesService.getResultados(1L)).thenReturn(List.of(resultadoDTO));
        mockMvc.perform(get("/elecciones/distritos/1/resultados")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].nombre").value("r"));
    }

    @Test
    void getResultadoNacional() throws Exception {
        ResultadoNacionalDTO resultadoDTO = new ResultadoNacionalDTO();
        resultadoDTO.setAgrupacionGanadora("R");
        resultadoDTO.setVotosEstructurados(1000L);

        when(eleccionesService.getResultadosNacionales()).thenReturn(List.of(resultadoDTO));
        mockMvc.perform(get("/elecciones/resultados")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].agrupacion_ganadora").value("R"))
                .andExpect(jsonPath("$[0].votos_escrutados").value(1000L));
    }
}