package com.peluditos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.entidades.CEventos;
import com.entidades.eventoRespuesta;
import com.servicios.PostEvento;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class listEvento extends AppCompatActivity {

    private static final String TAG = "PELUDITOS";
    private Button btnNuevo;
    private Retrofit retrofit;
    private ListView lvEvento;
    adapterEvento adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_evento);

        lvEvento =  (ListView)findViewById(R.id.lvEvento);
        retrofit = new Retrofit.Builder()
                .baseUrl("http://peluditos.online/rest/index.php/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        btnNuevo = findViewById(R.id.btnNuevoEvento);
        btnNuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), eventos.class );
                intent.putExtra("actualizar","0");
                startActivity(intent);
            }
        });

        obtenerDatos();

        lvEvento.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                try{
                    CEventos obj = (CEventos) adapter.getItem(position);
                    Intent in = new Intent(getBaseContext(), eventos.class);
                    in.putExtra("actualizar","1");
                    in.putExtra("codigo", obj.getCodigo());
                    in.putExtra("nombre", obj.getNombre());
                    in.putExtra("lugar", obj.getLugar());
                    in.putExtra("hora", obj.getHora());
                    in.putExtra("fecha", obj.getFecha());
                    startActivity(in);
                }catch(Exception ex){
                    ex.printStackTrace();
                }
            }
        });
    }
    private void obtenerDatos() {

        PostEvento service = retrofit.create(PostEvento.class);
        Call<eventoRespuesta> listRespuestaCall = service.obtenerLista();

        listRespuestaCall.enqueue(new Callback<eventoRespuesta>() {
            @Override
            public void onResponse(Call<eventoRespuesta> call, Response<eventoRespuesta> response) {
                if(response.isSuccessful()){
                    eventoRespuesta lstRespuesta = response.body();
                    ArrayList<CEventos> listEv = lstRespuesta.getResult();
                    adapter = new adapterEvento(getApplication(),listEv);
                    lvEvento.setAdapter(adapter);
                }else{
                    Log.e(TAG, "onResponse:"+ response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<eventoRespuesta> call, Throwable t) {
                Log.e(TAG, "onFailure: "+ t.getMessage());
            }
        });

    }
}
