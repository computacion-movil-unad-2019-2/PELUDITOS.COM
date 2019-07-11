package com.peluditos;

import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.servicios.PostServiceAdoptante;
import com.servicios.PostServiceMascota;

import org.json.JSONException;

import java.io.IOException;
import java.net.MalformedURLException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Main2Activity_Mascota extends AppCompatActivity {

    private static final String TAG = "PELUDITOS";
    private EditText txtNombres;
    private EditText txtTamano;
    private EditText txtEdad;
    private EditText txtControlMedico;
    private EditText txtCiudadReferencia;
    private EditText txtUbicacion;
    private EditText txtTipo;
    private Button btnGuardar;
    private Retrofit retrofit;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2__mascota);

        txtNombres = (EditText) findViewById(R.id.txtNombreMascota);
        txtTamano = (EditText) findViewById(R.id.txtTamanoMascota);
        txtEdad = (EditText) findViewById(R.id.txtEdadMascota);
        txtControlMedico = (EditText) findViewById(R.id.txtControlMedico);
        txtCiudadReferencia = (EditText) findViewById(R.id.txtCiudadMascota);
        txtUbicacion = (EditText) findViewById(R.id.txtUbicacionMascota);
        txtTipo = (EditText) findViewById(R.id.txtTipoMascota);

        retrofit = new Retrofit.Builder()
                .baseUrl("http://peluditos.online/rest/index.php/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        String estado = getIntent().getStringExtra("actualizar");
        if(estado.equals("1")){
            id = getIntent().getStringExtra("id");
            String nombres = getIntent().getStringExtra("nombres");
            String edad = getIntent().getStringExtra("edad");
            String tamano = getIntent().getStringExtra("tamano");
            String controlMedico = getIntent().getStringExtra("controlMedico");
            String ciudadReferencia = getIntent().getStringExtra("ciudadReferencia");
            String ubicacion = getIntent().getStringExtra("ubicacion");
            String tipo = getIntent().getStringExtra("tipo");

            txtNombres.setEnabled(false);
            txtTamano.setEnabled(false);
            txtTamano.setEnabled(false);
            txtEdad.setEnabled(false);
            txtTipo.setEnabled(false);
            txtControlMedico.setEnabled(false);
            txtCiudadReferencia.setEnabled(false);
            txtUbicacion.setEnabled(false);

            txtNombres.setText(nombres);
            txtTamano.setText(tamano);
            txtEdad.setText(edad);
            txtTipo.setText(tipo);
            txtControlMedico.setText(controlMedico);
            txtCiudadReferencia.setText(ciudadReferencia);
            txtUbicacion.setText(ubicacion);

        }else{
            id =  "0";
        }

        btnGuardar = findViewById(R.id.btnGuardarMascota);

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    if (txtNombres.getText().toString().trim().isEmpty()
                            || txtEdad.getText().toString().trim().isEmpty()
                            || txtTamano.getText().toString().trim().isEmpty()
                            || txtControlMedico.getText().toString().trim().isEmpty()
                            || txtCiudadReferencia.getText().toString().trim().isEmpty()
                            || txtTipo.getText().toString().trim().isEmpty()
                            || txtUbicacion.getText().toString().trim().isEmpty()) {

                        Toast.makeText(getBaseContext(), "un campo esta vacio, y no se pudo guardar la mascota", Toast.LENGTH_LONG).show();

                    } else {

                        String nombres = txtNombres.getText().toString().trim();
                        String edad = txtEdad.getText().toString().trim();
                        String tamano = txtTamano.getText().toString().trim();
                        String controlMedico = txtControlMedico.getText().toString().trim();
                        String ciudadReferencia = txtCiudadReferencia.getText().toString().trim();
                        String ubicacion = txtUbicacion.getText().toString().trim();
                        String tipo = txtTipo.getText().toString().trim();

                        sendPost(id, nombres, edad,
                                tamano, controlMedico, ciudadReferencia, ubicacion);

                        txtNombres.setText("");
                        txtTamano.setText("");
                        txtEdad.setText("");
                        txtControlMedico.setText("");
                        txtCiudadReferencia.setText("");
                        txtUbicacion.setText("");
                        txtTipo.setText("");

                        Toast.makeText(getBaseContext(), "Guardado con Exito", Toast.LENGTH_LONG).show();
                        onBackPressed();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getBaseContext(), "Error "+ e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    public void sendPost(String id, String nombres, String edad, String tamano,
                         String controlMedico, String ciudadReferencia, String ubicacion) {

        String foto ="";
        PostServiceMascota service = retrofit.create(PostServiceMascota.class);
        Call<String> call = service.savePost(id, nombres, edad,
                tamano, controlMedico, ciudadReferencia, ubicacion, "perro","Disponible", foto);

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
