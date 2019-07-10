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

import com.entidades.CEncontraranimal;
import com.entidades.CInstituciones;
import com.entidades.encontraranimalRespuesta;
import com.entidades.institucionesRespuesta;
import com.servicios.PostService;
import com.servicios.PostServiceEncontraranimal;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Main2Activity_list_encontraranimal extends AppCompatActivity {
    private static final String TAG = "PELUDITOS";
    private Retrofit retrofit;
    private ListView lv2;
    private Button btnNuevo;
    adapterEncontraranimal adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2_list_encontraranimal);
        btnNuevo = findViewById(R.id.btnNuevo);

        lv2 = (ListView)findViewById(R.id.lv2);
        retrofit = new Retrofit.Builder()
                .baseUrl("http://peluditos.online/rest/index.php/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        obtenerDatos();

        lv2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try{
                    CEncontraranimal obj = (CEncontraranimal) adapter.getItem(position);
                    Log.e("Encontraranimal :", obj.getNombremascota()+"-"+obj.getCaracteristicas());
                    Toast.makeText(getBaseContext(), "Tu mascota es :"+obj.getNombremascota(),
                            Toast.LENGTH_LONG).show();

                    Intent in = new Intent(getBaseContext(), Encontraranimal.class);
                    in.putExtra("actualizar","1");
                    in.putExtra("id", obj.getNombremascota());
                    in.putExtra("nombre", obj.getNombremascota());
                    in.putExtra("raza", obj.getRaza());
                    in.putExtra("ubicacion", obj.getUbicacion());
                    in.putExtra("caracteristicas", obj.getCaracteristicas());
                    startActivity(in);
                }catch(Exception ex){
                    ex.printStackTrace();
                }
            }
        });


        btnNuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), Encontraranimal.class );
                intent.putExtra("actualizar","0");
                startActivity(intent);
            }
        });




    }

    private void obtenerDatos() {

        PostServiceEncontraranimal service = retrofit.create(PostServiceEncontraranimal.class);
        Call<encontraranimalRespuesta> listRespuestaCall = service.obtenerLista();

        listRespuestaCall.enqueue(new Callback<encontraranimalRespuesta>() {
            @Override
            public void onResponse(Call<encontraranimalRespuesta> call,
                                   Response<encontraranimalRespuesta> response) {
                if(response.isSuccessful()){
                    encontraranimalRespuesta lstRespuesta = response.body();
                    ArrayList<CEncontraranimal> lstInst = lstRespuesta.getResult();
                    adapter = new adapterEncontraranimal (getApplication(),lstInst);
                    lv2.setAdapter(adapter);
                }else{
                    Log.e(TAG, "onResponse:"+ response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<encontraranimalRespuesta> call, Throwable t) {
                Log.e(TAG, "onFailure: "+ t.getMessage());
            }
        });

    }
}