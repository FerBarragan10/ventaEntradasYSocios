package com.entradas.sociosFutbol.SociosTest;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.entradas.sociosFutbol.socios.models.Socio;
import com.entradas.sociosFutbol.socios.service.SocioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest // Asegúrate de que Spring Boot pueda encontrar tu clase principal
class SocioServiceTest { // Asegúrate de que el nombre de la clase esté correctamente capitalizado

    @Autowired
    private SocioService sociosService;

//    @BeforeEach
//    void setUp() {
//        // Limpiar la base de datos o la tabla de socios para evitar datos previos en los tests
//        sociosService.eliminarTodosLosSocios();
//    }

    @Test
    void testAgregarSocioRepetido() throws Exception {
        Socio socio1 = new Socio();
        socio1.setNombre("fernando");
        socio1.setApellido("barragan");
        socio1.setEmail("juan.perez@example.com");
        socio1.setTelefono("1234567890");
        socio1.setDni("35775259");

        // Agregar el socio por primera vez
        sociosService.agregarSocio(socio1);

        // Intentar agregar el mismo socio de nuevo debe lanzar una excepción
        Exception exception = assertThrows(Exception.class, () -> {
            sociosService.agregarSocio(socio1);
        });

        // Verificar el mensaje de la excepción
        assertEquals("El socio ya está registrado", exception.getMessage());
    }
}
