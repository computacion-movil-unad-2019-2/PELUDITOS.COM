package com.peluditos;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

        String estado = getIntent().getStringExtra("actualizar");
        if(estado.equals("1")){
            String id = getIntent().getStringExtra("id");
            String razonSocial = getIntent().getStringExtra("nombre");
            String objetivo = getIntent().getStringExtra("objetivo");
            String ubicacion = getIntent().getStringExtra("ubicacion");
            String nit = getIntent().getStringExtra("nit");

            txtNit.setEnabled(false);
            txtRazonSocial.setEnabled(false);
            txtUbicacion.setEnabled(false);
            txtObjetivo.setEnabled(false);

            txtNit.setText(nit);
            txtRazonSocial.setText(razonSocial);
            txtUbicacion.setText(ubicacion);
            txtObjetivo.setText(objetivo);

            btnGuardar.setVisibility(View.INVISIBLE);
        }



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
            if(txtNit.getText().toString().trim().isEmpty()
                    || txtRazonSocial.getText().toString().trim().isEmpty()
                    || txtObjetivo.getText().toString().trim().isEmpty()
                    || txtUbicacion.getText().toString().trim().isEmpty()){

                Toast.makeText(getBaseContext(), "un campo esta vacio, y no se pudo guardar la Institución", Toast.LENGTH_LONG).show();

            }else{
                CInstituciones obj = new CInstituciones();
                obj.setNit(txtNit.getText().toString());
                obj.setRazonSocial(txtRazonSocial.getText().toString());
                obj.setObjetivo(txtObjetivo.getText().toString());
                obj.setUbicacion(txtUbicacion.getText().toString());


                ServicioTask serviciosTask = new ServicioTask(this, "http://peluditos.online/rest/index.php/instituciones/crear/", obj);
                serviciosTask.execute();

                txtNit.setText("");
                txtRazonSocial.setText("");
                txtObjetivo.setText("");
                txtUbicacion.setText("");

                Toast.makeText(getBaseContext(), "Guardado con Exito", Toast.LENGTH_LONG).show();
            }

        }
        catch (Exception e){
            e.printStackTrace();
            //Toast.makeText(getBaseContext(), "Error "+ e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
