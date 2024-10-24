package com.entradas.sociosFutbol.ventaEntradas;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class Entrada {


        public Integer id;

        public String precio;

        public String tipoEntrada;


        public boolean isSocio;




        public void setIsSocio(boolean b) {
                isSocio = b; // Asegúrate de que este setter se utiliza correctamente

        }
}
