package com.example.ruralcandelappf;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Inicio extends AppCompatActivity {

    public String nombre;
    public String apellido1;
    public String telefono;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        // Obtener los valores de nombre y apellido1 del Intent
        String nombre = getIntent().getStringExtra("nombre");
        String apellido1 = getIntent().getStringExtra("apellido1");
        String telefono = getIntent().getStringExtra("telefono");


        TextView txtUsuario = findViewById(R.id.txtUsuario);
        ImageView imgRestaurante = findViewById(R.id.imgRestaurante);
        Button btnRestaurante = findViewById(R.id.btnRestaurante);
        ImageView imgApartamentos = findViewById(R.id.imgApartamentos);
        Button btnApartamentos = findViewById(R.id.btnApartamentos);
        ImageView imgResenas = findViewById(R.id.imgResenas);
        Button btnResenas = findViewById(R.id.btnResenas);
        ImageView imgContacto = findViewById(R.id.imgContacto);
        Button btnContacto = findViewById(R.id.btnContacto);
        ImageView imgCuenta = findViewById(R.id.imgCuenta);
        Button btnCuenta = findViewById(R.id.btnCuenta);

        //si nombre, apellido y telefono son null no se ve el mesaje de bienvenida
        if (nombre == null && apellido1 == null && telefono == null) {
            txtUsuario.setVisibility(View.INVISIBLE);
        }

        txtUsuario.setText("Has iniciado sesión como " + nombre + " " + apellido1);

        btnRestaurante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Inicio.this, Restaurante.class);
                intent.putExtra("nombre", nombre);
                intent.putExtra("apellido1", apellido1);
                intent.putExtra("telefono", telefono);
                startActivity(intent);

            }

        });

        btnApartamentos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Inicio.this, Apartamentos.class);
                intent.putExtra("nombre", nombre);
                intent.putExtra("apellido1", apellido1);
                intent.putExtra("telefono", telefono);
                startActivity(intent);

            }
        });

        btnResenas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Inicio.this, Resenas.class);
                intent.putExtra("nombre", nombre);
                intent.putExtra("apellido1", apellido1);
                intent.putExtra("telefono", telefono);
                startActivity(intent);

            }
        });

        btnContacto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Inicio.this, Contacto.class);
                intent.putExtra("nombre", nombre);
                intent.putExtra("apellido1", apellido1);
                intent.putExtra("telefono", telefono);
                startActivity(intent);

            }
        });

        btnCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (nombre != null && apellido1 != null && telefono != null) {
                    Intent intent = new Intent(Inicio.this, Cuenta.class);
                    intent.putExtra("nombre", nombre);
                    intent.putExtra("apellido1", apellido1);
                    intent.putExtra("telefono", telefono);
                    startActivity(intent);

                }else {
                    Toast.makeText(Inicio.this, "Es necesario iniciar sesión", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Inicio.this, MainActivity.class);
                    startActivity(intent);
                }

            }


        });
    }
}