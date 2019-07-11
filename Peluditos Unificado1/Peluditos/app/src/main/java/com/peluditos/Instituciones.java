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

import com.entidades.CInstituciones;
import com.entidades.tipo;
import com.entidades.tipoInstitucion;
import com.entidades.tipoInstitucionRespuesta;
import com.entidades.tipoMascota;
import com.entidades.tipoMascotaRespuesta;
import com.servicios.PostTipoInstitucion;
import com.servicios.ServicioTask;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Instituciones extends AppCompatActivity {


    private static final String TAG = "PELUDITOS";
    private EditText txtNit;
    private TextView vwTipoInstitucion;
    private EditText txtRazonSocial;
    private EditText txtUbicacion;
    private EditText txtObjetivo;
    private EditText txtTipoInst;
    private EditText txtCuenta;
    private EditText txtBanco;
    private Button btnGuardar;
    private Retrofit retrofit;
    private Spinner spTipoInstitucion;
    private ArrayAdapter<String> lstTipoInst;
    private String [] lstLista;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.instituciones);

        txtNit = (EditText) findViewById(R.id.txtNit);
        txtRazonSocial = (EditText) findViewById(R.id.txtRazonSocial);
        txtUbicacion = (EditText) findViewById(R.id.txtUbicacion);
        txtObjetivo = (EditText) findViewById(R.id.txtObjetivo);
        txtTipoInst = (EditText) findViewById(R.id.txtTipoInst);
        txtCuenta = (EditText) findViewById(R.id.txtCuenta);
        txtBanco = (EditText) findViewById(R.id.txtBanco);
        spTipoInstitucion = (Spinner) findViewById(R.id.spTipoInstitucion);
        vwTipoInstitucion = (TextView) findViewById(R.id.vwTipoInstitucion);

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
            String tipo = getIntent().getStringExtra("tipo");
            String cuenta = getIntent().getStringExtra("cuenta");
            String banco = getIntent().getStringExtra("banco");

            txtNit.setEnabled(false);
            txtRazonSocial.setEnabled(false);
            txtUbicacion.setEnabled(false);
            txtObjetivo.setEnabled(false);
            txtTipoInst.setEnabled(false);
            txtCuenta.setEnabled(false);
            txtBanco.setEnabled(false);
            spTipoInstitucion.setEnabled(false);

            txtTipoInst.setText(tipo);
            txtNit.setText(nit);
            txtRazonSocial.setText(razonSocial);
            txtUbicacion.setText(ubicacion);
            txtObjetivo.setText(objetivo);
            txtCuenta.setText(cuenta);
            txtBanco.setText(banco);

            // cambia segun el perfil del usuario
            btnGuardar.setVisibility(View.INVISIBLE);
            spTipoInstitucion.setVisibility(View.INVISIBLE);
            vwTipoInstitucion.setVisibility(View.INVISIBLE);
        }else{
            txtTipoInst.setVisibility(View.INVISIBLE);
            spTipoInstitucion.setVisibility(View.VISIBLE);
            vwTipoInstitucion.setVisibility(View.VISIBLE);
        }
        cargarTipo();
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                consumirServicio();
                finish();
            }
        });


    }

    public void consumirServicio(){
        // ahora ejecutamos el hilo creado
        try {
            if(txtNit.getText().toString().trim().isEmpty()
                    || txtRazonSocial.getText().toString().trim().isEmpty()
                    || txtObjetivo.getText().toString().trim().isEmpty()
                    || txtCuenta.getText().toString().trim().isEmpty()
                    || txtBanco.getText().toString().trim().isEmpty()
                    || spTipoInstitucion.getSelectedItem().toString().trim().isEmpty()
                    || txtUbicacion.getText().toString().trim().isEmpty()){

                Toast.makeText(getBaseContext(), "un campo esta vacio, y no se pudo guardar la Institución", Toast.LENGTH_LONG).show();

            }else{
                CInstituciones obj = new CInstituciones();
                obj.setNit(txtNit.getText().toString());
                obj.setRazonSocial(txtRazonSocial.getText().toString());
                obj.setObjetivo(txtObjetivo.getText().toString());
                obj.setUbicacion(txtUbicacion.getText().toString());
                obj.setTipo(spTipoInstitucion.getSelectedItem().toString().trim());
                obj.setCuenta(txtCuenta.getText().toString());
                obj.setBanco(txtBanco.getText().toString());


                ServicioTask serviciosTask = new ServicioTask(this, "http://peluditos.online/rest/index.php/instituciones/crear/", obj);
                serviciosTask.execute();

                txtNit.setText("");
                txtRazonSocial.setText("");
                txtObjetivo.setText("");
                txtUbicacion.setText("");
                txtCuenta.setText("");

                Toast.makeText(getBaseContext(), "Guardado con Exito", Toast.LENGTH_LONG).show();
            }

        }
        catch (Exception e){
            e.printStackTrace();
            //Toast.makeText(getBaseContext(), "Error "+ e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void cargarTipo(){
        PostTipoInstitucion service = retrofit.create(PostTipoInstitucion.class);
        Call<tipoInstitucionRespuesta> listRespuestaCall = service.obtenerLista();

        listRespuestaCall.enqueue(new Callback<tipoInstitucionRespuesta>() {
            @Override
            public void onResponse(Call<tipoInstitucionRespuesta> call, Response<tipoInstitucionRespuesta> response) {

                if(response.isSuccessful()){
                    tipoInstitucionRespuesta lstRespuesta = response.body();
                    ArrayList<tipoInstitucion> lstAdop = lstRespuesta.getResult();
                    lstLista = new String[lstAdop.size()];
                    int i = 0;
                    for(tipoInstitucion item : lstAdop)
                    {
                        lstLista[i] = item.getDescripcion();
                        i += 1;
                        Log.e(TAG, "onResponse:"+ item.getDescripcion());
                    }
                    lstTipoInst = new ArrayAdapter<String>(getApplication(),android.R.layout.simple_spinner_item, lstLista);
                    spTipoInstitucion.setAdapter(lstTipoInst);

                }else{
                    Log.e(TAG, "onResponse:"+ response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<tipoInstitucionRespuesta> call, Throwable t) {
                Log.e(TAG, "onFailure: "+ t.getMessage());
            }
        });
    }
}
