package com.peluditos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.entidades.CUsuario;
import com.entidades.usuarioRespuesta;
import com.servicios.PostLoginUser;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserLogin extends AppCompatActivity {

    private static final String TAG = "PELUDITOS";
    private EditText txtUser;
    private EditText txtPassword;
    private Button btnGuardar;
    private Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);
        txtUser = (EditText) findViewById(R.id.txtUser);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        btnGuardar = findViewById(R.id.BtnLogin);

        retrofit = new Retrofit.Builder()
                .baseUrl("http://peluditos.online/rest/index.php/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        btnGuardar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick (View v){
                PostLoginUser service = retrofit.create(PostLoginUser.class);
                Call<usuarioRespuesta> listRespuestaCall = service.obtenerLista();
                listRespuestaCall.enqueue(new Callback<usuarioRespuesta>() {
                    @Override
                    public void onResponse(Call<usuarioRespuesta> call, Response<usuarioRespuesta> response) {
                        if(txtUser.getText().toString().trim().isEmpty() || txtPassword.getText().toString().trim().isEmpty())
                        {
                            Toast.makeText(getBaseContext(), "Complete los campos", Toast.LENGTH_LONG).show();
                        }else {
                            if (response.isSuccessful()) {
                                usuarioRespuesta lstRespuesta = response.body();
                                ArrayList<CUsuario> listUsers = lstRespuesta.getResult();

                                for (CUsuario item : listUsers) {
                                    if (item.getUser().trim().equals(txtUser.getText().toString().trim()) && item.getPwd().trim().equals(txtPassword.getText().toString().trim())) {
                                        Toast.makeText(getBaseContext(), "Bienvenido", Toast.LENGTH_LONG).show();

                                        Intent intent = new Intent(getBaseContext(), Main2Activity_Menu.class);
                                        startActivity(intent);

                                    } else {
                                        Toast.makeText(getBaseContext(), "", Toast.LENGTH_LONG).show();
                                    }
                                }
                            } else {
                                Log.e(TAG, "onResponse:" + response.errorBody());
                            }
                        }
                    }
                    @Override
                    public void onFailure(Call<usuarioRespuesta> call, Throwable t) {
                        Log.e(TAG, "onFailure: " + t.getMessage());
                    }
                });
            }
        });
    }
}
