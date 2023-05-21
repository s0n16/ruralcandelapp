package com.example.ruralcandelappf;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ruralcandelappf.adaptadores.ImagePagerAdapter;
import com.example.ruralcandelappf.adaptadores.ImagePagerAdapter;

public class Apartamentos extends AppCompatActivity {

    private int[] imageIds = { R.drawable.buleriacama3,R.drawable.alegriapuertapersiana, R.drawable.alegriacama1, R.drawable.soleaestudio, R.drawable.alegriacocina, R.drawable.fandangobano, R.drawable.piscina };
    public String nombre;
    public String apellido1;
    public String telefono;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apartamentos);

        // Obtener los valores de nombre y apellido1 del Intent
        String nombre = getIntent().getStringExtra("nombre");
        String apellido1 = getIntent().getStringExtra("apellido1");
        String telefono = getIntent().getStringExtra("telefono");


        ViewPager viewPager = findViewById(R.id.vpApartamentos);
        ImagePagerAdapter adapter = new ImagePagerAdapter(imageIds);
        viewPager.setAdapter(adapter);



        Button btnReservar = findViewById(R.id.btnReservar);
        btnReservar.setOnClickListener(new View.OnClickListener() {

            @Override
                public void onClick(View v) {

                    if(nombre != null && apellido1 != null && telefono != null){
                        Intent intent = new Intent(Apartamentos.this, ReservarApartamentos.class);
                        intent.putExtra("nombre", nombre);
                         intent.putExtra("apellido1", apellido1);
                         intent.putExtra("telefono", telefono);
                    startActivity(intent);
                    }else{

                        Toast.makeText(Apartamentos.this, "Es necesario iniciar sesión para reservar en el restaurante", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(Apartamentos.this, MainActivity.class);
                        startActivity(intent);
                    }
                }
            });
    }
}