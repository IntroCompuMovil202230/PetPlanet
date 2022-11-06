package com.example.petplanet.models;

import java.util.ArrayList;

public class Paseo {
    private String id;
    private String fotodelperro;
    private String nombredelowner;
    private String nombredelperro;
    private String nombredelwalker;
    private String fecha;
    private String hora;
    private String direcciondelowner;
    private String Localidad;
    private String distanciarecorrida;
    private String duracion;

    public Paseo(String fotodelperro, String nombredelowner, String nombredelperro, String localidad, String fecha, String hora, String direcciondelowner, String nombredelwalker) {
        this.fotodelperro = fotodelperro;
        this.nombredelowner = nombredelowner;
        this.nombredelperro = nombredelperro;
        this.Localidad = localidad;
        this.fecha = fecha;
        this.hora = hora;
        this.nombredelwalker = nombredelwalker;
        this.direcciondelowner = direcciondelowner;
    }

    public Paseo() {

    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombredelwalker() {
        return nombredelwalker;
    }

    public void setNombredelwalker(String nombredelwalker) {
        this.nombredelwalker = nombredelwalker;
    }

    public String getFotodelperro() {
        return fotodelperro;
    }

    public void setFotodelperro(String fotodelperro) {
        this.fotodelperro = fotodelperro;
    }

    public String getDuracion() {
        return duracion;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }

    public String getDistanciarecorrida() {
        return distanciarecorrida;
    }

    public void setDistanciarecorrida(String distanciarecorrida) {
        this.distanciarecorrida = distanciarecorrida;
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
