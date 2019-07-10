package com.peluditos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.entidades.donacion;
import com.entidades.donacionRespuesta;
import com.servicios.PostDonacionEspecie;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class listDonacionEspecie extends AppCompatActivity {

    private static final String TAG = "PELUDITOS";
    private Button btnDonar;
    private Retrofit retrofit;
    private ListView lvDonaciones;
    adapterDonacion adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_donacion_especie);

        lvDonaciones = (ListView)findViewById(R.id.lvDonaciones);
        retrofit = new Retrofit.Builder()
                .baseUrl("http://peluditos.online/rest/index.php/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        btnDonar = findViewById(R.id.btnDonar);
        btnDonar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), donacionEspecie.class );
                intent.putExtra("actualizar","0");
                startActivity(intent);
            }
        });
        obtenerDatos();

        lvDonaciones.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try{
                    donacion obj = (donacion) adapter.getItem(position);
                    //Log.e("Guarderias :", obj.getNit()+"-"+obj.getRazonSocial());
                    //Toast.makeText(getBaseContext(), "Tu código es :"+obj.getNit(), Toast.LENGTH_LONG).show();
                    Intent in = new Intent(getBaseContext(), donacionEspecie.class);
                    in.putExtra("actualizar","1");
                    in.putExtra("producto", obj.getProducto());
                    in.putExtra("descripcion", obj.getDescripcion());
                    in.putExtra("fecha", obj.getFecha());
                    in.putExtra("lugar", obj.getLugar());
                    in.putExtra("cantidad", obj.getCantidad());
                    in.putExtra("institucion", obj.getInstitucion());
                    startActivity(in);
                }catch(Exception ex){
                    ex.printStackTrace();
                }
            }
        });
    }
    private void obtenerDatos() {

        PostDonacionEspecie service = retrofit.create(PostDonacionEspecie.class);
        Call<donacionRespuesta> listRespuestaCall = service.obtenerLista();

        listRespuestaCall.enqueue(new Callback<donacionRespuesta>() {
            @Override
            public void onResponse(Call<donacionRespuesta> call, Response<donacionRespuesta> response) {
                if(response.isSuccessful()){
                    donacionRespuesta lstRespuesta = response.body();
                    ArrayList<donacion> lstInst = lstRespuesta.getResult();
                    adapter = new adapterDonacion(getApplication(),lstInst);
                    lvDonaciones.setAdapter(adapter);
                }else{
                    Log.e(TAG, "onResponse:"+ response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<donacionRespuesta> call, Throwable t) {
                Log.e(TAG, "onFailure: "+ t.getMessage());
            }
        });

    }

}
