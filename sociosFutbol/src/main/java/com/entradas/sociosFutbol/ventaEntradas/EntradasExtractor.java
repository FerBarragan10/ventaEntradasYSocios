package com.entradas.sociosFutbol.ventaEntradas;

import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

public class EntradasExtractor  implements RowMapper<Entrada> {


public Entrada mapRow(ResultSet rs, int rowNum) throws SQLException {

        Entrada entrada = new Entrada();
        entrada.setId((rs.getInt("id")));
        entrada.setPrecio(String.valueOf(rs.getString("precio")));
        entrada.setTipoEntrada(String.valueOf(rs.getString("tipo")));
        entrada.setSocio(rs.getBoolean("is_socio"));
        return entrada;

        }
}