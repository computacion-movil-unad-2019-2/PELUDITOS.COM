package com.peluditos;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TimePicker;
import android.widget.Toast;

import com.servicios.PostAgenda;
import com.servicios.PostServiceMascota;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;

import retrofit2.Retrofit;

public class agendar extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "PELUDITOS";
    private EditText txtFecha;
    private EditText txtComentarioAgenda;
    private EditText txtUbicacion;
    private EditText txtHora;
    private Button btnAgendar;
    private Retrofit retrofit;
    ImageButton ibObtenerFecha;
    ImageButton ibObtenerHora;

    public String getIdMascota() {
        return idMascota;
    }

    public void setIdMascota(String idMascota) {
        this.idMascota = idMascota;
    }

    public String getIdAdoptante() {
        return idAdoptante;
    }

    public void setIdAdoptante(String idAdoptante) {
        this.idAdoptante = idAdoptante;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getNombreMascota() {
        return nombreMascota;
    }

    public void setNombreMascota(String nombreMascota) {
        this.nombreMascota = nombreMascota;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String idMascota;
    public String idAdoptante;
    public String correo;
    public String nombreMascota;
    public String comentario;


    private static final String CERO = "0";
    private static final String BARRA = "/";
    private static final String DOS_PUNTOS = ":";

    //Calendario para obtener fecha & hora
    public final Calendar c = Calendar.getInstance();

    //Variables para obtener la fecha
    final int mes = c.get(Calendar.MONTH);
    final int dia = c.get(Calendar.DAY_OF_MONTH);
    final int anio = c.get(Calendar.YEAR);

    //Variables para obtener la hora hora
    final int hora = c.get(Calendar.HOUR_OF_DAY);
    final int minuto = c.get(Calendar.MINUTE);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agendar);

        txtFecha = (EditText) findViewById(R.id.txtFechaAgenda);
        txtHora = (EditText) findViewById(R.id.txtHoraAgenda);
        txtUbicacion = (EditText) findViewById(R.id.txtUbicacionAgenda);
        txtComentarioAgenda = (EditText) findViewById(R.id.txtComentarioAgenda);

        //Widget ImageButton del cual usaremos el evento clic para obtener la fecha
        ibObtenerFecha = (ImageButton) findViewById(R.id.ib_obtener_fecha);
        ibObtenerHora = (ImageButton) findViewById(R.id.ib_obtener_hora);
        //Evento setOnClickListener - clic
        ibObtenerHora.setOnClickListener(this);
        ibObtenerFecha.setOnClickListener(this);

        txtFecha.setText("");
        txtHora.setText("");
        txtUbicacion.setText("");
        txtComentarioAgenda.setText("");

        retrofit = new Retrofit.Builder()
                .baseUrl("http://peluditos.online/rest/index.php/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        btnAgendar = findViewById(R.id.btnAgendar);
        btnAgendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    String strFecha = txtFecha.getText().toString();
                    String strHora = txtHora.getText().toString();
                    String strUbicacion = txtUbicacion.getText().toString();
                    String strComentario = txtComentarioAgenda.getText().toString();

                    if(strFecha.toString().trim().isEmpty()
                            || strHora.toString().trim().isEmpty()
                            || strUbicacion.toString().trim().isEmpty()
                            || strComentario.toString().trim().isEmpty()){

                        Toast.makeText(getBaseContext(), "un campo esta vacio, y no se pudo Agendar la Cita", Toast.LENGTH_LONG).show();

                    }else{
                        sendPost(strFecha,strHora,strUbicacion,strComentario);
                    }
                }catch(Exception e){
                    e.printStackTrace();
                    Toast.makeText(getBaseContext(), "Error "+ e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    public void sendPost(String strFecha,String strHora,String strUbicacion,String strComentario){
        try{

            idMascota = getIntent().getStringExtra("id");
            idAdoptante = getIntent().getStringExtra("idAdoptante");
            correo = getIntent().getStringExtra("correo");
            nombreMascota = getIntent().getStringExtra("nombreMascota");
            comentario = strComentario;

            //Log.i(TAG, "Responsestring :"+ idMascota+" "+idAdoptante+" "+correo+" "+nombreMascota+" "+comentario);

            PostAgenda service = retrofit.create(PostAgenda.class);
            Call<String> call = service.savePost(strComentario,idMascota,idAdoptante,strFecha,strHora,strUbicacion);
            actualizaMascota();
            enviarCorreo(correo,comentario);
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    Log.i(TAG, "Responsestring :"+ response.body().toString());
                    if(response.isSuccessful()) {
                        if(response.body() != null) {
                            Log.i(TAG, "onSuccess." + response.body().toString());
                        }else{
                            Log.i(TAG, "Returned empty response");
                        }
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Log.e(TAG, "Unable to submit post to API.");
                }
            });
            finish();
            //Log.e(TAG, "onResponse:"+ strFecha+" "+strHora);
        }catch(Exception e){
            e.printStackTrace();
            Toast.makeText(getBaseContext(), "Error "+ e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ib_obtener_fecha:
                obtenerFecha();
                break;
            case R.id.ib_obtener_hora:
                obtenerHora();
                break;
        }
    }

    private void obtenerHora(){
        TimePickerDialog recogerHora = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                //Formateo el hora obtenido: antepone el 0 si son menores de 10
                String horaFormateada =  (hourOfDay < 10)? String.valueOf(CERO + hourOfDay) : String.valueOf(hourOfDay);
                //Formateo el minuto obtenido: antepone el 0 si son menores de 10
                String minutoFormateado = (minute < 10)? String.valueOf(CERO + minute):String.valueOf(minute);
                //Obtengo el valor a.m. o p.m., dependiendo de la selección del usuario
                String AM_PM;
                if(hourOfDay < 12) {
                    AM_PM = "a.m.";
                } else {
                    AM_PM = "p.m.";
                }
                //Muestro la hora con el formato deseado
                txtHora.setText(horaFormateada + DOS_PUNTOS + minutoFormateado + " " + AM_PM);
            }
            //Estos valores deben ir en ese orden
            //Al colocar en false se muestra en formato 12 horas y true en formato 24 horas
            //Pero el sistema devuelve la hora en formato 24 horas
        }, hora, minuto, false);

        recogerHora.show();
    }

    private void obtenerFecha(){
        DatePickerDialog recogerFecha = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                //Esta variable lo que realiza es aumentar en uno el mes ya que comienza desde 0 = enero
                final int mesActual = month + 1;
                //Formateo el día obtenido: antepone el 0 si son menores de 10
                String diaFormateado = (dayOfMonth < 10)? CERO + String.valueOf(dayOfMonth):String.valueOf(dayOfMonth);
                //Formateo el mes obtenido: antepone el 0 si son menores de 10
                String mesFormateado = (mesActual < 10)? CERO + String.valueOf(mesActual):String.valueOf(mesActual);
                //Muestro la fecha con el formato deseado
                txtFecha.setText(diaFormateado + BARRA + mesFormateado + BARRA + year);
            }
            //Estos valores deben ir en ese orden, de lo contrario no mostrara la fecha actual
            /**
             *También puede cargar los valores que usted desee
             */
        },anio, mes, dia);
        //Muestro el widget
        recogerFecha.show();

    }

    private void enviarCorreo(String Email, String mensaje){

        String[] recipients = Email.split(",");
        Log.i(TAG, "Email :"+ Email+" "+correo);

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL, recipients);
        intent.putExtra(Intent.EXTRA_SUBJECT, "Agendar Cita Adopción");
        intent.putExtra(Intent.EXTRA_TEXT, mensaje);

        intent.setType("message/rfc822");
        startActivity(Intent.createChooser(intent,"Seleccione su cliente de correo"));
    }

    public void actualizaMascota()
    {
        String id = getIntent().getStringExtra("id");
        String nombres = getIntent().getStringExtra("nombreMascota");
        String edad = getIntent().getStringExtra("edad");
        String tamano = getIntent().getStringExtra("tamano");
        String controlMedico = getIntent().getStringExtra("controlMedico");
        String ciudadReferencia = getIntent().getStringExtra("ciudadReferencia");
        String ubicacion = getIntent().getStringExtra("ubicacion");
        String tipo = getIntent().getStringExtra("tipo");
        String foto = getIntent().getStringExtra("foto");
        String strEstado = "En Proceso";

        guardarMascota(id, nombres, edad,
                tamano, controlMedico, ciudadReferencia, ubicacion, tipo, strEstado);
    }

    public void guardarMascota(String id, String nombres, String edad, String tamano,
                         String controlMedico, String ciudadReferencia, String ubicacion, String tipo, String estado) {

        Log.i(TAG, "Actualiza Mascota :"+ id+" "+nombres+" "+estado);
        PostServiceMascota service = retrofit.create(PostServiceMascota.class);
        Call<String> call = service.savePost(id, nombres, edad,
                tamano, controlMedico, ciudadReferencia, ubicacion, tipo, estado);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.i(TAG, "Responsestring :"+ response.body().toString());
                if(response.isSuccessful()) {
                    if(response.body() != null) {
                        Log.i(TAG, "onSuccess." + response.body().toString());
                    }else{
                        Log.i(TAG, "Returned empty response");
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e(TAG, "Unable to submit post to API.");
            }
        });
    }


}
