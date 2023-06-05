package com.example.ruralcandelappf;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.security.NoSuchAlgorithmException;

public class MainActivity extends AppCompatActivity {

    private TextView txtPrueba;
    private DatabaseReference mDatabase;
    private int contador = 0;
    private Boolean usuarioExiste = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView imgLogo = findViewById(R.id.imgLogo);
        EditText txtEmail = findViewById(R.id.txtEmail);
        EditText txtContrasena = findViewById(R.id.txtContrasena);
        Button btnIniciarSesion = findViewById(R.id.btnIniciarSesion);
        Button btnRegistrarse = findViewById(R.id.btnRegistrarse);
        Button btnSinSesion = findViewById(R.id.btnSinSesion);

        txtContrasena.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

        txtEmail.setText("");
        txtContrasena.setText("");



        //ACCIÓN DE BTNINICIARSESION

        btnIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //COMPROBAR QUE LOS CAMPOS NO ESTEN VACIOS
                if (txtEmail.getText().toString().isEmpty() || txtContrasena.getText().toString().isEmpty()) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Rellene todos los campos", Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    //SI LOS CAMPOS NO ESTAN VACIOS
                    // COMPROBAR QUE EL EL EMAIL Y LA CONTRASEÑA ESTÉN EN LA BASE DE DATOS

                    mDatabase = FirebaseDatabase.getInstance().getReference();
                    mDatabase.child("usuarios").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot ds : snapshot.getChildren()) {
                                String email = ds.child("email").getValue().toString();
                                String contrasena = ds.child("contrasena").getValue().toString();
                                //introdución de un contador para que solo salga el mensaje de email o contraseña incorrectos una vez
                                contador++;


                                String contrasenaEncriptada = Usuario.encriptarContrasena(txtContrasena.getText().toString());

                                if (email.equals(txtEmail.getText().toString()) && contrasena.equals(contrasenaEncriptada)) {

                                    //SI EL EMAIL Y LA CONTRASEÑA ESTÁN EN LA BASE DE DATOS

                                    //introducción de un booleano para que el mensaje de email o contraseña incorrectos no salga si el usuario existe
                                    usuarioExiste = true;
                                    //EXTRAER EL NOMBRE DEL USUARIO, APELLIDOS Y EMAIL
                                    String nombre = ds.child("nombre").getValue().toString();
                                    String apellido1 = ds.child("apellido1").getValue().toString();
                                    String telefono = ds.child("telefono").getValue().toString();

                                    //MENSAJE DE BIENVENIDA PERSONALIZADO
                                    Toast toast = Toast.makeText(getApplicationContext(), "Bienvenido/a " + nombre + " " + apellido1, Toast.LENGTH_SHORT);
                                    toast.show();

                                    //IR A LA PANTALLA DE INICIO
                                    Intent intent = new Intent(MainActivity.this, Inicio.class);
                                    intent.putExtra("nombre", nombre);
                                    intent.putExtra("apellido1", apellido1);
                                    intent.putExtra("telefono", telefono);
                                    startActivity(intent);


                                } else {
                                    if (contador == snapshot.getChildrenCount()-1 && !usuarioExiste) {
                                        Toast toast = Toast.makeText(getApplicationContext(), "Email o contraseña incorrectos", Toast.LENGTH_SHORT);
                                        toast.show();
                                        txtContrasena.setError("Email o contraseña incorrectos");
                                        txtEmail.setError("Email o contraseña incorrectos");
                                    }

                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });

        //ACCIÓN DE BTNREGISTRARSE
        btnRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Registro.class);
                startActivity(intent);
            }
        });

        //ACCIÓN DE BTNSINSESION
        btnSinSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Inicio.class);
                startActivity(intent);
            }
        });
    }
}

