package com.example.petplanet.models;

import java.util.ArrayList;

public class Paseo {
    private String id;
    private String fotodelperro;
    private String nombredelowner;
    private String nombredelperro;
    private String nombredelwalker;
    private String uidWalker;
    private String fecha;
    private String hora;
    private String horaderecogida;
    private String horaderegreso;
    private String direcciondelowner;
    private String Localidad;
    private String distanciarecorrida;
    private String duracion;

    private boolean seacaboelpaseo;
    private boolean yallegoelpaseador;
    private boolean yatengoelperro;
    private boolean yarecibielperro;


    private double Latitud;
    private double Longitud;
    private double latitudwalker;
    private double longitudwalker;



    public Paseo(String fotodelperro, String nombredelowner, String nombredelperro, String localidad, String fecha, String hora, String direcciondelowner,String duracion) {
        this.fotodelperro = fotodelperro;
        this.nombredelowner = nombredelowner;
        this.nombredelperro = nombredelperro;
        this.Localidad = localidad;
        this.fecha = fecha;
        this.hora = hora;
        this.direcciondelowner = direcciondelowner;
        this.duracion = duracion;
    }

    public Paseo() {

    }


    public boolean isYarecibielperro() {
        return yarecibielperro;
    }

    public void setYarecibielperro(boolean yarecibielperro) {
        this.yarecibielperro = yarecibielperro;
    }

    public boolean isYatengoelperro() {
        return yatengoelperro;
    }

    public void setYatengoelperro(boolean yatengoelperro) {
        this.yatengoelperro = yatengoelperro;
    }

    public boolean isYallegoelpaseador() {
        return yallegoelpaseador;
    }

    public void setYallegoelpaseador(boolean yallegoelpaseador) {
        this.yallegoelpaseador = yallegoelpaseador;
    }

    public String getHoraderecogida() {
        return horaderecogida;
    }

    public void setHoraderecogida(String horaderecogida) {
        this.horaderecogida = horaderecogida;
    }

    public String getHoraderegreso() {
        return horaderegreso;
    }

    public void setHoraderegreso(String horaderegreso) {
        this.horaderegreso = horaderegreso;
    }

    public String getUidWalker() {
        return uidWalker;
    }

    public void setUidWalker(String uidWalker) {
        this.uidWalker = uidWalker;
    }

    public boolean isSeacaboelpaseo() {
        return seacaboelpaseo;
    }

    public void setSeacaboelpaseo(boolean seacaboelpaseo) {
        this.seacaboelpaseo = seacaboelpaseo;
    }

    public double getLatitudwalker() {
        return latitudwalker;
    }

    public void setLatitudwalker(double latitudwalker) {
        this.latitudwalker = latitudwalker;
    }

    public double getLongitudwalker() {
        return longitudwalker;
    }

    public void setLongitudwalker(double longitudwalker) {
        this.longitudwalker = longitudwalker;
    }

    public double getLatitud() {
        return Latitud;
    }

    public void setLatitud(double latitud) {
        Latitud = latitud;
    }

    public double getLongitud() {
        return Longitud;
    }

    public void setLongitud(double longitud) {
        Longitud = longitud;
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
