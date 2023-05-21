package com.example.ruralcandelappf;

public class ReservaApartamentos {

    private String usuario;
    private String telefono;
    private String fechaEntrada;
    private String fechaSalida;
    private int noches;
    private boolean Supletoria;
    private boolean Cuna;
    private int mascotas;
    private double precio;


    public ReservaApartamentos(String usuario, String telefono, String fechaEntrada, String fechaSalida, int noches, boolean supletoria, boolean cuna, int mascotas, double precio) {
        this.usuario = usuario;
        this.telefono = telefono;
        this.fechaEntrada = fechaEntrada;
        this.fechaSalida = fechaSalida;
        this.noches = noches;
        this.Supletoria = supletoria;
        this.Cuna = cuna;
        this.mascotas = mascotas;
        this.precio = precio;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getFechaEntrada() {
        return fechaEntrada;
    }

    public String getFechaSalida() {
        return fechaSalida;
    }

    public int getNoches() {
        return noches;
    }

    public boolean isSupletoria() {
        return Supletoria;
    }

    public boolean isCuna() {
        return Cuna;
    }

    public int getMascotas() {
        return mascotas;
    }

    public double getPrecio() {
        return precio;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public void setFechaEntrada(String fechaEntrada) {
        this.fechaEntrada = fechaEntrada;
    }

    public void setFechaSalida(String fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public void setNoches(int noches) {
        this.noches = noches;
    }

    public void setSupletoria(boolean supletoria) {
        Supletoria = supletoria;
    }

    public void setCuna(boolean cuna) {
        Cuna = cuna;
    }

    public void setMascotas(int mascotas) {
        this.mascotas = mascotas;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}
