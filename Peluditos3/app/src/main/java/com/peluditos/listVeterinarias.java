package com.peluditos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.entidades.veterinaria;
import com.entidades.veterinariaRespuesta;
import com.servicios.PostVeterinaria;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class listVeterinarias extends AppCompatActivity {

    private static final String TAG = "PELUDITOS";
    private Button btnNuevo;
    private Retrofit retrofit;
    private ListView lvVeterinarias;
    adapterVeterinaria adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_veterinarias);

        lvVeterinarias =  (ListView)findViewById(R.id.lvVeterinarias);
        retrofit = new Retrofit.Builder()
                .baseUrl("http://peluditos.online/rest/index.php/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        btnNuevo = findViewById(R.id.btnNuevo);
        btnNuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(),veterinarias.class );
                intent.putExtra("actualizar","0");
                startActivity(intent);
            }
        });

        obtenerDatos();

        lvVeterinarias.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try{
                    veterinaria obj = (veterinaria) adapter.getItem(position);
                    //Log.e("Guarderias :", obj.getNit()+"-"+obj.getRazonSocial());
                    //Toast.makeText(getBaseContext(), "Tu código es :"+obj.getNit(), Toast.LENGTH_LONG).show();
                    Intent in = new Intent(getBaseContext(), guarderias.class);
                    in.putExtra("actualizar","1");
                    in.putExtra("nit", obj.getNit());
                    in.putExtra("nombre", obj.getRazonSocial());
                    in.putExtra("objetivo", obj.getObjetivo());
                    in.putExtra("ubicacion", obj.getUbicacion());
                    startActivity(in);
                }catch(Exception ex){
                    ex.printStackTrace();
                }
            }
        });
    }

    private void obtenerDatos() {

        PostVeterinaria service = retrofit.create(PostVeterinaria.class);
        Call<veterinariaRespuesta> listRespuestaCall = service.obtenerLista();

        listRespuestaCall.enqueue(new Callback<veterinariaRespuesta>() {
            @Override
            public void onResponse(Call<veterinariaRespuesta> call, Response<veterinariaRespuesta> response) {
                if(response.isSuccessful()){
                    veterinariaRespuesta lstRespuesta = response.body();
                    ArrayList<veterinaria> lstInst = lstRespuesta.getResult();
                    adapter = new adapterVeterinaria(getApplication(),lstInst);
                    lvVeterinarias.setAdapter(adapter);
                }else{
                    Log.e(TAG, "onResponse:"+ response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<veterinariaRespuesta> call, Throwable t) {
                Log.e(TAG, "onFailure: "+ t.getMessage());
            }
        });

    }
}
