package com.entradas.sociosFutbol.Token;


public class UserDetailsDto {
    private String nombreUsuario;
    private String email;

    // Constructor vacío (requerido para deserialización)
    public UserDetailsDto() {}

    // Getters y setters
    public String getUsername() {
        return nombreUsuario;
    }

    public void setUsername(String username) {
        this.nombreUsuario = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
