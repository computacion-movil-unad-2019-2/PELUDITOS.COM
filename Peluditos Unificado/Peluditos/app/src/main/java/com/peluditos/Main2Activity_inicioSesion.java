package com.peluditos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.entidades.User;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



public class Main2Activity_inicioSesion extends AppCompatActivity
        implements Response.Listener<JSONObject>, Response.ErrorListener {
    RequestQueue rq;
    JsonRequest jrq;
    EditText user, pwd;
    Button BtnSesion, BtnRegistrar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_sesion);
        user = (EditText) findViewById(R.id.usuario);
        pwd = (EditText) findViewById(R.id.pwd);
                BtnSesion=findViewById(R.id.BtnSesion);
        rq= (RequestQueue) Volley.newRequestQueue(getBaseContext());

        BtnSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iniciarSesion();
            }
        });


    }

    @Override
    public void onResponse(JSONObject response) {
        User usuario= new User();
        Toast.makeText(getBaseContext(), "Inicio de Sesion Exitoso",
                Toast.LENGTH_LONG).show();

        JSONArray jsonArray=response.optJSONArray("datos");
        JSONObject jsonObject=null;


        try {
            jsonObject=jsonArray.getJSONObject(0);
            usuario.setUser(jsonObject.optString("user"));
            usuario.setPwd(jsonObject.optString("pwd"));
            usuario.setNombres(jsonObject.optString("nombres"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Intent intencionmenu= new Intent(getBaseContext(), Main2Activity_Menu.class);
        //intencionmenu.putExtra(menu.nombres, usuario.getNombres());

        startActivity(intencionmenu);


    }

    private void iniciarSesion() {
        String url = "http://peluditos.mypressonline.com/sesion.php?" +
                "user="+ user.getText().toString()
                + "&pwd=" + pwd.getText().toString();


        jrq = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        rq.add(jrq);


    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getBaseContext(), "NO se encontro el usuario",
                Toast.LENGTH_LONG).show();
    }

    }




