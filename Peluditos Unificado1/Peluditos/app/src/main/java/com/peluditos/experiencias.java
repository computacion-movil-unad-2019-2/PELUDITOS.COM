package com.peluditos;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.servicios.PostExperiencia;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class experiencias extends AppCompatActivity {
    private static final String TAG = "PELUDITOS";
    private EditText txtVivenvia;
    private EditText txtLugar;
    private Button btnGuardar;
    private Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_experiencias);

        txtVivenvia = (EditText) findViewById(R.id.txtVivencia);
        txtLugar = (EditText) findViewById(R.id.txtLugar);
        btnGuardar = findViewById(R.id.btnGuardar);

        retrofit = new Retrofit.Builder()
                .baseUrl("http://peluditos.online/rest/index.php/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        String estado = getIntent().getStringExtra("actualizar");
        if(estado.equals("1")){
            String id = getIntent().getStringExtra("id");
            String vivencia = getIntent().getStringExtra("vivencia");
            String lugar = getIntent().getStringExtra("lugar");

            txtVivenvia.setEnabled(false);
            txtLugar.setEnabled(false);

            txtVivenvia.setText(vivencia);
            txtLugar.setText(lugar);

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
            if(txtVivenvia.getText().toString().trim().isEmpty()
                    || txtLugar.getText().toString().trim().isEmpty()){

                Toast.makeText(getBaseContext(), "Por favor, complete todos los campos", Toast.LENGTH_LONG).show();

            }else{
                String vivencia = txtVivenvia.getText().toString().trim();
                String lugar = txtLugar.getText().toString().trim();


                sendPost(vivencia, lugar);

                txtVivenvia.setText("");
                txtLugar.setText("");

                Toast.makeText(getBaseContext(), "Experiencia Guardada", Toast.LENGTH_LONG).show();
            }

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void sendPost(String vivencia, String lugar) {

        PostExperiencia service = retrofit.create(PostExperiencia.class);
        Call<String> call = service.savePost(vivencia, lugar);

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
