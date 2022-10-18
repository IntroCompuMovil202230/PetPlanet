package com.example.petplanet.models;

public class Paseo {
    private String nombredelowner;
    private String nombredelperro;
    private String fecha;
    private String hora;
    private String direcciondelowner;
    private String Localidad;

    public Paseo(String nombredelowner, String nombredelperro,String Localidad, String fecha, String hora, String direcciondelowner) {
        this.nombredelowner = nombredelowner;
        this.nombredelperro = nombredelperro;
        this.Localidad = Localidad;
        this.fecha = fecha;
        this.hora = hora;
        this.direcciondelowner = direcciondelowner;
    }

    public String getLocalidad() {
        return Localidad;
    }

    public void setLocalidad(String localidad) {
        Localidad = localidad;
    }

    public String getNombredelowner() {
        return nombredelowner;
    }

    public void setNombredelowner(String nombredelowner) {
        this.nombredelowner = nombredelowner;
    }

    public String getNombredelperro() {
        return nombredelperro;
    }

    public void setNombredelperro(String nombredelperro) {
        this.nombredelperro = nombredelperro;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getDirecciondelowner() {
        return direcciondelowner;
    }

    public void setDirecciondelowner(String direcciondelowner) {
        this.direcciondelowner = direcciondelowner;
    }
}
