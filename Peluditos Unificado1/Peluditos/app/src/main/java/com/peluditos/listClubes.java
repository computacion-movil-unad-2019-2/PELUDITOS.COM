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

import com.entidades.club;
import com.entidades.clubRespuesta;
import com.servicios.PostClubes;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class listClubes extends AppCompatActivity {

    private static final String TAG = "PELUDITOS";
    private Button btnNuevo;
    private Retrofit retrofit;
    private ListView lvClubes;
    adapterClub adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_clubes);

        lvClubes =  (ListView)findViewById(R.id.lvClubes);
        retrofit = new Retrofit.Builder()
                .baseUrl("http://peluditos.online/rest/index.php/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        btnNuevo = findViewById(R.id.btnNuevo);
        btnNuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(),clubes.class );
                intent.putExtra("actualizar","0");
                startActivity(intent);
            }
        });
        obtenerDatos();

        lvClubes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try{
                    club obj = (club) adapter.getItem(position);
                    Log.e("Clubes :", obj.getNit()+"-"+obj.getRazonSocial());
                    Toast.makeText(getBaseContext(), "Tu código es :"+obj.getNit(), Toast.LENGTH_LONG).show();
                    Intent in = new Intent(getBaseContext(), clubes.class);
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

        PostClubes service = retrofit.create(PostClubes.class);
        Call<clubRespuesta> listRespuestaCall = service.obtenerLista();

        listRespuestaCall.enqueue(new Callback<clubRespuesta>() {
            @Override
            public void onResponse(Call<clubRespuesta> call, Response<clubRespuesta> response) {
                if(response.isSuccessful()){
                    clubRespuesta lstRespuesta = response.body();
                    ArrayList<club> lstInst = lstRespuesta.getResult();
                    adapter = new adapterClub(getApplication(),lstInst);
                    lvClubes.setAdapter(adapter);
                }else{
                    Log.e(TAG, "onResponse:"+ response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<clubRespuesta> call, Throwable t) {
                Log.e(TAG, "onFailure: "+ t.getMessage());
            }
        });

    }
}
