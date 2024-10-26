package com.entradas.sociosFutbol.socios.service;

import com.entradas.sociosFutbol.socios.dao.SocioDao;
import com.entradas.sociosFutbol.socios.models.Socio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SocioService {
    @Autowired
    SocioDao socioDao;
    public List<Socio> getAllSocios()throws Exception {
        List<Socio> socio=null;
        socio=socioDao.getAllSocios();
        return socio;
    }

    public Socio agregarSocio(Socio socio) throws Exception {
        if (socioDao.existsByDni(socio.getDni())) {
            throw new Exception("El socio ya está registrado");
        }
        socioDao.addSocio(socio);
        return socio;
    }


    //public Entrada updateEntrada(Integer id, Entrada entrada) throws Exception {
        // Aquí podrías agregar validaciones antes de llamar al DAO
      //  return ventaEntradasDao.updateEntrada(id, entrada);
    //}
}
