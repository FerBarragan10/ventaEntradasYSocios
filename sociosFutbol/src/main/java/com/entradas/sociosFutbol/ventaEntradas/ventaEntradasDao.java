package com.entradas.sociosFutbol.ventaEntradas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.logging.Logger;

@Repository
public class ventaEntradasDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;


    private static Logger logger = Logger.getLogger(ventaEntradasDao.class.getName());
    public List<Entrada> getAllEntradas() throws Exception {

        logger.info("Inicio get all entradas");

        List<Entrada> entradas = null;

        entradas = jdbcTemplate.query("SELECT * FROM entrada", new EntradasExtractor());

        logger.info("Fin get all entradas");

        return entradas;

    }
}