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

        String estado = getIntent().getStringExtra("actualizar");
        if(estado.equals("1")){
            String id = getIntent().getStringExtra("id");
            String razonSocial = getIntent().getStringExtra("nombre");
            String objetivo = getIntent().getStringExtra("objetivo");
            String ubicacion = getIntent().getStringExtra("ubicacion");
            String nit = getIntent().getStringExtra("nit");

            txtNit.setText(nit);
            txtRazonSocial.setText(razonSocial);
            txtUbicacion.setText(ubicacion);
            txtObjetivo.setText(objetivo);

        }

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


        ServicioTask serviciosTask = new ServicioTask(this,"http://192.168.1.131:8080/rest/index.php/instituciones/crear/",obj);
        serviciosTask.execute();

        txtNit.setText("");
        txtRazonSocial.setText("");
        txtObjetivo.setText("");
        txtUbicacion.setText("");
    }
}
