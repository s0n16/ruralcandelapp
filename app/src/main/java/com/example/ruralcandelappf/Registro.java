package com.example.ruralcandelappf;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Registro extends AppCompatActivity {


    private String numeros = "0123456789";
    private Boolean camposNoVacios = false;
    private Boolean contrasenasCoinciden = false;
    private Boolean telefono9Digitos = false;
    private Boolean telefonoEsNumero = false;
    private Boolean emailValido = false;
    private Boolean contrasenaValida = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);



        ImageView imgLogo = findViewById(R.id.imgLogo);
        EditText txtNombre = findViewById(R.id.txtNombre);
        EditText txtApellido1 = findViewById(R.id.txtApellido1);
        EditText txtapellido2 = findViewById(R.id.txtApellido2);
        EditText txtTelefono = findViewById(R.id.txtTelefono);
        EditText txtPais = findViewById(R.id.txtPais);
        EditText txtCiudad = findViewById(R.id.txtCiudad);
        EditText txtEmail = findViewById(R.id.txtEmail);
        EditText txtContrasena = findViewById(R.id.txtContrasena);
        EditText txtContrasena2 = findViewById(R.id.txtContrasena2);
        Button btnRegistrarse = findViewById(R.id.btnRegistrarse);
        ;

        // Obtener una instancia de Firebase Realtime Database
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        // Obtener una referencia a la raíz de la base de datos
        DatabaseReference myRef = database.getReference();

        // Se utiliza una nueva referencia a usuarios
        DatabaseReference usuariosRef = myRef.child("usuarios");

        //se genera un id de usuario único
        String key = usuariosRef.push().getKey(); // Se genera una clave única




        //Acción de btnRegistarse
        btnRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //COMPROBAR QUE LOS CAMPOS NO ESTAN VACÍOS

                if(Usuario.comprobarCamposNoVacios(txtNombre.getText().toString(), txtApellido1.getText().toString(),
                        txtapellido2.getText().toString(), txtTelefono.getText().toString(),
                        txtPais.getText().toString(), txtCiudad.getText().toString(),
                        txtEmail.getText().toString(), txtContrasena.getText().toString(),
                        txtContrasena2.getText().toString()))
                {
                    camposNoVacios = true;
                }else {


                    txtNombre.setError("Rellena este campo");
                    txtApellido1.setError("Rellena este campo");
                    txtapellido2.setError("Rellena este campo");
                    txtTelefono.setError("Rellena este campo");
                    txtPais.setError("Rellena este campo");
                    txtCiudad.setError("Rellena este campo");
                    txtEmail.setError("Rellena este campo");
                    txtContrasena.setError("Rellena este campo");
                    txtContrasena2.setError("Rellena este campo");
                }


                //COMPROBAR QUE LAS CONTRASEÑAS COINCIDEN

                if(Usuario.comprobarContrasenasCoinciden(txtContrasena.getText().toString(), txtContrasena2.getText().toString()))
                {
                    contrasenasCoinciden = true;
                }else {
                    txtContrasena.setError("Las contraseñas no coinciden");
                    txtContrasena2.setError("Las contraseñas no coinciden");
                    txtContrasena.setText("");
                    txtContrasena2.setText("");
                }

                //COMPROBAR QUE EL TELEFONO TIENE 9 DIGITOS

                if(Usuario.comprobarTelefono9Digitos(txtTelefono.getText().toString()))
                {
                    telefono9Digitos = true;
                }else {
                    txtTelefono.setError("El telefono debe contener 9 digitos numéricos");
                    txtTelefono.setText("");
                }


                //COMPROBAR QUE EL TELEFONO ES NUMÉRICO

                if(Usuario.comprobarSiEsNumero(txtTelefono.getText().toString())) {
                    telefonoEsNumero = true;
                }else {
                    txtTelefono.setError("El telefono debe contener 9 digitos numéricos");
                    txtTelefono.setText("");
                }


                //COMPROBAR QUE EL EMAIL TIENE UN FORMATO CORRECTO

                if(Usuario.comprobarEmail(txtEmail.getText().toString())) {
                    emailValido = true;
                }else {
                    txtEmail.setError("El email no tiene un formato correcto");
                    txtEmail.setText("");
                }


                //COMPROBAR QUE LA CONTRASEÑA TIENE UN FORMATO CORRECTO

                if(Usuario.comprobarFormatoContrasena(txtContrasena.getText().toString(), txtContrasena2.getText().toString())) {
                    contrasenaValida = true;
                }else {
                    txtContrasena.setError("La contraseña debe contener al menos 8 caracteres, una mayúscula, una minúscula y un número");
                    txtContrasena.setText("");
                }


                //COMPROBAR QUE SE DAN TODAS LAS VALIDACIONES ANTERIORES

                if(camposNoVacios && contrasenasCoinciden && telefono9Digitos && telefonoEsNumero && emailValido && contrasenaValida)
                {
                    //ENCRIPTAR CONTRASEÑA
                    String contrasenaEncriptada = Usuario.encriptarContrasena(txtContrasena.getText().toString());

                    //CREAR OBJETO USUARIO
                    Usuario usuario = new Usuario(txtNombre.getText().toString(),
                            txtApellido1.getText().toString(), txtapellido2.getText().toString(),
                            txtTelefono.getText().toString(), txtEmail.getText().toString(),
                            contrasenaEncriptada
                            , txtPais.getText().toString(),
                            txtCiudad.getText().toString());

                    //GUARDAR EL USUARIO EN LA BASE DE DATOS
                    usuariosRef.child(key).setValue(usuario);

                    myRef.child("usuario").child("id").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() { //listener cuando la tarea se completa


                        @Override
                        public void onComplete(@NonNull Task<DataSnapshot> task) {//dataSnapShot, toda la información de un nodo determinado

                            //SI LA TAREA SE HA COMPLETADO CORRECTAMENTE
                            //MENSAJE DE REGISTRO CORRECTO
                            //IR A LA PANTALLA DE LOGIN

                            if (task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "Te has registrado correctamente", Toast.LENGTH_SHORT).show();

                                //ir a la pantalla de inicio
                                Intent intent = new Intent(Registro.this, MainActivity.class);
                                startActivity(intent);
                            }
                        }
                    });
                }
            }
        });
    }
}
