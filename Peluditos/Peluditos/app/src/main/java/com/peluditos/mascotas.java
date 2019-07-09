package com.peluditos;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.entidades.tipo;
import com.entidades.tipoRespuesta;
import com.servicios.PostServiceMascota;
import com.servicios.PostTipo;

import org.apache.http.util.ByteArrayBuffer;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class mascotas extends AppCompatActivity {

    private static final String TAG = "PELUDITOS";
    private EditText txtNombres;
    private EditText txtTamano;
    private EditText txtEdad;
    private EditText txtControlMedico;
    private EditText txtCualidadesMascota;
    private EditText txtUbicacion;
    private EditText txtEstado;
    private EditText txtTipo;
    private Button btnGuardar;
    private Retrofit retrofit;
    private ImageView imagen;
    private String id;
    private Spinner spTipo;
    private String [] lstLista;
    private ArrayAdapter<String> lstMascota;
    private Spinner spEstado;
    private ArrayAdapter<String> estado;
    private Button btnFoto;

    int num_aleatorio = (int) (Math.random()*10);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mascotas);

        txtNombres = (EditText) findViewById(R.id.txtNombreMascota);
        txtTamano = (EditText) findViewById(R.id.txtTamanoMascota);
        txtEdad = (EditText) findViewById(R.id.txtEdadMascota);
        txtControlMedico = (EditText) findViewById(R.id.txtControlMedico);
        txtCualidadesMascota = (EditText) findViewById(R.id.txtCualidadesMascota);
        txtUbicacion = (EditText) findViewById(R.id.txtUbicacionMascota);
        spTipo = (Spinner) findViewById(R.id.spTipo);
        txtTipo = (EditText) findViewById(R.id.txtTipo);
        imagen = (ImageView) findViewById(R.id.imagenMascota);
        spEstado = (Spinner) findViewById(R.id.spEstado);
        txtEstado = (EditText) findViewById(R.id.txtEstado);

        String [] lstEstado =  {"Disponible","En Proceso", "Adoptado"};
        estado = new ArrayAdapter<String>(getApplication(),android.R.layout.simple_spinner_item, lstEstado);
        spEstado.setAdapter(estado);

        btnFoto = findViewById(R.id.btnFoto);
        btnGuardar = findViewById(R.id.btnGuardarMascota);

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
            String foto = getIntent().getStringExtra("foto");
            String strEstado = getIntent().getStringExtra("estado");

            txtNombres.setEnabled(false);
            txtTamano.setEnabled(false);
            txtTamano.setEnabled(false);
            txtEdad.setEnabled(false);
            spTipo.setEnabled(false);
            spEstado.setEnabled(false);

            txtControlMedico.setEnabled(false);
            txtCualidadesMascota.setEnabled(false);
            txtUbicacion.setEnabled(false);
            txtTipo.setEnabled(false);
            txtEstado.setEnabled(false);

            txtNombres.setText(nombres);
            txtTamano.setText(tamano);
            txtEdad.setText(edad);
            txtTipo.setText(tipo);
            txtControlMedico.setText(controlMedico);
            txtCualidadesMascota.setText(ciudadReferencia);
            txtUbicacion.setText(ubicacion);
            txtEstado.setText(strEstado);
            
            //imagen.setImageBitmap(DownloadFullFromUrl("http://peluditos.online/img/toby.jpg"));
            //imagen.setImageURI(foto);
            btnGuardar.setVisibility(View.INVISIBLE);
            btnFoto.setVisibility(View.INVISIBLE);
            Glide.with(this).load(foto).into(imagen);
            spEstado.setVisibility(View.INVISIBLE);
            spTipo.setVisibility(View.INVISIBLE);

        }else{
            id =  "0";
            txtTipo.setVisibility(View.INVISIBLE);
            txtEstado.setVisibility(View.INVISIBLE);
            btnFoto.setVisibility(View.INVISIBLE);
        }
        cargarTipo();


        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //|| txtTipo.getText().toString().trim().isEmpty()
                try {
                    if (txtNombres.getText().toString().trim().isEmpty()
                            || txtEdad.getText().toString().trim().isEmpty()
                            || txtTamano.getText().toString().trim().isEmpty()
                            || txtControlMedico.getText().toString().trim().isEmpty()
                            || txtCualidadesMascota.getText().toString().trim().isEmpty()
                            || txtUbicacion.getText().toString().trim().isEmpty()) {

                        Toast.makeText(getBaseContext(), "un campo esta vacio, y no se pudo guardar la mascota", Toast.LENGTH_LONG).show();

                    } else {

                        String nombres = txtNombres.getText().toString().trim();
                        String edad = txtEdad.getText().toString().trim();
                        String tamano = txtTamano.getText().toString().trim();
                        String controlMedico = txtControlMedico.getText().toString().trim();
                        String ciudadReferencia = txtCualidadesMascota.getText().toString().trim();
                        String ubicacion = txtUbicacion.getText().toString().trim();
                        String tipo = spTipo.getSelectedItem().toString().trim();
                        String estado = spEstado.getSelectedItem().toString().trim();

                        sendPost(id, nombres, edad,
                                tamano, controlMedico, ciudadReferencia, ubicacion, tipo, estado);

                        txtNombres.setText("");
                        txtTamano.setText("");
                        txtEdad.setText("");
                        txtControlMedico.setText("");
                        txtCualidadesMascota.setText("");
                        txtUbicacion.setText("");


                        Toast.makeText(getBaseContext(), "Guardado con Exito", Toast.LENGTH_LONG).show();
                        onBackPressed();

                        finish();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getBaseContext(), "Error "+ e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    public void sendPost(String id, String nombres, String edad, String tamano,
                         String controlMedico, String ciudadReferencia, String ubicacion, String tipo, String estado) {

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

    private void cargarTipo(){
        PostTipo service = retrofit.create(PostTipo.class);
        Call<tipoRespuesta> listRespuestaCall = service.obtenerLista();

        listRespuestaCall.enqueue(new Callback<tipoRespuesta>() {
            @Override
            public void onResponse(Call<tipoRespuesta> call, Response<tipoRespuesta> response) {

                if(response.isSuccessful()){
                    tipoRespuesta lstRespuesta = response.body();
                    ArrayList<tipo> lstAdop = lstRespuesta.getResult();
                    lstLista = new String[lstAdop.size()];
                    int i = 0;
                    for(tipo item : lstAdop)
                    {
                        lstLista[i] = item.getNombres();
                        i += 1;
                    }
                    lstMascota = new ArrayAdapter<String>(getApplication(),android.R.layout.simple_spinner_item, lstLista);
                    spTipo.setAdapter(lstMascota);

                }else{
                    Log.e(TAG, "onResponse:"+ response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<tipoRespuesta> call, Throwable t) {
                Log.e(TAG, "onFailure: "+ t.getMessage());
            }
        });
    }


}
