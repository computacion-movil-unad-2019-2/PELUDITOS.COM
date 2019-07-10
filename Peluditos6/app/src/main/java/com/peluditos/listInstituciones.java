package com.peluditos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.entidades.CInstituciones;
import com.entidades.Post;
import com.entidades.institucionesRespuesta;
import com.servicios.PostService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class listInstituciones extends AppCompatActivity {

    private static final String TAG = "PELUDITOS";
    private Retrofit retrofit;
    private ListView lv2;
    private Button btnNuevo;
    AdapterInstituciones adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_instituciones);

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
                    CInstituciones obj = (CInstituciones) adapter.getItem(position);
                    Log.e("Instituciones :", obj.getNit()+"-"+obj.getRazonSocial());
                    Toast.makeText(getBaseContext(), "Tu código es :"+obj.getNit(), Toast.LENGTH_LONG).show();

                    Intent in = new Intent(getBaseContext(), Instituciones.class);
                    in.putExtra("actualizar","1");
                    in.putExtra("id", obj.getNit());
                    in.putExtra("nombre", obj.getRazonSocial());
                    in.putExtra("objetivo", obj.getObjetivo());
                    in.putExtra("ubicacion", obj.getUbicacion());
                    in.putExtra("nit", obj.getNit());
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
                Intent intent = new Intent(getBaseContext(), Instituciones.class );
                //  intent.putExtra("actualizar","0");
                //  intent.putExtra("id", "0");
                startActivity(intent);
            }
        });

    }

    private void obtenerDatos() {

        PostService service = retrofit.create(PostService.class);
        Call<institucionesRespuesta> listRespuestaCall = service.obtenerLista();

        listRespuestaCall.enqueue(new Callback<institucionesRespuesta>() {
            @Override
            public void onResponse(Call<institucionesRespuesta> call, Response<institucionesRespuesta> response) {
                if(response.isSuccessful()){
                    institucionesRespuesta lstRespuesta = response.body();
                    ArrayList<CInstituciones> lstInst = lstRespuesta.getResult();
                    adapter = new AdapterInstituciones(getApplication(),lstInst);
                    lv2.setAdapter(adapter);
                }else{
                    Log.e(TAG, "onResponse:"+ response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<institucionesRespuesta> call, Throwable t) {
                Log.e(TAG, "onFailure: "+ t.getMessage());
            }
        });

    }
}
