package com.example.ruralcandelappf;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class ReservarRestaurante extends AppCompatActivity {

    String fecha;
    String hora;
    String pax;
    String usuario;
    double precioXpax = 25.00;
    double precio;
    public String nombre;
    public String apellido1;
    public String Telefono;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservar_restaurante);

        // Obtener los valores de nombre y apellido1 del Intent
        String nombre = getIntent().getStringExtra("nombre");
        String apellido1 = getIntent().getStringExtra("apellido1");
        String telefono = getIntent().getStringExtra("telefono");

        usuario = nombre + " " + apellido1;
        Telefono = telefono;

        TextView tvSeleccionaFecha = findViewById(R.id.tvSeleccionaFecha);
        EditText etFecha = findViewById(R.id.etFecha);
        Button btnAbrirCalendario = findViewById(R.id.btnAbrirCalendario);
        TextView tvSeleccionaHora = findViewById(R.id.tvSeleccionaHora);

        //BOTÓN ABRIR CALENDARIO

        btnAbrirCalendario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int anio = cal.get(Calendar.YEAR);
                int mes = cal.get(Calendar.MONTH);
                int dia = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(ReservarRestaurante.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String fecha = dayOfMonth + "/" + (month + 1) + "/" + year;
                        //Muestra la fecha en el EditText
                        etFecha.setText(fecha);
                        //asigna la fecha a la variable fecha
                        ReservarRestaurante.this.fecha = fecha;
                    }
                }, anio, mes, dia);
                datePickerDialog.show();
            }
        });

        Spinner spnComidaCena = findViewById(R.id.spnComidaCena);

        // Obtiene la lista de opciones de strings.xml
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.horas, android.R.layout.simple_spinner_item);

        // Especifica el diseño para el menú desplegable
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Asigna el adaptador al Spinner
        spnComidaCena.setAdapter(adapter);

        //recoge el texto del spinner
        spnComidaCena.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String comidaCena = parent.getItemAtPosition(position).toString();
                Log.d("comidaCena", comidaCena);

                //asigna la hora a la variable hora
                hora = comidaCena;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        NumberPicker numberPicker = findViewById(R.id.numberPicker);

        // Configurar los valores mínimo, máximo e inicial del NumberPicker
        numberPicker.setMinValue(0);
        numberPicker.setMaxValue(40);
        numberPicker.setValue(0);

        // Agregar un listener para escuchar los cambios en el valor seleccionado
        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                // Hacer algo con el nuevo valor seleccionado
                Log.d("NumberPicker", "Nuevo valor seleccionado: " + newVal);

                //asigna el pax a la variable pax
                pax = String.valueOf(newVal);
            }
        });

        Button btnReservar = findViewById(R.id.btnReservar);

        btnReservar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //comprobar que pax no sea 0
                //comprobar que fecha, hora, usuario y pax no estén vacíos

                if (fecha == null || hora == null || usuario == null || pax == null || spnComidaCena.getSelectedItemPosition() == 0) {

                    Toast.makeText(ReservarRestaurante.this, "Seleciona todos los campos", Toast.LENGTH_SHORT).show();

                } else {

                    //calcular el precio

                    precio = Double.parseDouble(pax) * precioXpax;

                    //crea un objeto de la clase ReservaRestaurante
                    ReservaRestaurante reservaRestaurante = new ReservaRestaurante(usuario, fecha, hora, pax, Telefono, precio);

                    // Obtener una instancia de Firebase Realtime Database
                    FirebaseDatabase database = FirebaseDatabase.getInstance();

                    // Obtener una referencia a la raíz de la base de datos
                    DatabaseReference myRef = database.getReference();

                    // Se utiliza una nueva referencia a reservasRestaurante
                    DatabaseReference reservaRestauranteRef = myRef.child("reservaRestaurante");

                    //se genera un id de reserva único
                    String key = reservaRestauranteRef.push().getKey(); // Se genera una clave única

                    String mensaje = "Va a relizar una reserva para el día " + fecha + " a las " + hora + " para " + pax + " personas. El precio total es de " + precio + "€. ¿Está seguro de que quiere realizar la reserva?";


                    // Crea el diálogo de alerta
                    AlertDialog.Builder builder = new AlertDialog.Builder(ReservarRestaurante.this);
                    builder.setMessage(mensaje)
                            .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {


                                    //se guarda la reserva en la base de datos
                                    reservaRestauranteRef.child(key).setValue(reservaRestaurante);


                                    // Muestra un mensaje de éxito
                                    Toast.makeText(ReservarRestaurante.this, "Reserva realizada correctamente", Toast.LENGTH_SHORT).show();

                                    // Redirige a la pantalla de inicio
                                    Intent intent = new Intent(ReservarRestaurante.this, Inicio.class);
                                    startActivity(intent);
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // El usuario ha cancelado la reserva, no se realiza ninguna acción
                                }
                            });

                    // Muestra el diálogo de alerta
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }
        });
    }
}