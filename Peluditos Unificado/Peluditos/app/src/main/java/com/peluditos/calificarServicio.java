package com.peluditos;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import com.servicios.PostCalificarServicio;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class calificarServicio extends AppCompatActivity {

    private static final String TAG = "PELUDITOS";
    private RadioButton Excelente;
    private RadioButton Buena;
    private RadioButton Regular;
    private RadioButton Mala;
    private String Calificacion;
    private Button btnEnviar;
    private Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calificar_servicio);

        Excelente = (RadioButton) findViewById(R.id.rbExcelente);
        Buena = (RadioButton) findViewById(R.id.rbBuena);
        Regular = (RadioButton) findViewById(R.id.rbRegular);
        Mala = (RadioButton) findViewById(R.id.rbMala);
        btnEnviar = findViewById(R.id.btnEnviarC);

        retrofit = new Retrofit.Builder()
                .baseUrl("http://peluditos.online/rest/index.php/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String id = getIntent().getStringExtra("id");

                if (Excelente.isChecked() == true) {
                    Calificacion = "Excelente";
                    Toast.makeText(getBaseContext(), "Gracias por tu opinión", Toast.LENGTH_LONG).show();
                    sendPost(Calificacion);
                } else if (Buena.isChecked() == true) {
                    Calificacion = "Buena";
                    Toast.makeText(getBaseContext(), "Gracias por tu opinión", Toast.LENGTH_LONG).show();
                    sendPost(Calificacion);
                } else if (Regular.isChecked() == true) {
                    Calificacion = "Regular";
                    Toast.makeText(getBaseContext(), "Gracias por tu opinión", Toast.LENGTH_LONG).show();
                    sendPost(Calificacion);
                } else if (Mala.isChecked() == true) {
                    Calificacion = "Mala";
                    Toast.makeText(getBaseContext(), "Gracias por tu opinión", Toast.LENGTH_LONG).show();
                    sendPost(Calificacion);
                } else {
                    Toast.makeText(getBaseContext(), "Selecciona una calificación", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    public void sendPost(String calificacion) {

        PostCalificarServicio service = retrofit.create(PostCalificarServicio.class);
        Call<String> call = service.savePost(calificacion);

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
