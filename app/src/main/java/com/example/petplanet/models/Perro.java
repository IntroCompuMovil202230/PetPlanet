package com.example.petplanet.models;

import android.graphics.Bitmap;

import java.sql.Date;

public class Perro {
    String nombredueno;
    String nombrecompleto;
    String raza;
    String sexo;
    String color;
    String Horario;
    Date fechanacimiento;
    Boolean vacunado;
    Boolean esterilizado;
    int  foto;

    public Perro() {
    }


    public Perro(String nombredueno, String nombrecompleto, String raza, String sexo, String color, String horario, Date fechanacimiento, Boolean vacunado, Boolean esterilizado, int foto) {
        this.nombredueno = nombredueno;
        this.nombrecompleto = nombrecompleto;
        this.raza = raza;
        this.sexo = sexo;
        this.color = color;
        this.Horario = horario;
        this.fechanacimiento = fechanacimiento;
        this.vacunado = vacunado;
        this.esterilizado = esterilizado;
        this.foto = foto;
    }



    public Perro(String nombredueno,String nombrecompleto,int  foto) {
        this.nombredueno = nombredueno;
        this.nombrecompleto = nombrecompleto;
        this.foto = foto;
    }

    public String getNombredueno() {
        return nombredueno;
    }

    public void setNombredueno(String nombredueno) {
        this.nombredueno = nombredueno;
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

    public Date getFechanacimiento() {
        return fechanacimiento;
    }

    public void setFechanacimiento(Date fechanacimiento) {
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

    public int  getFoto() {
        return foto;
    }

    public void setFoto(int  foto) {
        this.foto = foto;
    }
}
