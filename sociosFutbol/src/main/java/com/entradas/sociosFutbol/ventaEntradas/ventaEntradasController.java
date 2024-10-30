package com.entradas.sociosFutbol.ventaEntradas;

import com.entradas.sociosFutbol.Token.TokenService;
import com.entradas.sociosFutbol.security.JwtRequestFilter;
import com.entradas.sociosFutbol.security.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.SignatureException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api")
public class ventaEntradasController{


        @Autowired
        private static Logger logger=Logger.getLogger(ventaEntradasController.class.getName());

        @Autowired
         private ventaEntradasService ventaEntradasService;

        @Autowired
            private TokenService tokenService;
    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("API funcionando correctamente");
    }

    @GetMapping("/getAllEntradas")
    public ResponseEntity<?> obtenerAllEntradas(@RequestHeader("Authorization") String token) {
        Map<String, Object> response = new HashMap<>();

        try {
            // Validar que el token esté presente y empiece con "Bearer "
            if (token == null || !token.startsWith("Bearer ")) {
                response.put("error", "Token no provisto o malformado");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            // Extraer el JWT del token y validar
            String jwt = token.substring(7);
            if (!jwtUtil.validateToken(jwt)) {
                response.put("error", "Token no válido o expirado");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }

            // Obtener el nombre de usuario desde el token si es válido
            String username = jwtUtil.extractUsername(jwt);
            response.put("username", username);

            // Obtener todas las entradas desde el servicio
            List<Entrada> entradas = ventaEntradasService.getAllEntradas();
            if (entradas.isEmpty()) {
                response.put("mensaje", "No se encontraron entradas");
                return ResponseEntity.ok(response);
            }

            // Retornar el resultado de la lista de entradas junto con el usuario
            response.put("entradas", entradas);
            response.put("valid", true);
            return ResponseEntity.ok(response);

        } catch (ExpiredJwtException e) {
            response.put("error", "El token ha expirado");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);

        } catch (SignatureException e) {
            response.put("error", "La firma del token no es válida");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);

        } catch (MalformedJwtException e) {
            response.put("error", "Token malformado");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);

        } catch (Exception e) {
            response.put("error", "Error al procesar la solicitud: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }


//    @GetMapping("/getEntradas")
//    public ResponseEntity<List<Entrada>> obtenerAllEntradas()throws Exception {
//        try {
//            //UserDetailsDto userDetails = tokenService.validateToken(token);
//
//            // Si el token es válido, obtener las entradas
//            List<Entrada> entradas = ventaEntradasService.getAllEntradas();
//            return ResponseEntity.ok(entradas); // Devuelve la lista de entradas en un ResponseEntity
//        } catch (RuntimeException e) {
//            // Manejar el error de token inválido
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
//        }
//    }


    @GetMapping("/validateToken")
    public ResponseEntity<Map<String, Object>> validateToken(@RequestHeader("Authorization") String token) {
        Map<String, Object> response = new HashMap<>();
        try {
            if (token != null && token.startsWith("Bearer ")) {
                String jwt = token.substring(7);
                boolean isValid = jwtUtil.validateToken(jwt);
                response.put("valid", isValid);
                response.put("username", jwtUtil.extractUsername(jwt));
                return ResponseEntity.ok(response);
            }
            response.put("error", "No Bearer token provided");
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
