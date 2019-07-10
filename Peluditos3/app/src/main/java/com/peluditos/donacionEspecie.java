package com.peluditos;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.entidades.CInstituciones;
import com.entidades.institucionesRespuesta;
import com.servicios.PostDonacionEspecie;
import com.servicios.PostService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class donacionEspecie extends AppCompatActivity {

    private static final String TAG = "PELUDITOS";
    private EditText Producto;
    private EditText Descripcion;
    private EditText Fecha;
    private EditText Lugar;
    private EditText Cantidad;
    private Button btnConfirmar;
    private Retrofit retrofit;
    private Spinner Instituciones;
    private String [] lstLista;
    private ArrayAdapter<String> lstInstitucion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donacion_especie);

        Producto = (EditText) findViewById(R.id.textProduc);
        Descripcion = (EditText) findViewById(R.id.textDescripcio);
        Fecha = (EditText) findViewById(R.id.textFec);
        Lugar = (EditText) findViewById(R.id.textLug);
        Cantidad = (EditText) findViewById(R.id.textCantid);
        btnConfirmar = findViewById(R.id.btnConfirmar);
        Instituciones = (Spinner) findViewById(R.id.Instituc);

        retrofit = new Retrofit.Builder()
                .baseUrl("http://peluditos.online/rest/index.php/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

         String estado = getIntent().getStringExtra("actualizar");
        if(estado.equals("1")){
            String producto = getIntent().getStringExtra("producto");
            String descripcion = getIntent().getStringExtra("descripcion");
            String fecha = getIntent().getStringExtra("fecha");
            String lugar = getIntent().getStringExtra("lugar");
            String cantidad = getIntent().getStringExtra("cantidad");
            //String institucion = getIntent().getStringExtra("institucion");


            Producto.setText(producto);
            Descripcion.setText(descripcion);
            Fecha.setText(fecha);
            Lugar.setText(lugar);
            Cantidad.setText(cantidad);

            Producto.setEnabled(false);
            Descripcion.setEnabled(false);
            Fecha.setEnabled(false);
            Lugar.setEnabled(false);
            Cantidad.setEnabled(false);
            Instituciones.setEnabled(false);

            btnConfirmar.setVisibility(View.INVISIBLE);
        }
       cargarTipo();
        btnConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String id = getIntent().getStringExtra("id");
                try {
                    if(Producto.getText().toString().trim().isEmpty()
                            || Descripcion.getText().toString().trim().isEmpty()
                            || Fecha.getText().toString().trim().isEmpty()
                            || Lugar.getText().toString().trim().isEmpty()
                            || Cantidad.getText().toString().trim().isEmpty()){

                        Toast.makeText(getBaseContext(), "Existe un campo vacio, Complete todos los campos", Toast.LENGTH_LONG).show();

                    }else {

                        String producto = Producto.getText().toString().trim();
                        String descripcion = Descripcion.getText().toString().trim();
                        String fecha = Fecha.getText().toString().trim();
                        String lugar = Lugar.getText().toString().trim();
                        String cantidad = Cantidad.getText().toString().trim();
                        String institucion = Instituciones.getSelectedItem().toString();

                        sendPost(producto, descripcion, fecha, lugar, cantidad, institucion);

                        Producto.setText("");
                        Descripcion.setText("");
                        Fecha.setText("");
                        Lugar.setText("");
                        Cantidad.setText("");

                        Toast.makeText(getBaseContext(), "Donacion confirmada", Toast.LENGTH_LONG).show();
                        //onBackPressed();
                    }
                }catch(Exception e){
                    e.printStackTrace();
                    Toast.makeText(getBaseContext(), "Error "+ e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    public void sendPost(String producto, String descripcion, String fecha, String lugar,
                         String cantidad, String institucion) {

        PostDonacionEspecie service = retrofit.create(PostDonacionEspecie.class);
        Call<String> call = service.savePost(producto, descripcion, fecha,
                lugar, cantidad, institucion);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.i(TAG, "Responsestring :"+ response.body());
                if(response.isSuccessful()) {
                    if(response.body() != null) {
                        Log.i(TAG, "onSuccess." + response.body());
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
        PostService service = retrofit.create(PostService.class);
        Call<institucionesRespuesta> listRespuestaCall = service.obtenerLista();

        listRespuestaCall.enqueue(new Callback<institucionesRespuesta>() {
            @Override
            public void onResponse(Call<institucionesRespuesta> call, Response<institucionesRespuesta> response) {

                if(response.isSuccessful()){
                    institucionesRespuesta lstRespuesta = response.body();
                    ArrayList<CInstituciones> lstAdop = lstRespuesta.getResult();
                    lstLista = new String[lstAdop.size()];
                    int i = 0;
                    for(CInstituciones item : lstAdop)
                    {
                        lstLista[i] = item.getRazonSocial();
                        i += 1;
                    }
                    lstInstitucion = new ArrayAdapter<String>(getApplication(),android.R.layout.simple_spinner_item, lstLista);
                    Instituciones.setAdapter(lstInstitucion);
                }else{
                    Log.e(TAG, "onResponse:"+ response.errorBody());
                }
            }
            @Override
            public void onFailure(Call<institucionesRespuesta> call, Throwable t) {
                Log.e(TAG, "onFailure: "+ t.getMessage());
            }
        });
    }
}
