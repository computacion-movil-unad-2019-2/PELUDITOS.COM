package com.peluditos;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.servicios.PostDonacionDinero;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DonacionesDinero extends AppCompatActivity {

    private static final String TAG = "PELUDITOS";
    private EditText txtNit;
    private EditText txtRazonSocial;
    private EditText txtCuenta;
    private EditText txtBanco;
    private Button btnGuardar;
    private Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donaciones_dinero);

        txtNit = (EditText) findViewById(R.id.txtNit);
        txtRazonSocial = (EditText) findViewById(R.id.txtRazonSocial);
        txtBanco = (EditText) findViewById(R.id.txtBanco);
        txtCuenta = (EditText) findViewById(R.id.txtCuenta);
        btnGuardar = findViewById(R.id.btnGuardar);

        retrofit = new Retrofit.Builder()
                .baseUrl("http://peluditos.online/rest/index.php/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        String estado = getIntent().getStringExtra("actualizar");
        if(estado.equals("1")){
            String id = getIntent().getStringExtra("id");
            String razonSocial = getIntent().getStringExtra("razonsocial");
            String banco = getIntent().getStringExtra("banco");
            String cuenta = getIntent().getStringExtra("cuenta");
            String nit = getIntent().getStringExtra("nit");

            txtNit.setEnabled(false);
            txtRazonSocial.setEnabled(false);
            txtBanco.setEnabled(false);
            txtCuenta.setEnabled(false);

            txtNit.setText(nit);
            txtRazonSocial.setText(razonSocial);
            txtBanco.setText(banco);
            txtCuenta.setText(cuenta);

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
        try {
            if(txtNit.getText().toString().trim().isEmpty()
                    || txtRazonSocial.getText().toString().trim().isEmpty()
                    || txtBanco.getText().toString().trim().isEmpty()
                    || txtCuenta.getText().toString().trim().isEmpty()){

                Toast.makeText(getBaseContext(), "Campos Vacios, Diligencie todos los campos", Toast.LENGTH_LONG).show();

            }else{
                String nit = txtNit.getText().toString().trim();
                String razonSocial = txtRazonSocial.getText().toString().trim();
                String banco = txtBanco.getText().toString().trim();
                String cuenta = txtCuenta.getText().toString().trim();


                sendPost(nit, razonSocial, banco, cuenta);

                txtNit.setText("");
                txtRazonSocial.setText("");
                txtBanco.setText("");
                txtCuenta.setText("");

                Toast.makeText(getBaseContext(), "Guardado con Exito", Toast.LENGTH_LONG).show();
            }

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void sendPost(String nit, String razonSocial, String banco, String cuenta) {

        PostDonacionDinero service = retrofit.create(PostDonacionDinero.class);
        Call<String> call = service.savePost(nit, razonSocial, banco,
                cuenta);

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
