package com.peluditos;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.servicios.PostGuarderia;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class guarderias extends AppCompatActivity {

    private static final String TAG = "PELUDITOS";
    private EditText txtNit;
    private EditText txtRazonSocial;
    private EditText txtUbicacion;
    private EditText txtObjetivo;
    private Button btnGuardar;
    private Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guarderias);

        txtNit = (EditText) findViewById(R.id.txtNit);
        txtRazonSocial = (EditText) findViewById(R.id.txtRazonSocial);
        txtUbicacion = (EditText) findViewById(R.id.txtUbicacion);
        txtObjetivo = (EditText) findViewById(R.id.txtObjetivo);
        btnGuardar = findViewById(R.id.btnGuardar);

        retrofit = new Retrofit.Builder()
                .baseUrl("http://peluditos.online/rest/index.php/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        String estado = getIntent().getStringExtra("actualizar");
        if(estado.equals("1")){
            String id = getIntent().getStringExtra("id");
            String razonSocial = getIntent().getStringExtra("nombre");
            String objetivo = getIntent().getStringExtra("objetivo");
            String ubicacion = getIntent().getStringExtra("ubicacion");
            String nit = getIntent().getStringExtra("nit");

            txtNit.setEnabled(false);
            txtRazonSocial.setEnabled(false);
            txtUbicacion.setEnabled(false);
            txtObjetivo.setEnabled(false);

            txtNit.setText(nit);
            txtRazonSocial.setText(razonSocial);
            txtUbicacion.setText(ubicacion);
            txtObjetivo.setText(objetivo);

            btnGuardar.setVisibility(View.INVISIBLE);
        }
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                consumirServicio();
            }
        });
    }
    public void consumirServicio(){
        // ahora ejecutamos el hilo creado
        try {
            if(txtNit.getText().toString().trim().isEmpty()
                    || txtRazonSocial.getText().toString().trim().isEmpty()
                    || txtObjetivo.getText().toString().trim().isEmpty()
                    || txtUbicacion.getText().toString().trim().isEmpty()){

                Toast.makeText(getBaseContext(), "Campos Vacios, Diligencie todos los campos", Toast.LENGTH_LONG).show();

            }else{
                String nit = txtNit.getText().toString().trim();
                String razonSocial = txtRazonSocial.getText().toString().trim();
                String objetivo = txtObjetivo.getText().toString().trim();
                String ubicacion = txtUbicacion.getText().toString().trim();
                String tipo = "Guarderia";


                sendPost(nit, razonSocial, objetivo, ubicacion, tipo);

                txtNit.setText("");
                txtRazonSocial.setText("");
                txtObjetivo.setText("");
                txtUbicacion.setText("");

                Toast.makeText(getBaseContext(), "Guardado con Exito", Toast.LENGTH_LONG).show();
            }

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void sendPost(String nit, String razonSocial, String objetivo, String ubicacion,
                         String tipo) {

        PostGuarderia service = retrofit.create(PostGuarderia.class);
        Call<String> call = service.savePost(nit, razonSocial, objetivo,
                ubicacion, tipo);

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
