package com.peluditos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.entidades.mascota;
import com.entidades.mascotaRespuesta;
import com.entidades.tipo;
import com.entidades.tipoMascota;
import com.entidades.tipoRespuesta;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.servicios.PostServiceMascota;
import com.servicios.PostTipo;

import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class adopta extends AppCompatActivity {

    private static final String TAG = "PELUDITOS";
    private Retrofit retrofit;
    private Spinner spinner1;
    adapterAdopta adapter;
    private ListView lvAdopta;
    private ArrayAdapter<String> lstMascota;
    private String tipo;
    private String codigo;
    adapterTipo tpAdapter;
    private String [] lstLista;
    private mascota MascotaObj;
    adapterMascota adapterMascota;

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    private String nombre;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adopta);

        lvAdopta =  (ListView)findViewById(R.id.lvAdopta);

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        retrofit = new Retrofit.Builder()
                .baseUrl("http://peluditos.online/rest/index.php/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        spinner1 = (Spinner)findViewById(R.id.spinnerMascotas);
        cargarTipo();
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                obtenerMascota();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        lvAdopta.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try{
                    mascota obj1 = (mascota) adapterMascota.getItem(position);
                    Intent in = new Intent(getBaseContext(), hojaMascota.class);
                    in.putExtra("actualizar","1");
                    in.putExtra("id", obj1.getId());
                    in.putExtra("nombres", obj1.getNombres());
                    in.putExtra("edad", obj1.getEdad());
                    in.putExtra("tamano", obj1.getTamano());
                    in.putExtra("controlMedico", obj1.getControlMedico());
                    in.putExtra("ciudadReferencia", obj1.getCiudadReferencia());
                    in.putExtra("ubicacion", obj1.getUbicacion());
                    in.putExtra("tipo", obj1.getTipo());
                    in.putExtra("foto",obj1.getFoto());
                    in.putExtra("estado",obj1.getEstado());
                    finish();
                    startActivity(in);
                }catch(Exception ex){
                    ex.printStackTrace();
                }
            }
        });
    }

    private void cargarTipo(){
        PostTipo service = retrofit.create(PostTipo.class);
        Call<tipoRespuesta> listRespuestaCall = service.obtenerLista();

        listRespuestaCall.enqueue(new Callback<tipoRespuesta>() {
            @Override
            public void onResponse(Call<tipoRespuesta> call, Response<tipoRespuesta> response) {

                if(response.isSuccessful()){
                    tipoRespuesta lstRespuesta = response.body();
                    ArrayList<tipo> lstAdop = lstRespuesta.getResult();
                    lstLista = new String[lstAdop.size()];
                    int i = 0;
                    for(tipo item : lstAdop)
                    {
                        lstLista[i] = item.getNombres();
                        i += 1;
                    }
                    lstMascota = new ArrayAdapter<String>(getApplication(),android.R.layout.simple_spinner_item, lstLista);
                    spinner1.setAdapter(lstMascota);

                }else{
                    Log.e(TAG, "onResponse:"+ response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<tipoRespuesta> call, Throwable t) {
                Log.e(TAG, "onFailure: "+ t.getMessage());
            }
        });
    }

    private void obtenerMascota( ){

        PostServiceMascota service = retrofit.create(PostServiceMascota.class);
        Call<mascotaRespuesta> listRespuestaCall = service.obtenerLista();

        listRespuestaCall.enqueue(new Callback<mascotaRespuesta>() {
            @Override
            public void onResponse(Call<mascotaRespuesta> call, Response<mascotaRespuesta> response) {
                if(response.isSuccessful()){
                    mascotaRespuesta lstRespuesta = response.body();
                    ArrayList<mascota> lstAdop = lstRespuesta.getResult();

                    ArrayList<tipoMascota> lstAdop1 = new ArrayList<tipoMascota>();
                    ArrayList<mascota> lstMascota = new ArrayList<mascota>();
                    String tpMascota = spinner1.getSelectedItem().toString().trim();
                    for( mascota item : lstAdop){
                        if(item.getTipo().trim().equals(tpMascota.toString().trim())){
                            tipoMascota obj = new tipoMascota();
                            obj.setId(item.getId());
                            obj.setNombre(item.getNombres());
                            lstAdop1.add(obj);

                            lstMascota.add(item);
                        }
                    }
                    adapter = new adapterAdopta(getApplication(),lstAdop1);
                    lvAdopta.setAdapter(adapter);

                    adapterMascota = new adapterMascota(getApplication(),lstMascota);
                }else{
                    Log.e(TAG, "onResponse:"+ response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<mascotaRespuesta> call, Throwable t) {
                Log.e(TAG, "onFailure: "+ t.getMessage());
            }
        });
    }

}
