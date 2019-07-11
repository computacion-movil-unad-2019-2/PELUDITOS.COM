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

import com.entidades.experiencia;
import com.entidades.expRespuesta;
import com.servicios.PostExperiencia;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class listExperiencias extends AppCompatActivity {

    private static final String TAG = "PELUDITOS";
    private Button btnNuevo;
    private Retrofit retrofit;
    private ListView lvExperiencias;
    adapterExperiencia adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_experiencias);

        lvExperiencias =  (ListView)findViewById(R.id.lvExperiencias);
        retrofit = new Retrofit.Builder()
                .baseUrl("http://peluditos.online/rest/index.php/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        btnNuevo = findViewById(R.id.btnNuevo);
        btnNuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(),experiencias.class );
                intent.putExtra("actualizar","0");
                startActivity(intent);
            }
        });
        obtenerDatos();

        lvExperiencias.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try{
                    experiencia obj = (experiencia) adapter.getItem(position);
                    Log.e("Experiencias :", obj.getVivencia()+" - "+obj.getLugar());
                    Toast.makeText(getBaseContext(), "Lugar de la Experiencia : "+obj.getLugar(), Toast.LENGTH_LONG).show();
                    Intent in = new Intent(getBaseContext(), experiencia.class);
                    in.putExtra("actualizar","1");
                    in.putExtra("nit", obj.getVivencia());
                    in.putExtra("nombre", obj.getLugar());
                    startActivity(in);
                }catch(Exception ex){
                    ex.printStackTrace();
                }
            }
        });
    }
    private void obtenerDatos() {

        PostExperiencia service = retrofit.create(PostExperiencia.class);
        Call<expRespuesta> listRespuestaCall = service.obtenerLista();

        listRespuestaCall.enqueue(new Callback<expRespuesta>() {
            @Override
            public void onResponse(Call<expRespuesta> call, Response<expRespuesta> response) {
                if(response.isSuccessful()){
                    expRespuesta lstRespuesta = response.body();
                    ArrayList<experiencia> lstInst = lstRespuesta.getResult();
                    adapter = new adapterExperiencia(getApplication(),lstInst);
                    lvExperiencias.setAdapter(adapter);
                }else{
                    Log.e(TAG, "onResponse:"+ response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<expRespuesta> call, Throwable t) {
                Log.e(TAG, "onFailure: "+ t.getMessage());
            }
        });

    }
}
