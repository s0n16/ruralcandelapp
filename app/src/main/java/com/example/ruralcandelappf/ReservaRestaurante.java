package com.example.ruralcandelappf;

public class ReservaRestaurante {

    private static String usuario;
    private static String fecha;
    private static String hora;
    private static String pax;
    private static String telefono;
    private static double precio;

    public ReservaRestaurante(String usuario, String fecha, String hora, String pax, String telefono, double precio) {
        this.usuario = usuario;
        this.fecha = fecha;
        this.hora = hora;
        this.pax = pax;
        this.precio = precio;
        this.telefono = telefono;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getFecha() {
        return fecha;
    }

    public String getHora() {
        return hora;
    }

    public String getPax() {
        return pax;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public void setPax(double pax) {
        this.pax = String.valueOf(pax);
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public double getPrecio() {
        return precio;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    @Override
    public String toString() {
        return "ReservaRestaurante{" +
                "usuario='" + usuario + '\'' +
                ", fecha='" + fecha + '\'' +
                ", hora='" + hora + '\'' +
                ", pax='" + pax + '\'' +
                ", telefono='" + telefono + '\'' +
                ", precio=" + precio +
                '}';
    }

}
