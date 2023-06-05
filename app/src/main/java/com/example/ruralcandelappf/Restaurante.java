package com.example.ruralcandelappf;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.ruralcandelappf.adaptadores.ImagePagerAdapter;

public class Restaurante extends AppCompatActivity {

    private int[] imageIds = { R.drawable.crema, R.drawable.cenanoche, R.drawable.tronco1, R.drawable.ximenez, R.drawable.tomate, R.drawable.tartafruta, R.drawable.cochifrito, R.drawable.ventanarestaurante, R.drawable.restauranteregalos, R.drawable.restauranteblanconegro, R.drawable.tabule, R.drawable.solomillo, R.drawable.restaurante1 };
    public String nombre;
    public String apellido1;
    public String telefono;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurante);

        // Obtener los valores de nombre y apellido1 del Intent
        String nombre = getIntent().getStringExtra("nombre");
        String apellido1 = getIntent().getStringExtra("apellido1");
        String telefono = getIntent().getStringExtra("telefono");

        ViewPager viewPager = findViewById(R.id.vpRestaurante);
        ImagePagerAdapter adapter = new ImagePagerAdapter(imageIds);
        viewPager.setAdapter(adapter);

        Button btnReservar = findViewById(R.id.btnReservar);

        btnReservar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nombre != null && apellido1 != null && telefono != null) {

                    Intent intent = new Intent(Restaurante.this, com.example.ruralcandelappf.ReservarRestaurante.class);
                    intent.putExtra("nombre", nombre);
                    intent.putExtra("apellido1", apellido1);
                    intent.putExtra("telefono", telefono);
                    startActivity(intent);
                }else {
                    Toast.makeText(Restaurante.this, "Es necesario iniciar sesión", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Restaurante.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}