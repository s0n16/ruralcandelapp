package com.example.ruralcandelappf;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ReservarApartamentos extends AppCompatActivity {

    private String nombre;
    private String apellido1;
    private String telefono;

    private String usuario;
    private String fechaEntrada;
    private String fechaSalida;
    private int noches;
    private double precioNoche = 50.00;
    private boolean supletoria;
    private boolean cuna;
    private double mascota;
    Date fechaInicio;
    Date fechaFin;

    String mensaje1 = "Va a realizar una reserva de ";
    String mensaje2 = " noches, el precio total será de ";
    String mensaje3 = " euros. ¿Quiere confirmar la reserva?";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservar_apartamentos);

        // Obtener los valores de nombre y apellido1 del Intent
        nombre = getIntent().getStringExtra("nombre");
        apellido1 = getIntent().getStringExtra("apellido1");
        telefono = getIntent().getStringExtra("telefono");

        String fechaEntradaString;
        String fechaSalidaString;

        TextView tvFechaEntrada = findViewById(R.id.tvFechaEntrada);
        EditText etFechaEntrada = findViewById(R.id.etFechaEntrada);
        Button btnAbrirCalendario = findViewById(R.id.btnAbrirCalendario);
        Button btnAbrirCalendario2 = findViewById(R.id.btnAbrirCalendario2);
        TextView tvFechaSalida = findViewById(R.id.tvFechaSalida);
        EditText etFechaSalida = findViewById(R.id.etFechaSalida);


        // BOTÓN ABRIR CALENDARIO
        btnAbrirCalendario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int anio = cal.get(Calendar.YEAR);
                int mes = cal.get(Calendar.MONTH);
                int dia = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(ReservarApartamentos.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String fechaEntrada = dayOfMonth + "/" + (month + 1) + "/" + year;

                        // Muestra la fecha en el EditText
                        etFechaEntrada.setText(fechaEntrada);

                    }
                }, anio, mes, dia);
                datePickerDialog.show();
            }
        });

        // BOTÓN ABRIR CALENDARIO2
        btnAbrirCalendario2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int anio = cal.get(Calendar.YEAR);
                int mes = cal.get(Calendar.MONTH);
                int dia = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(ReservarApartamentos.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String fechaSalida = dayOfMonth + "/" + (month + 1) + "/" + year;

                        // Muestra la fecha en el EditText
                        etFechaSalida.setText(fechaSalida);

                    }
                }, anio, mes, dia);
                datePickerDialog.show();
            }
        });

        // Mostrar el resultado
        Switch switchSupletoria = findViewById(R.id.switchSupletoria);
        boolean estadoSwitchSupletoria = switchSupletoria.isChecked();

        Switch switchCuna = findViewById(R.id.switchCuna);
        boolean estadoSwitchCuna = switchCuna.isChecked();



        SeekBar seekBar = findViewById(R.id.seekBar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // No se realiza ninguna acción al cambiar el progreso de la SeekBar
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // No se realiza ninguna acción al tocar la SeekBar
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // No se realiza ninguna acción al dejar de tocar la SeekBar
            }
        });

        Button btnReservar = findViewById(R.id.btnReservar);
        btnReservar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtenemos las fechas del EditText
                String fechaEntradaStr = etFechaEntrada.getText().toString();
                String fechaSalidaStr = etFechaSalida.getText().toString();

                // Creamos objetos Calendar para cada fecha
                Calendar calEntrada = Calendar.getInstance();
                Calendar calSalida = Calendar.getInstance();

                // Parseamos las fechas y las asignamos a los objetos Calendar
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                try {
                    Date fechaEntrada = sdf.parse(fechaEntradaStr);
                    Date fechaSalida = sdf.parse(fechaSalidaStr);
                    calEntrada.setTime(fechaEntrada);
                    calSalida.setTime(fechaSalida);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                // Obtenemos la diferencia en milisegundos entre las dos fechas
                long diferenciaMs = calSalida.getTimeInMillis() - calEntrada.getTimeInMillis();

                // Convertimos la diferencia a días
                int dias = (int) (diferenciaMs / (1000 * 60 * 60 * 24));

                int numMascotas = seekBar.getProgress();
                int precioNocheMascotas = numMascotas * 5;

                precioNoche = precioNoche + precioNocheMascotas;

                boolean estadoSwitchCuna = switchCuna.isChecked();
                boolean estadoSwitchSupletoria = switchSupletoria.isChecked();

                if (estadoSwitchCuna) {
                    precioNoche = precioNoche + 10.00;
                }
                if (estadoSwitchSupletoria) {
                    precioNoche = precioNoche + 10.00;
                }

                double precioTotal = precioNoche * dias;


                usuario = nombre + " " + apellido1;

                // Crea un objeto de la clase ReservaApartamentos
                ReservaApartamentos reservaApartamentos = new ReservaApartamentos(usuario, telefono, fechaEntradaStr, fechaSalidaStr, dias, estadoSwitchSupletoria, estadoSwitchCuna, numMascotas, precioTotal);

                // Obtiene una instancia de Firebase Realtime Database
                FirebaseDatabase database = FirebaseDatabase.getInstance();

                // Obtener una referencia a la raíz de la base de datos
                DatabaseReference myRef = database.getReference();

                // Se utiliza una nueva referencia a reservasRestaurante
                DatabaseReference reservaApartamentosRef = myRef.child("reservasApartamentos");

                // Genera un nuevo ID para la reserva de apartamentos
                String reservaId = reservaApartamentosRef.push().getKey();

                // Guarda la reserva de apartamentos en la base de datos usando el ID generado
                reservaApartamentosRef.child(reservaId).setValue(reservaApartamentos);

            // Crea el diálogo de alerta
                AlertDialog.Builder builder = new AlertDialog.Builder(ReservarApartamentos.this);
                builder.setMessage(mensaje1 + dias + mensaje2 + precioTotal + mensaje3)
                        .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // Guarda la reserva de apartamentos en la base de datos usando el ID generado
                                reservaApartamentosRef.child(reservaId).setValue(reservaApartamentos);

                                // Muestra un mensaje de éxito
                                Toast.makeText(ReservarApartamentos.this, "Reserva realizada correctamente", Toast.LENGTH_SHORT).show();

                                // Redirige a la pantalla de inicio
                                Intent intent = new Intent(ReservarApartamentos.this, Inicio.class);
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





                // Vuelve a la pantalla de inicio
                Intent intent = new Intent(ReservarApartamentos.this, Inicio.class);
                //pasando los datos del usuario
                intent.putExtra("nombre", nombre);
                intent.putExtra("apellido1", apellido1);
                intent.putExtra("telefono", telefono);
                startActivity(intent);
            }
        });
    }
}
