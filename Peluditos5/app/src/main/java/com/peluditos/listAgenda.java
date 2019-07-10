package com.peluditos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.entidades.agendaRespuesta;
import com.servicios.PostAgenda;
import com.entidades.agenda;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class listAgenda extends AppCompatActivity {

    private static final String TAG = "PELUDITOS";
    private Button btnNuevo;
    private Retrofit retrofit;
    private ListView lvAgenda;
    adapterAgenda adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_agenda);

        lvAgenda =  (ListView)findViewById(R.id.lvAgenda);
        retrofit = new Retrofit.Builder()
                .baseUrl("http://peluditos.online/rest/index.php/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        btnNuevo = findViewById(R.id.btnNuevoAgenda);
        btnNuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), agenda.class );
              //  intent.putExtra("actualizar","0");
              //  intent.putExtra("id", "0");
                startActivity(intent);
            }
        });

        obtenerDatos();

        lvAgenda.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try{
                    agenda obj = (agenda) adapter.getItem(position);
                    Intent in = new Intent(getBaseContext(), agenda.class);
                    in.putExtra("actualizar","1");

                    startActivity(in);
                } catch(Exception ex){
                    ex.printStackTrace();
                }
            }
        });
    }

    private void obtenerDatos() {

        PostAgenda service = retrofit.create(PostAgenda.class);
        Call<agendaRespuesta> listRespuestaCall = service.obtenerLista();

        listRespuestaCall.enqueue(new Callback<agendaRespuesta>() {
            @Override
            public void onResponse(Call<agendaRespuesta> call, Response<agendaRespuesta> response) {
                if(response.isSuccessful()){
                    agendaRespuesta lstRespuesta = response.body();
                    ArrayList<agenda> lstAdop = lstRespuesta.getResult();
                    adapter = new adapterAgenda(getApplication(),lstAdop);
                    lvAgenda.setAdapter(adapter);
                }else{
                    Log.e(TAG, "onResponse:"+ response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<agendaRespuesta> call, Throwable t) {
                Log.e(TAG, "onFailure: "+ t.getMessage());
            }
        });

    }
}
