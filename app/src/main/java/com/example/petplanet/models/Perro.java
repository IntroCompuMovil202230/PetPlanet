package com.example.petplanet.models;

import android.graphics.Bitmap;

import java.sql.Date;

public class Perro {
    String nombrecompleto;
    String raza;
    String sexo;
    String color;
    String Horario;
    String fechanacimiento;
    Boolean vacunado;
    Boolean esterilizado;
    Boolean disponibleparapaseo;
    Boolean redomendacionesespeciales;
    String recomendaciones;
    String foto;

    public Perro() {
    }


    public Perro(String nombrecompleto, String raza, String sexo, String color, String fechanacimiento, Boolean vacunado, Boolean esterilizado, String foto, Boolean redomendacionesespeciales, String recomendaciones) {
        this.nombrecompleto = nombrecompleto;
        this.raza = raza;
        this.sexo = sexo;
        this.color = color;
        this.fechanacimiento = fechanacimiento;
        this.vacunado = vacunado;
        this.esterilizado = esterilizado;
        this.foto = foto;
        this.redomendacionesespeciales = redomendacionesespeciales;
        if (redomendacionesespeciales) {
            this.recomendaciones = recomendaciones;
        }
    }

    public Perro(String nombredelperro, String foto) {
        this.nombrecompleto = nombredelperro;
        this.foto = foto;
    }


    public Perro(String nombredueno, String nombrecompleto, String foto) {
        this.nombrecompleto = nombrecompleto;
        this.foto = foto;
    }


    public Boolean getRedomendacionesespeciales() {
        return redomendacionesespeciales;
    }

    public void setRedomendacionesespeciales(Boolean redomendacionesespeciales) {
        this.redomendacionesespeciales = redomendacionesespeciales;
    }

    public String getRecomendaciones() {
        return recomendaciones;
    }

    public void setRecomendaciones(String recomendaciones) {
        this.recomendaciones = recomendaciones;
    }

    public Boolean getDisponibleparapaseo() {
        return disponibleparapaseo;
    }

    public void setDisponibleparapaseo(Boolean disponibleparapaseo) {
        this.disponibleparapaseo = disponibleparapaseo;
    }

    public String getHorario() {
        return Horario;
    }

    public void setHorario(String horario) {
        this.Horario = horario;
    }

    public String getNombrecompleto() {
        return nombrecompleto;
    }

    public void setNombrecompleto(String nombrecompleto) {
        this.nombrecompleto = nombrecompleto;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getFechanacimiento() {
        return fechanacimiento;
    }

    public void setFechanacimiento(String fechanacimiento) {
        this.fechanacimiento = fechanacimiento;
    }

    public Boolean getVacunado() {
        return vacunado;
    }

    public void setVacunado(Boolean vacunado) {
        this.vacunado = vacunado;
    }

    public Boolean getEsterilizado() {
        return esterilizado;
    }

    public void setEsterilizado(Boolean esterilizado) {
        this.esterilizado = esterilizado;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}
