package com.example.ruralcandelappf;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.ruralcandelappf.adaptadores.ResenaAdapter;
import com.example.ruralcandelappf.Resena;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Resenas extends AppCompatActivity {

    public String nombre;
    public String apellido1;
    String fecha;

    private RecyclerView mRecyclerView;
    private ResenaAdapter mAdapter;
    private DatabaseReference mDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resenas);

        String nombre = getIntent().getStringExtra("nombre");
        String apellido1 = getIntent().getStringExtra("apellido1");
        String telefono = getIntent().getStringExtra("telefono");

        Button btnDejarResena = findViewById(R.id.btnDejarResena);

        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mDatabaseReference = FirebaseDatabase.getInstance().getReference("resenas");

        // Leer los datos de Firebase
        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Resena> resenasList = new ArrayList<>();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Resena resena = snapshot.getValue(Resena.class);
                    resenasList.add(resena);
                }

                mAdapter = new ResenaAdapter(resenasList);
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Manejar el error en caso de que ocurra
                Toast.makeText(Resenas.this, "Error al leer los datos de Firebase", Toast.LENGTH_SHORT).show();
            }
        });


        btnDejarResena.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nombre != null && apellido1 != null && telefono != null){
                    Intent intent = new Intent(Resenas.this, DejarResena.class);
                    intent.putExtra("nombre", nombre);
                    intent.putExtra("apellido1", apellido1);
                    intent.putExtra("telefono", telefono);
                    startActivity(intent);
                }else{
                    Toast.makeText(Resenas.this, "Debes iniciar sesión para dejar una reseña", Toast.LENGTH_SHORT).show();
                }
            }
        });
        }
    }

