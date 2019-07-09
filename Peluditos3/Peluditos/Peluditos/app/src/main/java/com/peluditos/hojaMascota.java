package com.peluditos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class hojaMascota extends AppCompatActivity {

    private static final String TAG = "PELUDITOS";
    private EditText txtNombres;
    private EditText txtTamano;
    private EditText txtEdad;
    private EditText txtControlMedico;
    private EditText txtCualidadesMascota;
    private EditText txtUbicacion;
    private EditText txtTipo;
    private EditText txtEstadoHoja;
    private Button btnGuardar;
    private Retrofit retrofit;
    private ImageView imagen;
    private String id;

    int num_aleatorio = (int) (Math.random()*10);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hoja_mascota);

        txtNombres = (EditText) findViewById(R.id.txtNombreMascota);
        txtTamano = (EditText) findViewById(R.id.txtTamanoMascota);
        txtEdad = (EditText) findViewById(R.id.txtEdadMascota);
        txtControlMedico = (EditText) findViewById(R.id.txtControlMedico);
        txtCualidadesMascota = (EditText) findViewById(R.id.txtCualidadesMascota);
        txtUbicacion = (EditText) findViewById(R.id.txtUbicacionMascota);
        txtTipo = (EditText) findViewById(R.id.txtTipoMascota);
        imagen = (ImageView) findViewById(R.id.imagenMascota);
        txtEstadoHoja = (EditText) findViewById(R.id.txtEstadoHoja);

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
            txtTipo.setEnabled(false);
            txtControlMedico.setEnabled(false);
            txtCualidadesMascota.setEnabled(false);
            txtUbicacion.setEnabled(false);
            txtEstadoHoja.setEnabled(false);

            txtNombres.setText(nombres);
            txtTamano.setText(tamano);
            txtEdad.setText(edad);
            txtTipo.setText(tipo);
            txtControlMedico.setText(controlMedico);
            txtCualidadesMascota.setText(ciudadReferencia);
            txtUbicacion.setText(ubicacion);
            txtEstadoHoja.setText(strEstado);

            Glide.with(this).load(foto).into(imagen);

        }else{
            id =  "0";
        }

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String edad = getIntent().getStringExtra("edad");
                String tamano = getIntent().getStringExtra("tamano");
                String controlMedico = getIntent().getStringExtra("controlMedico");
                String ciudadReferencia = getIntent().getStringExtra("ciudadReferencia");
                String ubicacion = getIntent().getStringExtra("ubicacion");
                String tipo = getIntent().getStringExtra("tipo");
                String foto = getIntent().getStringExtra("foto");
                String strEstado = getIntent().getStringExtra("estado");

                Intent in = new Intent(getBaseContext(), agendar.class );
                in.putExtra("actualizar","0");
                in.putExtra("id",getIntent().getStringExtra("id"));
                in.putExtra("nombreMascota",getIntent().getStringExtra("nombres"));

                in.putExtra("edad",edad);
                in.putExtra("tamano",tamano);
                in.putExtra("controlMedico",controlMedico);
                in.putExtra("ciudadReferencia",ciudadReferencia);
                in.putExtra("ubicacion",ubicacion);
                in.putExtra("tipo",tipo);

                // parametros que se deben de recibir del storage al momento del login
                in.putExtra( "idAdoptante","1");
                in.putExtra("correo","avillafana0394@gmail.com");


                finish();

                startActivity(in);
            }
        });


    }
}
