package com.peluditos;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.servicios.PostDonacionEspecie;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donacion_especie);

        Producto = (EditText) findViewById(R.id.textProduc);
        Descripcion = (EditText) findViewById(R.id.textDescripcion3);
        Fecha = (EditText) findViewById(R.id.textFecha);
        Lugar = (EditText) findViewById(R.id.textLugar);
        Cantidad = (EditText) findViewById(R.id.textCantidad);
        btnConfirmar = findViewById(R.id.btnConfirmar);

        retrofit = new Retrofit.Builder()
                .baseUrl("http://peluditos.online/rest/index.php/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

       /* String estado = getIntent().getStringExtra("actualizar");
        if(estado.equals("1")){
            String producto = getIntent().getStringExtra("producto");
            String descripcion = getIntent().getStringExtra("descripcion");
            String fecha = getIntent().getStringExtra("fecha");
            String lugar = getIntent().getStringExtra("lugar");
            String cantidad = getIntent().getStringExtra("cantidad");

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

            btnConfirmar.setVisibility(View.INVISIBLE);
        } */
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

                        sendPost(producto, descripcion, fecha, lugar, cantidad);

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
                         String cantidad) {

        PostDonacionEspecie service = retrofit.create(PostDonacionEspecie.class);
        Call<String> call = service.savePost(producto, descripcion, fecha,
                lugar, cantidad);

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
