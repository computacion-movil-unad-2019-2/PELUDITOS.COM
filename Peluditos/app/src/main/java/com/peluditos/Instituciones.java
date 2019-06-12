package com.peluditos;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.entidades.CInstituciones;
import com.servicios.ServicioTask;

public class Instituciones extends AppCompatActivity {


    private EditText txtNit;
    private EditText txtRazonSocial;
    private EditText txtUbicacion;
    private EditText txtObjetivo;
    private Button btnGuardar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.instituciones);

        txtNit = (EditText) findViewById(R.id.txtNit);
        txtRazonSocial = (EditText) findViewById(R.id.txtRazonSocial);
        txtUbicacion = (EditText) findViewById(R.id.txtUbicacion);
        txtObjetivo = (EditText) findViewById(R.id.txtObjetivo);

        btnGuardar = findViewById(R.id.btnGuardar);

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                consumirServicio();
            }
        });
    }

    public void consumirServicio(){
        // ahora ejecutamos el hilo creado
        CInstituciones obj = new CInstituciones();
        obj.setNit(txtNit.getText().toString());
        obj.setRazonSocial(txtRazonSocial.getText().toString());
        obj.setObjetivo(txtObjetivo.getText().toString());
        obj.setUbicacion(txtUbicacion.getText().toString());


        ServicioTask serviciosTask = new ServicioTask(this,"http://www.hfgranadadev.com/api/Instituciones/2",obj);
        serviciosTask.execute();
    }
}
