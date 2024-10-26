package com.entradas.sociosFutbol.socios.controller;

import com.entradas.sociosFutbol.socios.models.Socio;
import com.entradas.sociosFutbol.socios.service.SocioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/socio")
public class SociosController {


    @Autowired
    private static Logger logger=Logger.getLogger(com.entradas.sociosFutbol.socios.controller.SociosController.class.getName());

    @Autowired
    private SocioService socioService;



    @GetMapping("/getAllSocios")
    public List<Socio> obtenerAllSocios()throws Exception{
        logger.info("inicio get all socios");
        List<Socio> socios = socioService.getAllSocios();

        return socios;
    }

    @PostMapping
    public ResponseEntity<Socio> agregarSocio(@RequestBody Socio socio) {
        try {
            Socio nuevoSocio = socioService.agregarSocio(socio);
            return new ResponseEntity<>(nuevoSocio, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT); // Código 409 para conflictos
        }
    }
}
