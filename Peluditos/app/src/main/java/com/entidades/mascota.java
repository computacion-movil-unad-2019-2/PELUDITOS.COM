package com.entidades;

public class mascota {

    public String id;
    public String nombres;
    public String edad;
    public String tamano;
    public String controlMedico;
    public String ciudadReferencia;
    public String ubicacion;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public String getTamano() {
        return tamano;
    }

    public void setTamano(String tamano) {
        this.tamano = tamano;
    }

    public String getControlMedico() {
        return controlMedico;
    }

    public void setControlMedico(String controlMedico) {
        this.controlMedico = controlMedico;
    }

    public String getCiudadReferencia() {
        return ciudadReferencia;
    }

    public void setCiudadReferencia(String ciudadReferencia) {
        this.ciudadReferencia = ciudadReferencia;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }
}
