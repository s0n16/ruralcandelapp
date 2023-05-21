package com.example.ruralcandelappf;

import java.security.NoSuchAlgorithmException;

public class Usuario {

    private String nombre;
    private String apellido1;
    private String apellido2;
    private String telefono;
    private String email;
    private String contrasena;
    private String pais;
    private String ciudad;


    public Usuario(String nombre, String apellido1, String apellido2, String telefono, String email, String contrasena, String pais, String ciudad) {
        this.nombre = nombre;
        this.apellido1 = apellido1;
        this.apellido2 = apellido2;
        this.telefono = telefono;
        this.email = email;
        this.contrasena = contrasena;
        this.pais = pais;
        this.ciudad = ciudad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido1() {
        return apellido1;
    }

    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    public String getApellido2() {
        return apellido2;
    }

    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }




    @Override
    public String toString() {
        return "Usuario{" +
                "nombre='" + nombre + '\'' +
                ", apellido1='" + apellido1 + '\'' +
                ", apellido2='" + apellido2 + '\'' +
                ", telefono='" + telefono + '\'' +
                ", email='" + email + '\'' +
                ", contrasena='" + contrasena + '\'' +
                ", pais='" + pais + '\'' +
                ", ciudad='" + ciudad + '\'' +
                '}';
    }

    //comprueba que el email contenga @, . y que tenga una longitud de 8 o más caracteres
    public static boolean comprobarEmail(String email) {
        boolean comprobar = false;
        if (email.contains("@") && email.contains(".") && email.length() >= 8) {
            comprobar = true;
        }
        return comprobar;
    }



    //comprueba que el telefono tenga 9 digitos
    public static boolean comprobarTelefono9Digitos(String telefono) {
        boolean comprobar = false;
        if (telefono.length() == 9) {
            comprobar = true;
        }
        return comprobar;
    }

    public static boolean comprobarCamposNoVacios(String nombre, String apellido1, String apellido2, String telefono, String pais, String ciudad, String email, String contrasena, String contrasena2) {
        boolean comprobar = false;
        if (!nombre.isEmpty() && !apellido1.isEmpty() && !apellido2.isEmpty() && !telefono.isEmpty() && !pais.isEmpty() && !ciudad.isEmpty() && !email.isEmpty() && !contrasena.isEmpty() && !contrasena2.isEmpty()) {
            comprobar = true;
        }
        return comprobar;
    }

    //comprueba que las contraseñas coinciden
    public static boolean comprobarContrasenasCoinciden(String contrasena, String contrasena2) {
        boolean comprobar = false;
        if (contrasena.equals(contrasena2)) {
            comprobar = true;
        }
        return comprobar;
    }


    //comprueba que el telefono solo tenga numeros
    public static boolean comprobarSiEsNumero(String telefono) {
        boolean comprobar = false;
        String numeros = "0123456789";
        for (int i = 0; i < telefono.length(); i++) {
            if (numeros.indexOf(telefono.charAt(i)) == -1) {
                comprobar = false;
                break;
            } else {
                comprobar = true;
            }
        }
        return comprobar;
    }

    //comprueba que la contraseña tenga al menos 8 caracteres, una mayuscula, una minuscula, un numero y un caracter especial
    public static boolean comprobarFormatoContrasena(String contrasena, String s) {
        boolean comprobar = false;
        if (contrasena.length() >= 8 &&
                contrasena.matches(".*[a-z].*") &&
                contrasena.matches(".*[A-Z].*") &&
                contrasena.matches(".*\\d.*") &&
                contrasena.matches(".*[!@#$%^&*].*")) {
            comprobar = true;
        }
        return comprobar;
    }

    //encripta la contraseña
    public static String encriptarContrasena(String contrasena) {
        String contrasenaEncriptada = null;
        try {
            contrasenaEncriptada = PasswordHasher.hashPassword(contrasena);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return contrasenaEncriptada;
    }
}
