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

import com.entidades.adoptante;
import com.entidades.adoptanteRespuesta;
import com.servicios.PostServiceAdoptante;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class lstAdoptante extends AppCompatActivity {

    private static final String TAG = "PELUDITOS";
    private Button btnNuevo;
    private Retrofit retrofit;
    private ListView lvAdoptante;
    adapterAdoptante adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lst_adoptante);

        lvAdoptante =  (ListView)findViewById(R.id.lvAdoptante);
        retrofit = new Retrofit.Builder()
                .baseUrl("http://peluditos.online/rest/index.php/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        btnNuevo = findViewById(R.id.btnNuevoAdoptante);
        btnNuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), Main2Activity_Adoptante.class );
                intent.putExtra("actualizar","0");
                startActivity(intent);
            }
        });

        obtenerDatos();

        lvAdoptante.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                try{
                    adoptante obj = (adoptante) adapter.getItem(position);
                    Intent in = new Intent(getBaseContext(), Main2Activity_Adoptante.class);
                    in.putExtra("actualizar","1");
                    in.putExtra("id", obj.getId());
                    in.putExtra("nombres", obj.getNombres());
                    in.putExtra("apellidos", obj.getApellidos());
                    in.putExtra("edad", obj.getEdad());
                    in.putExtra("sexo", obj.getSexo());
                    in.putExtra("estadoCivil", obj.getEstadoCivil());
                    in.putExtra("correoElectronico", obj.getCorreoElectronico());
                    in.putExtra("numTelefono", obj.getNumTelefono());
                    in.putExtra("tipoMascota", obj.getTipoMascota());
                    in.putExtra("cedula", obj.getCedula());
                    startActivity(in);
                }catch(Exception ex){
                    ex.printStackTrace();
                }
            }
        });
    }

    private void obtenerDatos() {

        PostServiceAdoptante service = retrofit.create(PostServiceAdoptante.class);
        Call<adoptanteRespuesta> listRespuestaCall = service.obtenerLista();

        listRespuestaCall.enqueue(new Callback<adoptanteRespuesta>() {
            @Override
            public void onResponse(Call<adoptanteRespuesta> call, Response<adoptanteRespuesta> response) {
                if(response.isSuccessful()){
                    adoptanteRespuesta lstRespuesta = response.body();
                    ArrayList<adoptante> lstAdop = lstRespuesta.getResult();
                    adapter = new adapterAdoptante(getApplication(),lstAdop);
                    lvAdoptante.setAdapter(adapter);
                }else{
                    Log.e(TAG, "onResponse:"+ response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<adoptanteRespuesta> call, Throwable t) {
                Log.e(TAG, "onFailure: "+ t.getMessage());
            }
        });

    }
}
