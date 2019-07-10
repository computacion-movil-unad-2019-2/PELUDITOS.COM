package com.peluditos;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.entidades.adoptanteRespuesta;
import com.entidades.tipo;
import com.entidades.tipoRespuesta;
import com.servicios.PostServiceAdoptante;
import com.servicios.PostTipo;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Main2Activity_Adoptante extends AppCompatActivity {

    private static final String TAG = "PELUDITOS";
    private EditText txtNombres;
    private EditText txtApellidos;
    private EditText txtEdad;
    private EditText txtSexo;
    private EditText txtEstadoCivil;
    private EditText txtEmail;
    private EditText txtTelefono;
    private EditText txtTipoMascota;
    private EditText txtCedula;
    private Retrofit retrofit;
    private Button btnGuardarAdop;
    private Spinner spTipo;
    private String [] lstLista;
    private TextView txtVTipoMascota;
    private ArrayAdapter<String> lstMascota;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2__adoptante);

        txtNombres = (EditText) findViewById(R.id.txtNombres);
        txtApellidos = (EditText) findViewById(R.id.txtApellidos);
        txtEdad = (EditText) findViewById(R.id.txtEdad);
        txtSexo = (EditText) findViewById(R.id.txtSexo);
        txtEstadoCivil = (EditText) findViewById(R.id.txtEstadoCivil);
        txtEmail = (EditText) findViewById(R.id.txtEmail);
        txtTelefono = (EditText) findViewById(R.id.txtTelefono);
        txtTipoMascota = (EditText) findViewById(R.id.txtTipoMascota);
        txtCedula = (EditText) findViewById(R.id.txtCedula);
        spTipo = (Spinner) findViewById(R.id.spTipo);
        txtVTipoMascota = (TextView)findViewById(R.id.txtVTipoMascota);
        btnGuardarAdop = findViewById(R.id.btnGuardarAdop);

        retrofit = new Retrofit.Builder()
                .baseUrl("http://peluditos.online/rest/index.php/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        String estado = getIntent().getStringExtra("actualizar");
        if(estado.equals("1")){
            String id = getIntent().getStringExtra("id");
            String nombres = getIntent().getStringExtra("nombres");
            String apellidos = getIntent().getStringExtra("apellidos");
            String edad = getIntent().getStringExtra("edad");
            String sexo = getIntent().getStringExtra("sexo");
            String estadoCivil = getIntent().getStringExtra("estadoCivil");
            String correoElectronico = getIntent().getStringExtra("correoElectronico");
            String numeroTelefono = getIntent().getStringExtra("numTelefono");
            String tipoMascota = getIntent().getStringExtra("tipoMascota");
            String cedula = getIntent().getStringExtra("cedula");

            txtNombres.setText(nombres);
            txtApellidos.setText(apellidos);
            txtEdad.setText(edad);
            txtSexo.setText(sexo);
            txtEstadoCivil.setText(estadoCivil);
            txtEmail.setText(correoElectronico);
            txtTelefono.setText(numeroTelefono);
            txtTipoMascota.setText(tipoMascota);
            txtCedula.setText(cedula);

            spTipo.setEnabled(false);
            txtNombres.setEnabled(false);
            txtApellidos.setEnabled(false);
            txtEdad.setEnabled(false);
            txtSexo.setEnabled(false);
            txtEstadoCivil.setEnabled(false);
            txtEmail.setEnabled(false);
            txtTelefono.setEnabled(false);
            txtTipoMascota.setEnabled(false);
            txtCedula.setEnabled(false);

            btnGuardarAdop.setVisibility(View.INVISIBLE);
            spTipo.setVisibility(View.INVISIBLE);
            txtVTipoMascota.setVisibility(View.INVISIBLE);
        }else{
            txtTipoMascota.setVisibility(View.INVISIBLE);
        }
        cargarTipo();



        btnGuardarAdop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String id = getIntent().getStringExtra("id");
                try {

                    String nombres = txtNombres.getText().toString().trim();
                    String apellidos = txtApellidos.getText().toString().trim();
                    String edad = txtEdad.getText().toString().trim();
                    String sexo = txtSexo.getText().toString().trim();
                    String estadoCivil = txtEstadoCivil.getText().toString().trim();
                    String correoElectronico = txtEmail.getText().toString().trim();
                    String numeroTelefono = txtTelefono.getText().toString().trim();
                    //String tipoMascota = txtTipoMascota.getText().toString().trim();
                    String tipoMascota = spTipo.getSelectedItem().toString().trim();
                    String cedula = txtCedula.getText().toString().trim();

                    if(nombres.trim().isEmpty() || apellidos.trim().isEmpty() || edad.trim().isEmpty()
                            || sexo.trim().isEmpty() || estadoCivil.trim().isEmpty()
                            || tipoMascota.trim().isEmpty()
                            || correoElectronico.trim().isEmpty() || numeroTelefono.trim().isEmpty()
                            || tipoMascota.trim().isEmpty() || cedula.trim().isEmpty()){

                        Toast.makeText(getBaseContext(), "un campo esta vacio, y no se pudo guardar el Adoptante", Toast.LENGTH_LONG).show();

                    }else {

                        sendPost(cedula, nombres, apellidos, edad, sexo, estadoCivil, correoElectronico, numeroTelefono, tipoMascota);

                        txtNombres.setText("");
                        txtApellidos.setText("");
                        txtEdad.setText("");
                        txtSexo.setText("");
                        txtEstadoCivil.setText("");
                        txtEmail.setText("");
                        txtTelefono.setText("");
                        txtTipoMascota.setText("");
                        txtCedula.setText("");

                        Toast.makeText(getBaseContext(), "Guardado con Exito", Toast.LENGTH_LONG).show();
                        finish();
                    }
                }catch(Exception e){
                    e.printStackTrace();
                    Toast.makeText(getBaseContext(), "Error "+ e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void sendPost(String cedula, String nombres, String apellidos, String edad,
                         String sexo, String estadoCivil, String correoElectronico,
                         String numTelefono, String tipoMascota) {

        PostServiceAdoptante service = retrofit.create(PostServiceAdoptante.class);
        Call<String> call = service.savePost(cedula, nombres, apellidos,
                                            edad, sexo, estadoCivil,
                                            correoElectronico, numTelefono, tipoMascota);

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
                //Log.e(TAG, "Unable to submit post to API.");
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

    public void showResponse(String response) {
        /*if(mResponseTv.getVisibility() == View.GONE) {
            mResponseTv.setVisibility(View.VISIBLE);
        }
        mResponseTv.setText(response);*/
    }
}
