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

import com.entidades.donacionDinero;
import com.entidades.CInstituciones;
import com.entidades.dineroRespuesta;
import com.entidades.donacionDinero;
import com.servicios.PostDonacionDinero;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class listDonacionDinero extends AppCompatActivity {

    private static final String TAG = "PELUDITOS";
    private Button btnNuevo;
    private Retrofit retrofit;
    private ListView lvDonaDinero;
    adapterDonacionDinero adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_donacion_dinero);

        lvDonaDinero =  (ListView)findViewById(R.id.lvDonaDinero);
        retrofit = new Retrofit.Builder()
                .baseUrl("http://peluditos.online/rest/index.php/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

       /* btnNuevo = findViewById(R.id.btnNuevo);
        btnNuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(),experiencias.class );
                intent.putExtra("actualizar","0");
                startActivity(intent);
            }
        }); */
        obtenerDatos();

        lvDonaDinero.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try{
                    donacionDinero obj = (donacionDinero) adapter.getItem(position);
                    Log.e("Donaciones en Dinero :", obj.getNit()+" - "+obj.getRazonSocial());
                    Toast.makeText(getBaseContext(), "Cuenta bancaria  : "+obj.getBanco(), Toast.LENGTH_LONG).show();
                    Intent in = new Intent(getBaseContext(), CInstituciones.class);
                    in.putExtra("actualizar","1");
                    in.putExtra("nit", obj.getBanco());
                    in.putExtra("nombre", obj.getCuenta());
                    startActivity(in);
                }catch(Exception ex){
                    ex.printStackTrace();
                }
            }
        });
    }
    private void obtenerDatos() {

        PostDonacionDinero service = retrofit.create(PostDonacionDinero.class);
        Call<dineroRespuesta> listRespuestaCall = service.obtenerLista();

        listRespuestaCall.enqueue(new Callback<dineroRespuesta>() {
            @Override
            public void onResponse(Call<dineroRespuesta> call, Response<dineroRespuesta> response) {
                if(response.isSuccessful()){
                    dineroRespuesta lstRespuesta = response.body();
                    ArrayList<donacionDinero> lstInst = lstRespuesta.getResult();
                    adapter = new adapterDonacionDinero(getApplication(),lstInst);
                    lvDonaDinero.setAdapter(adapter);
                }else{
                    Log.e(TAG, "onResponse:"+ response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<dineroRespuesta> call, Throwable t) {
                Log.e(TAG, "onFailure: "+ t.getMessage());
            }
        });

    }
}
