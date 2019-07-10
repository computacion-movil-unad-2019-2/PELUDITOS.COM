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

import com.entidades.CDenunciamaltrato;
import com.entidades.denunciamaltratoRespuesta;
import com.servicios.PostServiceDenunciamaltrato;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Main2Activity_denunciaslist extends AppCompatActivity {

    private static final String TAG = "PELUDITOS";
    private Retrofit retrofit;
    private ListView lv2;
    private Button btnNuevo;
    adapterDenunciamaltrato adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_denunciaslist);
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
                    CDenunciamaltrato obj = (CDenunciamaltrato) adapter.getItem(position);
                    Log.e("Denunciamaltrato :", obj.getDescripcion()+"-"+obj.getUbicacion());
                    Toast.makeText(getBaseContext(), "Tu Denuncia Fue Registrada",
                            Toast.LENGTH_LONG).show();

                    Intent in = new Intent(getBaseContext(), Denunciasmaltrato.class);
                    in.putExtra("actualizar","1");
                    in.putExtra("id", obj.getDescripcion());
                    in.putExtra("nombre", obj.getDescripcion());
                    in.putExtra("ubicacion", obj.getUbicacion());

                    startActivity(in);
                }catch(Exception ex){
                    ex.printStackTrace();
                }
            }
        });


        btnNuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), Denunciasmaltrato.class );
                intent.putExtra("actualizar","0");
                startActivity(intent);
            }
        });




    }

    private void obtenerDatos() {

        PostServiceDenunciamaltrato service = retrofit.create(PostServiceDenunciamaltrato.class);
        Call<denunciamaltratoRespuesta> listRespuestaCall = service.obtenerLista();

        listRespuestaCall.enqueue(new Callback<denunciamaltratoRespuesta>() {
            @Override
            public void onResponse(Call<denunciamaltratoRespuesta> call,
                                   Response<denunciamaltratoRespuesta> response) {
                if(response.isSuccessful()){
                    denunciamaltratoRespuesta lstRespuesta = response.body();
                    ArrayList<CDenunciamaltrato> lstInst = lstRespuesta.getResult();
                    adapter = new adapterDenunciamaltrato (getApplication(),lstInst);
                    lv2.setAdapter(adapter);
                }else{
                    Log.e(TAG, "onResponse:"+ response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<denunciamaltratoRespuesta> call, Throwable t) {
                Log.e(TAG, "onFailure: "+ t.getMessage());
            }
        });

    }
}