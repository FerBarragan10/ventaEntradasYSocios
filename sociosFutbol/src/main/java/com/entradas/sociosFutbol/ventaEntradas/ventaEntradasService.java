package com.entradas.sociosFutbol.ventaEntradas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ventaEntradasService {
    @Autowired
    ventaEntradasDao ventaEntradasDao;
    public List<Entrada> getAllEntradas()throws Exception {
        List<Entrada> entrada=null;
        entrada=ventaEntradasDao.getAllEntradas();
        return entrada;
    }



    public Entrada updateEntrada(Integer id, Entrada entrada) throws Exception {
        // Aquí podrías agregar validaciones antes de llamar al DAO
        return ventaEntradasDao.updateEntrada(id, entrada);
    }
}
