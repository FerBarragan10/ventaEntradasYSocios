package com.entradas.sociosFutbol.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class TokenService {

    @Autowired
    private RestTemplate restTemplate; // O WebClient

    @Value("${http://localhost:8080/auth}") // URL del backend de autenticación
    private String authServerUrl; // URL del backend de autenticación

    public String login(String username, String password) {
        // Crea el cuerpo de la solicitud para el login
        Map<String, String> loginRequest = new HashMap<>();
        loginRequest.put("nombreUsuario", username);
        loginRequest.put("password", password);

        // Realiza la solicitud de login
        ResponseEntity<String> response = restTemplate.postForEntity(
                authServerUrl + "/login",
                loginRequest,
                String.class
        );

        if (response.getStatusCode() == HttpStatus.OK) {
            // Asume que el token JWT está en el cuerpo de la respuesta
            return response.getBody();
        } else {
            throw new RuntimeException("Error en el login: " + response.getStatusCode());
        }
    }

    public UserDetailsDto validateToken(String token) {
        // Validar el token llamando al backend de autenticación
        ResponseEntity<UserDetailsDto> response = restTemplate.exchange(
                authServerUrl + "/validate",
                HttpMethod.GET,
                new HttpEntity<>(createHeaders(token)),
                UserDetailsDto.class
        );

        if (response.getStatusCode() != HttpStatus.OK) {
            throw new RuntimeException("Token inválido o expirado");
        }

        return response.getBody(); // Devuelve los detalles del usuario
    }

    private HttpHeaders createHeaders(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);
        return headers;
    }
}
