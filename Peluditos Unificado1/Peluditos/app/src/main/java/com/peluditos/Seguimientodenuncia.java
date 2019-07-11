package com.peluditos;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.entidades.CSeguimientodenunciamaltrato;
import com.servicios.ServicioTaskSeguimientodenunciasmaltrato;

public class Seguimientodenuncia extends AppCompatActivity {
    EditText txtId, txtseguimiento, txtNmascota,txtCaracteristicas;
    private Button btnGuardar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seguimientodenuncia);

        txtId = (EditText) findViewById(R.id.txtId);
        txtseguimiento = (EditText) findViewById(R.id.txtseguimiento);

        btnGuardar = findViewById(R.id.BtnSeguimiento);

        String estado = getIntent().getStringExtra("actualizar");
        if(estado.equals("1")){
            String id = getIntent().getStringExtra("id");
            String iddenuncia = getIntent().getStringExtra("iddenuncia");
            String seguimiento = getIntent().getStringExtra("seguimiento");


            txtId.setEnabled(false);
            txtseguimiento.setEnabled(false);


            txtId.setText(iddenuncia);
            txtseguimiento.setText(seguimiento);


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
            if(txtId.getText().toString().trim().isEmpty()
                    || txtseguimiento.getText().toString().trim().isEmpty()
                    ){

                Toast.makeText(getBaseContext(), "un campo esta vacio, y no se pudo guardar El Reporte", Toast.LENGTH_LONG).show();

            }else{
                CSeguimientodenunciamaltrato obj = new CSeguimientodenunciamaltrato();
                obj.setSeguimiento(txtseguimiento.getText().toString());
                obj.setIddenuncia(txtId.getText().toString());


                ServicioTaskSeguimientodenunciasmaltrato ServicioTaskSeguimientodenunciasmaltrato = new ServicioTaskSeguimientodenunciasmaltrato
                        (this, "http://peluditos.online/rest/index.php/encontraranimal/crear/", obj);
                ServicioTaskSeguimientodenunciasmaltrato.execute();

                txtseguimiento.setText("");
                txtId.setText("");


                Toast.makeText(getBaseContext(), "Guardado con Exito", Toast.LENGTH_LONG).show();
            }

        }
        catch (Exception e){
            e.printStackTrace();
            //Toast.makeText(getBaseContext(), "Error "+ e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
