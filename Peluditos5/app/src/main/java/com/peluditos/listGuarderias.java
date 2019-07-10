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

import com.entidades.guarderia;
import com.entidades.guarderiaRespuesta;
import com.servicios.PostGuarderia;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class listGuarderias extends AppCompatActivity {

    private static final String TAG = "PELUDITOS";
    private Button btnNuevo;
    private Retrofit retrofit;
    private ListView lvGuarderias;
    adapterGuarderia adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_guarderias);

        lvGuarderias =  (ListView)findViewById(R.id.lvGuarderias);
        retrofit = new Retrofit.Builder()
                .baseUrl("http://peluditos.online/rest/index.php/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        btnNuevo = findViewById(R.id.btnNuevo);
        btnNuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(),guarderias.class );
                intent.putExtra("actualizar","0");
                startActivity(intent);
            }
        });

        obtenerDatos();

        lvGuarderias.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try{
                    guarderia obj = (guarderia) adapter.getItem(position);
                    Log.e("Guarderias :", obj.getNit()+"-"+obj.getRazonSocial());
                    Toast.makeText(getBaseContext(), "Tu código es :"+obj.getNit(), Toast.LENGTH_LONG).show();
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

        PostGuarderia service = retrofit.create(PostGuarderia.class);
        Call<guarderiaRespuesta> listRespuestaCall = service.obtenerLista();

        listRespuestaCall.enqueue(new Callback<guarderiaRespuesta>() {
            @Override
            public void onResponse(Call<guarderiaRespuesta> call, Response<guarderiaRespuesta> response) {
                if(response.isSuccessful()){
                    guarderiaRespuesta lstRespuesta = response.body();
                    ArrayList<guarderia> lstInst = lstRespuesta.getResult();
                    adapter = new adapterGuarderia(getApplication(),lstInst);
                    lvGuarderias.setAdapter(adapter);
                }else{
                    Log.e(TAG, "onResponse:"+ response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<guarderiaRespuesta> call, Throwable t) {
                Log.e(TAG, "onFailure: "+ t.getMessage());
            }
        });

    }
}
