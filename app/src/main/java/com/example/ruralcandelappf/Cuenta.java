package com.example.ruralcandelappf;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Cuenta extends AppCompatActivity {

    public String nombre;
    public String apellido1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuenta);

        // Obtener los valores de nombre y apellido1 del Intent
        String nombre = getIntent().getStringExtra("nombre");
        String apellido1 = getIntent().getStringExtra("apellido1");



        Button btnAjustes = findViewById(R.id.btnAjustes);
        Button btnDarDeBaja = findViewById(R.id.btnDarDeBaja);
        Spinner spnModificar = findViewById(R.id.spnModificar);
        TextView txtJocker = findViewById(R.id.txtJocker);
        TextView txtContrasena2 = findViewById(R.id.txtContrasena2);
        Button btnCambiarContrasena = findViewById(R.id.btnCambiarContrasena);
        Button btnCambiarTelefono = findViewById(R.id.btnCambiarTelefono);
        Button btnCambiarEmail = findViewById(R.id.btnCambiarEmail);
        Button btnCambiarPais = findViewById(R.id.btnCambiarPais);
        Button btnCambiarCiudad = findViewById(R.id.btnCambiarCiudad);


        btnAjustes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spnModificar.setVisibility(View.VISIBLE);
            }
        });

        btnDarDeBaja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spnModificar.setVisibility(View.GONE);
                txtJocker.setVisibility(View.GONE);
                txtContrasena2.setVisibility(View.GONE);
                btnCambiarContrasena.setVisibility(View.GONE);
                btnCambiarTelefono.setVisibility(View.GONE);
                btnCambiarEmail.setVisibility(View.GONE);
                btnCambiarPais.setVisibility(View.GONE);
                btnCambiarCiudad.setVisibility(View.GONE);
            }
        });

        spnModificar.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();

                switch(position) {

                    case 0:
                        btnCambiarCiudad.setVisibility(View.GONE);
                        btnCambiarPais.setVisibility(View.GONE);
                        btnCambiarEmail.setVisibility(View.GONE);
                        btnCambiarTelefono.setVisibility(View.GONE);
                        txtJocker.setVisibility(View.GONE);
                        btnCambiarContrasena.setVisibility(View.GONE);
                        txtContrasena2.setVisibility(View.GONE);


                        break;
                    case 1:
                        // Código para contraseña
                        txtJocker.setVisibility(View.VISIBLE);
                        txtJocker.setHint("Introduce la nueva contraseña");
                        txtContrasena2.setVisibility(View.VISIBLE);
                        btnCambiarContrasena.setVisibility(View.VISIBLE);
                        btnCambiarTelefono.setVisibility(View.GONE);
                        btnCambiarEmail.setVisibility(View.GONE);
                        btnCambiarPais.setVisibility(View.GONE);
                        btnCambiarCiudad.setVisibility(View.GONE);


                        btnCambiarContrasena.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                //comprobar que los campos no están vacíos

                                if(txtJocker.getText().toString().length() > 0 || txtContrasena2.getText().toString().length() > 0){

                                    //comprobar que las contraseñas coinciden


                                    if(Usuario.comprobarFormatoContrasena(txtJocker.getText().toString(), txtContrasena2.getText().toString())){

                                        if(Usuario.comprobarContrasenasCoinciden(txtJocker.getText().toString(), txtContrasena2.getText().toString())) {


                                            //ENCRIPTAR CONTRASEÑA


                                            Usuario.encriptarContrasena(txtJocker.getText().toString());


                                            //Modificar el campo contraseña en la base de datos, en el usuario donde nombre es igual a nombre y apellido1 es igual a apellido1

                                            DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                                            Query query = ref.child("usuarios").orderByChild("nombre").equalTo(nombre);

                                            query.addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                    if (snapshot.exists()) {
                                                        for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                                                            String userID = userSnapshot.getKey();
                                                            ref.child("usuarios").child(userID).child("contrasena").setValue(Usuario.encriptarContrasena(txtJocker.getText().toString())).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                @Override
                                                                public void onSuccess(Void aVoid) {
                                                                    Toast.makeText(getApplicationContext(), "Contraseña cambiada correctamente", Toast.LENGTH_SHORT).show();
                                                                }
                                                            }).addOnFailureListener(new OnFailureListener() {
                                                                @Override
                                                                public void onFailure(@NonNull Exception e) {
                                                                    Toast.makeText(getApplicationContext(), "Error al cambiar la contraseña", Toast.LENGTH_SHORT).show();
                                                                }
                                                            });
                                                        }
                                                    }
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError error) {
                                                    Toast.makeText(getApplicationContext(), "Error al buscar el usuario", Toast.LENGTH_SHORT).show();
                                                }
                                            });



                                        }Toast.makeText(getApplicationContext(), "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();



                                    }Toast.makeText(getApplicationContext(), "Las contraseña debe tener al menos 8 dígitos, una mayúscula, una minúscula, un número y un caracter especial", Toast.LENGTH_SHORT).show();



                                }else {
                                    Toast.makeText(getApplicationContext(), "Los campos no pueden estar vacíos", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        break;
                    case 2:
                        // Código para teléfono
                        txtJocker.setVisibility(View.VISIBLE);
                        txtJocker.setHint("Introduce el nuevo teléfono");
                        btnCambiarTelefono.setVisibility(View.VISIBLE);
                        txtContrasena2.setVisibility(View.GONE);
                        btnCambiarContrasena.setVisibility(View.GONE);
                        btnCambiarEmail.setVisibility(View.GONE);
                        btnCambiarPais.setVisibility(View.GONE);
                        btnCambiarCiudad.setVisibility(View.GONE);

                        btnCambiarTelefono.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                if(Usuario.comprobarTelefono9Digitos(txtJocker.getText().toString())){

                                    if(Usuario.comprobarSiEsNumero(txtJocker.getText().toString())) {

                                        //CAMBIAR TELEFONO EN LA BASE DE DATOS
                                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                                        Query query = ref.child("usuarios").orderByChild("nombre").equalTo(nombre);

                                        query.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                if (snapshot.exists()) {
                                                    for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                                                        String userID = userSnapshot.getKey();
                                                        ref.child("usuarios").child(userID).child("telefono").setValue(txtJocker.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                            @Override
                                                            public void onSuccess(Void aVoid) {
                                                                Toast.makeText(getApplicationContext(), "Contraseña cambiada correctamente", Toast.LENGTH_SHORT).show();
                                                            }
                                                        }).addOnFailureListener(new OnFailureListener() {
                                                            @Override
                                                            public void onFailure(@NonNull Exception e) {
                                                                Toast.makeText(getApplicationContext(), "Error al cambiar la contraseña", Toast.LENGTH_SHORT).show();
                                                            }
                                                        });
                                                    }
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {
                                                Toast.makeText(getApplicationContext(), "Error al buscar el usuario", Toast.LENGTH_SHORT).show();
                                            }
                                        });

                                    }else {
                                        Toast.makeText(getApplicationContext(), "El teléfono debe contener sólo números", Toast.LENGTH_SHORT).show();
                                    }

                                }else {
                                    Toast.makeText(getApplicationContext(), "El teléfono debe tener 9 dígitos", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        break;
                    case 3:
                        // Código para Email
                        txtJocker.setVisibility(View.VISIBLE);
                        txtJocker.setHint("Introduce el nuevo Email");
                        btnCambiarEmail.setVisibility(View.VISIBLE);
                        txtContrasena2.setVisibility(View.GONE);
                        btnCambiarContrasena.setVisibility(View.GONE);
                        btnCambiarTelefono.setVisibility(View.GONE);
                        btnCambiarPais.setVisibility(View.GONE);
                        btnCambiarCiudad.setVisibility(View.GONE);
                        break;
                    case 4:
                        // Código para país
                        txtJocker.setVisibility(View.VISIBLE);
                        txtJocker.setHint("Introduce el nuevo país");
                        btnCambiarPais.setVisibility(View.VISIBLE);
                        txtContrasena2.setVisibility(View.GONE);
                        btnCambiarContrasena.setVisibility(View.GONE);
                        btnCambiarTelefono.setVisibility(View.GONE);
                        btnCambiarEmail.setVisibility(View.GONE);
                        btnCambiarCiudad.setVisibility(View.GONE);
                        break;
                    case 5:
                        // Código para ciudad
                        txtJocker.setVisibility(View.VISIBLE);
                        txtJocker.setHint("Introduce la nueva ciudad");
                        btnCambiarCiudad.setVisibility(View.VISIBLE);
                        txtContrasena2.setVisibility(View.GONE);
                        btnCambiarContrasena.setVisibility(View.GONE);
                        btnCambiarTelefono.setVisibility(View.GONE);
                        btnCambiarEmail.setVisibility(View.GONE);
                        btnCambiarPais.setVisibility(View.GONE);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //ningún item seleccionado
            }
        });

        btnDarDeBaja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Cuenta.this);
                builder.setMessage("¿Seguro que quieres borrar tu cuenta?")
                        .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // Borrar el usuario
                                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("usuarios");
                                Query query = ref.orderByChild("nombre").equalTo(nombre);

                                query.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        if(dataSnapshot.exists()){
                                            // Recorrer los nodos que coinciden con la consulta y borrarlos
                                            for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                                                snapshot.getRef().removeValue();
                                            }
                                            Toast.makeText(getApplicationContext(), "Usuario eliminado correctamente", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(getApplicationContext(), "No se encontró ningún usuario con ese nombre", Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {
                                        Toast.makeText(getApplicationContext(), "Error al borrar el usuario", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // Cancelar el borrado del usuario
                                dialog.cancel();
                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }
}