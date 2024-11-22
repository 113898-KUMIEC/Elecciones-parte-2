package ar.edu.utn.frc.tup.lciv.services.Impl;

import ar.edu.utn.frc.tup.lciv.Client.RestClient;
import ar.edu.utn.frc.tup.lciv.dtos.*;
import ar.edu.utn.frc.tup.lciv.models.*;
import ar.edu.utn.frc.tup.lciv.services.EleccionesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class EleccionesServiceImpl implements EleccionesService {
    @Autowired
    private RestClient restClient;

    private List<Distrito> getAllDistrito(){
        return restClient.getAllDistritos().getBody();
    }

    private List<Resultados> getResultadoByDistritoId(Long id){
        return restClient.getResultadoByDistritoId(id).getBody();
    }

    private Agrupaciones getAgrupacionById(Long id){
        return restClient.getAgrupacionById(id).getBody();
    }

    @Override
    public List<DistritoDTO> getAllDistritos() {
        List<Distrito> distritos = getAllDistrito();
        List<DistritoDTO> distritoResponse = new ArrayList<>();
        for (Distrito d:distritos){
            DistritoDTO distritoDTO = new DistritoDTO(d.getDistritoId(),d.getDistritoNombre());
            distritoResponse.add(distritoDTO);
        }
        return distritoResponse;
    }
    @Override
    public List<DistritoDTO> getDistritoById(Long id) {
        List<Distrito> distritos = restClient.getDistritoById(id).getBody();
        List<DistritoDTO> distritoResponse = new ArrayList<>();
        if (distritos != null) {
            for (Distrito d:distritos){
                DistritoDTO distritoDTO = new DistritoDTO(d.getDistritoId(),d.getDistritoNombre());
                distritoResponse.add(distritoDTO);
            }
        }
        return distritoResponse;
    }

    @Override
    public List<CargosDTO> getCargos(Long idDistrito, Long idCargo) {
        List<CargosDTO> cargosResponse = new ArrayList<>();
        List<Cargos> cargos = restClient.getAllCargosByDistritoAndCargo(idDistrito, idCargo).getBody();

        if (cargos != null) {
            for (Cargos c : cargos) {
                CargosDTO cargosDTO = new CargosDTO(c.getCargoId(), c.getCargoNombre());
                cargosResponse.add(cargosDTO);
            }
        }
        return cargosResponse;
    }

    @Override
    public List<SeccionDTO> getSecciones(Long idDistrito, Long idSeccion) {
        List<SeccionDTO> seccionDTOS = new ArrayList<>();
        List<Seccion> seccions = restClient.getAllSeccionByDistritoAndSeccion(idDistrito,idSeccion).getBody();

        if (seccions != null){
            for (Seccion s:seccions){
                SeccionDTO seccionDTO = new SeccionDTO(s.getSeccionId(),s.getSeccionNombre());
                seccionDTOS.add(seccionDTO);
            }
        }
        return seccionDTOS;
    }

    @Override
    public List<ResultadoDTO> getResultados(Long distritoId) {
        List<Resultados> resultadosList = getResultadoByDistritoId(distritoId);

        String nombreDistrito = getDistritoById(distritoId).get(0).getNombre();
        Long totalVotantes = calcularVotantes(resultadosList);
        List<String> secciones = cargarSecciones(distritoId);
        Double porcentajeNacional = calcularPorcentajeNacional(totalVotantes);
        List<ResultadosAgrupacionesDTO> resultadosAgrupacionesDTOS = calcularResultado(resultadosList);

        List<ResultadoDTO> resultadoResponse = new ArrayList<>();

        ResultadoDTO resultadoDTO = new ResultadoDTO();
        resultadoDTO.setId(distritoId);
        resultadoDTO.setNombre(nombreDistrito);
        resultadoDTO.setVotantes(totalVotantes);
        resultadoDTO.setSecciones(secciones);
        resultadoDTO.setPorcentajePadronNacional(porcentajeNacional);
        resultadoDTO.setResultadosAgrupaciones(resultadosAgrupacionesDTOS);
        resultadoDTO.setAgrupacionGanadora( getAgrupacionGanadora(resultadosAgrupacionesDTOS));
        resultadoResponse.add(resultadoDTO);

        return resultadoResponse;
    }

    @Override
    public List<ResultadoNacionalDTO> getResultadosNacionales() {
        List<Resultados> resultadosList = getAllResultados();

        List<ResultadosAgrupacionesDTO> resultadosAgrupacionesDTOS = calcularResultado(resultadosList);
        Long votosTotales = calcularVotantes(resultadosList);

        List<ResultadoNacionalDTO> resultadoResponse = new ArrayList<>();
        ResultadoNacionalDTO resultadoNacionalDTO = new ResultadoNacionalDTO();
        resultadoNacionalDTO.setDistritos(getAllDistritoNombre());
        resultadoNacionalDTO.setVotosEstructurados(votosTotales);
        resultadoNacionalDTO.setAgrupacionGanadora(getAgrupacionGanadora(resultadosAgrupacionesDTOS));
        resultadoNacionalDTO.setResultadosNacionales(resultadosAgrupacionesDTOS);
        resultadoNacionalDTO.setResultadosDistritos(getResultadosDistritoNacional());
        resultadoResponse.add(resultadoNacionalDTO);

        return resultadoResponse;
    }

    private List<ResultadoDistritoDTO> getResultadosDistritoNacional() {
        List<ResultadoDistritoDTO> resultadoResponse = new ArrayList<>();

        List<Distrito> distritos = getAllDistrito();
        for (Distrito d:distritos){
            Long cantidadVotosDistrito = calcularVotantes(getResultadoByDistritoId(d.getDistritoId()));
            List<ResultadosAgrupacionesDTO> resultadosAgrupacionesDTOS = calcularResultado(getResultadoByDistritoId(d.getDistritoId()));
            ResultadoDistritoDTO resultadoDistritoDTO = new ResultadoDistritoDTO();
            resultadoDistritoDTO.setId(d.getDistritoId());
            resultadoDistritoDTO.setNombre(d.getDistritoNombre());
            resultadoDistritoDTO.setSecciones(cargarSecciones(d.getDistritoId()));
            resultadoDistritoDTO.setVotosEstructurados(calcularVotantes(getResultadoByDistritoId(d.getDistritoId())));
            resultadoDistritoDTO.setPorcentajePadronNacional(calcularPorcentajeNacional(cantidadVotosDistrito));
            resultadoDistritoDTO.setResultadosAgrupaciones(resultadosAgrupacionesDTOS);
            resultadoDistritoDTO.setAgrupacionGanadora(getAgrupacionGanadora(resultadosAgrupacionesDTOS));
            resultadoResponse.add(resultadoDistritoDTO);
        }
        return resultadoResponse;
    }

    private List<String> getAllDistritoNombre() {
        List<Distrito> distritos = getAllDistrito();
        List<String> response = new ArrayList<>();
        for (Distrito d:distritos){
            response.add(d.getDistritoNombre());
        }
        return response;
    }

    private List<Resultados> getAllResultados() {
        List<Resultados> resultados = new ArrayList<>();
        for (int i=1; i<getAllDistrito().size()+1;i++){
            List<Resultados> r = getResultadoByDistritoId((long)i);
            resultados.addAll(r);
        }
        return resultados;
    }

    private List<ResultadosAgrupacionesDTO> calcularResultado(List<Resultados> resultadosList) {
        List<Agrupaciones> agrupaciones = cargarAgrupaciones(resultadosList);
        List<ResultadosAgrupacionesDTO> resultado = new ArrayList<>();
        Double totalVotos = cargarVotos(resultadosList);


        for (Agrupaciones a:agrupaciones){
            ResultadosAgrupacionesDTO agrupacionesDTO = new ResultadosAgrupacionesDTO();
            agrupacionesDTO.setNombre(a.getAgrupacionNombre());
            agrupacionesDTO.setVotos(0L);
            for (Resultados r:resultadosList){
                if (a.getAgrupacionId() != 0 && Objects.equals(a.getAgrupacionId(), r.getAgrupacionId()) || a.getAgrupacionId() == 0 && Objects.equals(a.getAgrupacionNombre(), r.getVotosTipo())){
                    agrupacionesDTO.setVotos(agrupacionesDTO.getVotos()+r.getVotosCantidad());
                }
            }
            agrupacionesDTO.setPorcentaje(devolverPorcentaje(totalVotos,agrupacionesDTO.getVotos()));
            resultado.add(agrupacionesDTO);
        }
        resultado = resultado.stream()
                .sorted((a1, a2) -> Long.compare(a2.getVotos(), a1.getVotos())) // Ordenar de mayor a menor
                .collect(Collectors.toList());

        ordenarPosicion(resultado);
        return resultado;
    }

    private void ordenarPosicion(List<ResultadosAgrupacionesDTO> resultado) {
        int posicion = 0;
        for ( ResultadosAgrupacionesDTO r:resultado){
            r.setPosicion(posicion+1);
            posicion++;
        }
    }

    private String devolverPorcentaje(Double totalVotos, Long votos) {
        double porcentaje = votos * 100 / totalVotos;
        BigDecimal bd = new BigDecimal(Double.toString(porcentaje));
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        double redondeado = bd.doubleValue();
        return redondeado + " %";
    }

    private Double cargarVotos(List<Resultados> resultadosList) {
        Double total = 0D;
        for (Resultados r: resultadosList){
            total += r.getVotosCantidad();
        }
        return total;
    }

    private List<Agrupaciones> cargarAgrupaciones(List<Resultados> resultadosList) {
        List<Agrupaciones> agrupaciones = new ArrayList<>();
        for (Resultados r: resultadosList){
            Agrupaciones a = new Agrupaciones();
            if (r.getAgrupacionId() != 0 && !agrupaciones.contains(getAgrupacionById(r.getAgrupacionId()))){
                agrupaciones.add(getAgrupacionById(r.getAgrupacionId()));
            } else if (r.getAgrupacionId() == 0) {
                boolean tipoVotoExiste = agrupaciones.stream()
                        .anyMatch(agrupacion -> agrupacion.getAgrupacionNombre().equals(r.getVotosTipo()));

                if (!tipoVotoExiste) {
                    a.setAgrupacionId(0L);
                    a.setAgrupacionNombre(r.getVotosTipo());
                    agrupaciones.add(a);
                }
            }
        }
        return agrupaciones;
    }

    private String getAgrupacionGanadora(List<ResultadosAgrupacionesDTO> resultadosList) {
        String nombre = "";

        for (ResultadosAgrupacionesDTO r: resultadosList){
            nombre = r.getNombre();
            break;
        }
        return nombre;
    }

    private Double calcularPorcentajeNacional(Long totalVotantes) {
        List<DistritoDTO> distritoDTOS = getAllDistritos();
        Double totalPais = 0D;
        for (DistritoDTO distritoDTO:distritoDTOS){
            List<Resultados> r = getResultadoByDistritoId(distritoDTO.getId());
            totalPais += calcularVotantes(r);
        }
        double resultado = totalVotantes/totalPais;
        BigDecimal bd = new BigDecimal(Double.toString(resultado));
        bd = bd.setScale(4, RoundingMode.HALF_UP);

        return bd.doubleValue();
    }

    private List<String> cargarSecciones(Long id) {
        List<SeccionDTO> seccions = getSecciones(id,null);
        List<String> seccionesResponse = new ArrayList<>();

        for (SeccionDTO s:seccions){
            seccionesResponse.add(s.getNombre());
        }
        return seccionesResponse;
    }

    private Long calcularVotantes(List<Resultados> resultadosList) {
        Long cantidad = 0L;
        for (Resultados r:resultadosList){
            cantidad += r.getVotosCantidad();
        }
        return cantidad;
    }


}
