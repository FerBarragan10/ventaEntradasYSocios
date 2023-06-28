package com.entradas.sociosFutbol.ventaEntradas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/")
public class ventaEntradasController{


        @Autowired
        private static Logger logger=Logger.getLogger(ventaEntradasController.class.getName());

        @Autowired
         private ventaEntradasService ventaEntradasService;

        @GetMapping("/test")
        public String test(){
            logger.info("llega aca");
            return"app venta entradas Works!";
        }

         @GetMapping("/getAllEntradas")
            public List<Entrada> obtenerAllEntradas()throws Exception{
            logger.info("inicio get all entradas");
             List<Entrada> entradas = ventaEntradasService.getAllEntradas();

             return entradas;
        }


//        @GetMapping("/prueba")
//        public String getIsMantenimiento(){
//        String isMantenimiento="0";
//        try{
//        isMantenimiento=pruebaService.getIsMantenimiento();
//        }catch(Exception e) {
//        logger.info("Exception GesrhController getIsMantenimiento: "+e.getMessage());
//        }
//        logger.info("Fin ApiController getIsMantenimiento");
//        return isMantenimiento;
//        }
}