package com.peluditos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.entidades.CInstituciones;
import com.entidades.CSeguimientodenunciamaltrato;

import com.entidades.institucionesRespuesta;
import com.entidades.seguimientodenunciaRespuesta;
import com.servicios.PostService;
import com.servicios.PostServiceDenunciamaltrato;
import com.servicios.PostServiceSeguimientodenunciamaltrato;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class listseguimientodenuncia extends AppCompatActivity {

    private static final String TAG = "PELUDITOS";
    private Retrofit retrofit;
    private ListView lv2;
    private Button btnNuevo;
    private Button btnEdit;
    AdapterSeguimientodenuncia adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listseguimientodenuncia);

        lv2 = (ListView)findViewById(R.id.lv2);
        btnEdit = (Button)findViewById(R.id.btnEdit);
        retrofit = new Retrofit.Builder()
                .baseUrl("http://peluditos.online/rest/index.php/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();




        obtenerDatos();

        lv2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try{
                    CSeguimientodenunciamaltrato obj = (CSeguimientodenunciamaltrato) adapter.getItem(position);
                    //Log.e("Instituciones :", obj.getNit()+"-"+obj.getRazonSocial());
                    //Toast.makeText(getBaseContext(), "Tu código es :"+obj.getNit(), Toast.LENGTH_LONG).show();

                    Intent in = new Intent(getBaseContext(), Seguimientodenuncia.class);
                    in.putExtra("actualizar","1");
                    in.putExtra("id", obj.getSeguimiento());
                    in.putExtra("nombre", obj.getIddenuncia());

                    startActivity(in);
                }catch(Exception ex){
                    ex.printStackTrace();
                }
            }
        });

        btnNuevo = findViewById(R.id.btnNuevo);

        btnNuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), Seguimientodenuncia.class );
                intent.putExtra("actualizar","0");
                finish();
                startActivity(intent);
            }
        });




    }
    private void obtenerDatos() {

        PostServiceSeguimientodenunciamaltrato service = retrofit.create(PostServiceSeguimientodenunciamaltrato.class);
        Call<seguimientodenunciaRespuesta> listRespuestaCall = service.obtenerLista();

        listRespuestaCall.enqueue(new Callback<seguimientodenunciaRespuesta>() {
            public void onResponse(Call<seguimientodenunciaRespuesta> call, Response<seguimientodenunciaRespuesta> response) {
                if(response.isSuccessful()){
                    seguimientodenunciaRespuesta lstRespuesta = response.body();
                    ArrayList<CSeguimientodenunciamaltrato> listEv = lstRespuesta.getResult();
                    adapter = new AdapterSeguimientodenuncia(getApplication(),listEv);
                    lv2.setAdapter((ListAdapter) adapter);
                }else{
                    Log.e(TAG, "onResponse:"+ response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<seguimientodenunciaRespuesta> call, Throwable t) {
                Log.e(TAG, "onFailure: "+ t.getMessage());
            }
        });

    }
}
