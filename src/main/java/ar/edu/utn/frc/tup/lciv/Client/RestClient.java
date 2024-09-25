package ar.edu.utn.frc.tup.lciv.Client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RestClient {

    @Autowired
    RestTemplate restTemplate;

    private static final String URL = "http://server:8081";

    //public ResponseEntity<List<Distrito>> getAllDistritos(){
    //    return restTemplate.exchange(URL + "/distritos", HttpMethod.GET, null,
    //            new ParameterizedTypeReference<List<Distrito>>() {});
    //}
}
