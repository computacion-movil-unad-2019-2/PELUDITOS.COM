package com.peluditos;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.servicios.PostEvento;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class eventos extends AppCompatActivity {

    private static final String TAG = "PELUDITOS";
    private EditText Codigo;
    private EditText Nombre;
    private EditText Lugar;
    private EditText Hora;
    private EditText Fecha;
    private Button btnGuardarEvento;
    private Retrofit retrofit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventos);

        Codigo = (EditText) findViewById(R.id.txtCodigo);
        Nombre = (EditText) findViewById(R.id.txtNombre);
        Lugar = (EditText) findViewById(R.id.txtLugar);
        Hora = (EditText) findViewById(R.id.txtHora);
        Fecha = (EditText) findViewById(R.id.txtFecha);
        btnGuardarEvento = findViewById(R.id.btnGuardarEv);

        retrofit = new Retrofit.Builder()
                .baseUrl("http://peluditos.online/rest/index.php/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        //Toast.makeText(getBaseContext(), "Entro al evento", Toast.LENGTH_LONG).show();

        String estado = getIntent().getStringExtra("actualizar");
        if(estado.equals("1")){
            String id = getIntent().getStringExtra("id");
            String codigo = getIntent().getStringExtra("codigo");
            String nombre = getIntent().getStringExtra("nombre");
            String lugar = getIntent().getStringExtra("lugar");
            String hora = getIntent().getStringExtra("hora");
            String fecha = getIntent().getStringExtra("fecha");

            Codigo.setText(codigo);
            Nombre.setText(nombre);
            Lugar.setText(lugar);
            Hora.setText(hora);
            Fecha.setText(fecha);

            Codigo.setEnabled(false);
            Nombre.setEnabled(false);
            Lugar.setEnabled(false);
            Hora.setEnabled(false);
            Fecha.setEnabled(false);

            btnGuardarEvento.setVisibility(View.INVISIBLE);

    }

      btnGuardarEvento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String id = getIntent().getStringExtra("id");
                try {
                    if(Codigo.getText().toString().trim().isEmpty()
                            || Nombre.getText().toString().trim().isEmpty()
                            || Lugar.getText().toString().trim().isEmpty()
                            || Hora.getText().toString().trim().isEmpty()
                            || Fecha.getText().toString().trim().isEmpty()){

                        Toast.makeText(getBaseContext(), "Existe un campo vacio, no se puede guardar el evento", Toast.LENGTH_LONG).show();

                    }else {

                        String codigo = Codigo.getText().toString().trim();
                        String nombre = Nombre.getText().toString().trim();
                        String lugar = Lugar.getText().toString().trim();
                        String hora = Hora.getText().toString().trim();
                        String fecha = Fecha.getText().toString().trim();

                        sendPost(codigo, nombre, lugar, hora, fecha);

                        Codigo.setText("");
                        Nombre.setText("");
                        Lugar.setText("");
                        Hora.setText("");
                        Fecha.setText("");

                        Toast.makeText(getBaseContext(), "Guardado con Exito", Toast.LENGTH_LONG).show();
                        //onBackPressed();
                    }
                }catch(Exception e){
                    e.printStackTrace();
                    Toast.makeText(getBaseContext(), "Error "+ e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void sendPost(String codigo, String nombre, String lugar, String hora,
                         String fecha) {

        PostEvento service = retrofit.create(PostEvento.class);
        Call<String> call = service.savePost(codigo, nombre, lugar,
                hora, fecha);

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

