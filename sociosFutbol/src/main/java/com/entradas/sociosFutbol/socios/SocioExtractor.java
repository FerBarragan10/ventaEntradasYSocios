package com.entradas.sociosFutbol.socios;

import com.entradas.sociosFutbol.socios.models.Socio;
import com.entradas.sociosFutbol.ventaEntradas.Entrada;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SocioExtractor implements RowMapper<Socio> {


    public Socio mapRow(ResultSet rs, int rowNum) throws SQLException {

        Socio socio = new Socio();
        socio.setNombre(String.valueOf(rs.getString("nombre")));
        socio.setApellido(String.valueOf(rs.getString("apellido")));
        socio.setEmail(String.valueOf(rs.getString("email")));
        socio.setTelefono(String.valueOf(rs.getString("telefono")));
        socio.setDni(String.valueOf(rs.getString("dni")));

        return socio;

    }
}

