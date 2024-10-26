package com.entradas.sociosFutbol.socios.dao;

import com.entradas.sociosFutbol.socios.SocioExtractor;
import com.entradas.sociosFutbol.socios.models.Socio;
import com.entradas.sociosFutbol.ventaEntradas.Entrada;
import com.entradas.sociosFutbol.ventaEntradas.EntradasExtractor;
import com.entradas.sociosFutbol.ventaEntradas.ventaEntradasDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.logging.Logger;
@Repository

public class SocioDao {

    private Connection connection;

    @Autowired
    private JdbcTemplate jdbcTemplate;


    private static Logger logger = Logger.getLogger(ventaEntradasDao.class.getName());
    public List<Socio> getAllSocios() throws Exception {

        logger.info("Inicio get all Socios");

        List<Socio> socios = null;

        socios = jdbcTemplate.query("SELECT * FROM socio", new SocioExtractor());

        logger.info("Fin get all Socios");

        return socios;


    }




    public boolean existsByDni(String dni) {
        String sql = "SELECT COUNT(*) FROM socio WHERE dni = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, dni);
        return count != null && count > 0;
    }

    public void addSocio(Socio socio) {
            String user = "INSERT INTO socio (nombre, apellido, email, telefono, dni) VALUES (?, ?, ?, ?, ?)";
            jdbcTemplate.update(user, socio.getNombre(), socio.getApellido(), socio.getEmail(), socio.getTelefono(), socio.getDni());

        logger.info("el socio es "+ user);
    }
}

