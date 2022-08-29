package com.example.petplanet;

import android.graphics.Bitmap;

public class Usuario {
    private String nombre;
    private String localidad;
    private String correo;
    private String contrasena;
    private String direccion;
    private String telefono;
    private int foto;
    Boolean isWalker;
    String experiencia;
    String linkhojadevida;
    String linkdocumentoid;

    public Usuario() {
    }

    public Usuario(String nombre, String localidad, String correo, String contrasena, String direccion, String telefono, int foto, Boolean isWalker, String experiencia, String linkhojadevida, String linkdocumentoid) {
        this.nombre = nombre;
        this.localidad = localidad;
        this.correo = correo;
        this.contrasena = contrasena;
        this.direccion = direccion;
        this.telefono = telefono;
        this.foto = foto;
        this.isWalker = isWalker;
        this.experiencia = experiencia;
        this.linkhojadevida = linkhojadevida;
        this.linkdocumentoid = linkdocumentoid;
    }
    public Usuario(String nombre,String telefono,String experiencia,int foto){
        this.nombre=nombre;
        this.telefono=telefono;
        this.experiencia = experiencia;
        this.foto=foto;
    }



    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public int getFoto() {
        return foto;
    }

    public void setFoto(int foto) {
        this.foto = foto;
    }

    public Boolean getWalker() {
        return isWalker;
    }

    public void setWalker(Boolean walker) {
        isWalker = walker;
    }

    public String getExperiencia() {
        return experiencia;
    }

    public void setExperiencia(String experiencia) {
        this.experiencia = experiencia;
    }

    public String getLinkhojadevida() {
        return linkhojadevida;
    }

    public void setLinkhojadevida(String linkhojadevida) {
        this.linkhojadevida = linkhojadevida;
    }

    public String getLinkdocumentoid() {
        return linkdocumentoid;
    }

    public void setLinkdocumentoid(String linkdocumentoid) {
        this.linkdocumentoid = linkdocumentoid;
    }
}
