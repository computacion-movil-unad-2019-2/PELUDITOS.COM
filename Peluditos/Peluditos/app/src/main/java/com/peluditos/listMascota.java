package com.peluditos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.entidades.adoptante;
import com.entidades.adoptanteRespuesta;
import com.entidades.mascota;
import com.entidades.mascotaRespuesta;
import com.servicios.PostServiceAdoptante;
import com.servicios.PostServiceMascota;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class listMascota extends AppCompatActivity {

    private static final String TAG = "PELUDITOS";
    private Button btnNuevo;
    private Retrofit retrofit;
    private ListView lvMascota;
    adapterMascota adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_mascota);

        lvMascota =  (ListView)findViewById(R.id.lvMascota);
        retrofit = new Retrofit.Builder()
                .baseUrl("http://peluditos.online/rest/index.php/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        btnNuevo = findViewById(R.id.btnNuevoMascota);
        btnNuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), mascotas.class );
                intent.putExtra("actualizar","0");
                intent.putExtra("id", "0");
                finish();
                startActivity(intent);
            }
        });

        obtenerDatos();

        lvMascota.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try{
                    mascota obj = (mascota) adapter.getItem(position);
                    Intent in = new Intent(getBaseContext(), mascotas.class);
                    in.putExtra("actualizar","1");
                    in.putExtra("id", obj.getId());
                    in.putExtra("nombres", obj.getNombres());
                    in.putExtra("edad", obj.getEdad());
                    in.putExtra("tamano", obj.getTamano());
                    in.putExtra("controlMedico", obj.getControlMedico());
                    in.putExtra("ciudadReferencia", obj.getCiudadReferencia());
                    in.putExtra("ubicacion", obj.getUbicacion());
                    in.putExtra("tipo", obj.getTipo());
                    in.putExtra("foto",obj.getFoto());
                    in.putExtra("estado",obj.getEstado());
                    finish();
                    startActivity(in);
                }catch(Exception ex){
                    ex.printStackTrace();
                }
            }
        });
    }

    private void obtenerDatos() {

        PostServiceMascota service = retrofit.create(PostServiceMascota.class);
        Call<mascotaRespuesta> listRespuestaCall = service.obtenerLista();

        listRespuestaCall.enqueue(new Callback<mascotaRespuesta>() {
            @Override
            public void onResponse(Call<mascotaRespuesta> call, Response<mascotaRespuesta> response) {
                if(response.isSuccessful()){
                    mascotaRespuesta lstRespuesta = response.body();
                    ArrayList<mascota> lstAdop = lstRespuesta.getResult();
                    adapter = new adapterMascota(getApplication(),lstAdop);
                    lvMascota.setAdapter(adapter);
                }else{
                    Log.e(TAG, "onResponse:"+ response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<mascotaRespuesta> call, Throwable t) {
                Log.e(TAG, "onFailure: "+ t.getMessage());
            }
        });

    }
}
