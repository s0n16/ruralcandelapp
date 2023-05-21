package com.example.ruralcandelappf;

import java.util.HashMap;
import java.util.Map;

public class Resena {
    private String usuario;
    private String fecha;
    private String comentario;
    private float valoracion;

    public Resena() {

    }

    public Resena(String usuario, String fecha, String comentario, float valoracion) {
        this.usuario = usuario;
        this.fecha = fecha;
        this.comentario = comentario;
        this.valoracion = valoracion;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public float getValoracion() {
        return valoracion;
    }

    public void setValoracion(float valoracion) {
        this.valoracion = valoracion;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("usuario", usuario);
        result.put("fecha", fecha);
        result.put("comentario", comentario);
        result.put("valoracion", valoracion);
        return result;
    }

    @Override
    public String toString() {
        return "Resena{" +
                "usuario='" + usuario + '\'' +
                ", fecha='" + fecha + '\'' +
                ", comentario='" + comentario + '\'' +
                ", valoracion=" + valoracion +
                '}';
    }
}
