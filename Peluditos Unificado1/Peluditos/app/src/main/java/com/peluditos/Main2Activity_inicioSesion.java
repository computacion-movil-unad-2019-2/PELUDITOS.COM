package com.peluditos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.entidades.CUsuario;
import com.entidades.tipoInstitucion;
import com.entidades.usuarioRespuesta;
import com.servicios.PostServiceUsuario;
import com.servicios.ServicioTaskUsuario;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static java.security.AccessController.getContext;

public class Main2Activity_inicioSesion extends AppCompatActivity {
    private static final String TAG = "PELUDITOS";
    EditText  user, pwd;
    Button btnGuardar, BtnRegistrar;
    private Retrofit retrofit;
    private String [] lstLista;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_sesion);

        user= (EditText) findViewById(R.id.usuario);
        pwd= (EditText) findViewById(R.id.pwd);
        btnGuardar=(Button) findViewById(R.id.BtnSesion);

        retrofit = new Retrofit.Builder()
                .baseUrl("http://peluditos.online/rest/index.php/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                consumirServicio();
            }
        });
    }
    public void consumirServicio(){
        // ahora ejecutamos el hilo creado
        try {
            if(        user.getText().toString().trim().isEmpty()
                    || pwd.getText().toString().trim().isEmpty()

            ){

                Toast.makeText(getBaseContext(), "un campo esta vacio, y no se pudo guardar el Usuario",
                        Toast.LENGTH_LONG).show();

            }else{
                CUsuario obj = new CUsuario();
            cargarTipo();
                //obj.setPwd(pwd.getText().toString());
                //obj.setUser(user.getText().toString());



             //   ServicioTaskUsuario ServicioTaskUsuario = new ServicioTaskUsuario
               //         (this, "http://peluditos.online/rest/index.php/usuario/sesion/", obj);
              //  ServicioTaskUsuario.execute();

               // String userx = obj.getUser();

               // pwd.setText("");
               // user.setText("");
              //  Toast.makeText(getBaseContext(), "Inicio de Sesion Exitoso"+userx, Toast.LENGTH_LONG).show();

               // Intent intent = new Intent(getBaseContext(), Main2Activity_Menu.class );
               // startActivity(intent);

            }

        }
        catch (Exception e){
            e.printStackTrace();
            //Toast.makeText(getBaseContext(), "Error "+ e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void cargarTipo(){
        PostServiceUsuario service = retrofit.create(PostServiceUsuario.class);
        Call<usuarioRespuesta> listRespuestaCall = service.obtenerLista();

        listRespuestaCall.enqueue(new Callback<usuarioRespuesta>() {
            @Override
            public void onResponse(Call<usuarioRespuesta> call, Response<usuarioRespuesta> response) {

                if(response.isSuccessful()){
                    usuarioRespuesta lstRespuesta = response.body();
                    CUsuario obj = new CUsuario();
                    ArrayList<CUsuario> lstAdop = lstRespuesta.getResult();
                    lstLista = new String[lstAdop.size()];
                    //int i = 0;

                    for (int i = 0; i < lstLista.length; i++) {
                        //Log.d(TAG, lstLista[i]);
                        //System.out.println(lstLista[i]);
                        Toast.makeText(getBaseContext(), " "+ obj.getUser(), Toast.LENGTH_LONG).show();
                    }
                    /*for(CUsuario item : lstAdop)
                    {
                        lstLista[i] = item.getUser();
                        i += 1;
                        Log.e(TAG, "onResponse:"+ item.getPwd());
                    }*/


                }else{
                    Log.e(TAG, "onResponse:"+ response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<usuarioRespuesta> call, Throwable t) {
                Log.e(TAG, "onFailure: "+ t.getMessage());
            }
        });
    }

}

