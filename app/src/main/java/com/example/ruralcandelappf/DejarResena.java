package com.example.ruralcandelappf;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class DejarResena extends AppCompatActivity {

    public String nombre;
    public String apellido1;
    String fecha;

    LocalDate fechaActual = LocalDate.now();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dejar_resena);

        String nombre = getIntent().getStringExtra("nombre");
        String apellido1 = getIntent().getStringExtra("apellido1");
        String telefono = getIntent().getStringExtra("telefono");


        EditText txtComentarioResena = findViewById(R.id.txtComentarioResena);
        RatingBar ratingBar = findViewById(R.id.ratingBar);
        Button btnConfirmarResena = findViewById(R.id.btnConfirmarResena);



        btnConfirmarResena.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float valoracion = ratingBar.getRating();
                String comentario = txtComentarioResena.getText().toString();

                String usuario = nombre + " " + apellido1;
                fecha = fechaActual.toString();

                //comprobar que el comentario tiene la longitud requerida
                if (comentario.length() < 14) {
                    Toast.makeText(DejarResena.this, "El comentario debe tener al menos 14 caracteres", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    //crear objeto de la clase Resena
                    Resena resena = new Resena(usuario, fecha, comentario, valoracion);

                    // Obtener una instancia de Firebase Realtime Database
                    FirebaseDatabase database = FirebaseDatabase.getInstance();

                    // Obtener una referencia a la raíz de la base de datos
                    DatabaseReference myRef = database.getReference();

                    // Se utiliza una nueva referencia a reservasRestaurante
                    DatabaseReference resenaRef = myRef.child("resenas");

                    //se genera un id de reserva único
                    String key = resenaRef.push().getKey(); // Se genera una clave única

                    // se crea un Map para guardar los valores de la reseña
                    Map<String, Object> reseñaValues = resena.toMap();

                    // se crea un Map para actualizar los valores de la reseña en la base de datos
                    Map<String, Object> childUpdates = new HashMap<>();
                    childUpdates.put(key, reseñaValues);

                    // se actualizan los valores en la base de datos
                    resenaRef.updateChildren(childUpdates);

                    //mensaje de confirmación
                    Toast.makeText(DejarResena.this, "Reseña enviada", Toast.LENGTH_SHORT).show();
                    //volver a la actividad anterior
                    finish();
                }
            }
        });
    }
}

