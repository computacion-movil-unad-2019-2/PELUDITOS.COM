package com.peluditos;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.entidades.CDenunciamaltrato;
import com.entidades.CSeguimientodenunciamaltrato;
import com.servicios.ServicioTaskDenunciasmaltrato;
import com.servicios.ServicioTaskSeguimientodenunciasmaltrato;

public class Denunciasmaltrato extends AppCompatActivity {
    private static final String TAG = "PELUDITOS";
    EditText txtDescripcion, txtUbicacion, txtSeguimiento;
    TextView seguimiento;
    private Button btnGuardar, btnEliminar, btnActualizar, BtnAddSeguimiento;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_denunciasmaltrato);

        txtUbicacion = (EditText) findViewById(R.id.txtUbicacion);
        txtDescripcion = (EditText) findViewById(R.id.txtDescripcion);


        btnGuardar=(Button) findViewById(R.id.denunciarBtn);



        String estado = getIntent().getStringExtra("actualizar");
        if(estado.equals("1")){
            String id = getIntent().getStringExtra("id");
            String ubicacion = getIntent().getStringExtra("ubicacion");
            String descripcion = getIntent().getStringExtra("nombre");
            txtUbicacion.setEnabled(false);
            txtDescripcion.setEnabled(false);

            txtUbicacion.setText(ubicacion);
            txtDescripcion.setText(descripcion);
            btnGuardar.setVisibility(View.INVISIBLE);








        } else {



            btnGuardar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    consumirServicio();

                }
            });



        }




    }


    public void consumirServicio(){
        // ahora ejecutamos el hilo creado
        try {
            if(        txtUbicacion.getText().toString().trim().isEmpty()
                    || txtDescripcion.getText().toString().trim().isEmpty()
                 ){

                Toast.makeText(getBaseContext(), "un campo esta vacio, y no se pudo guardar la Denuncia",
                        Toast.LENGTH_LONG).show();

            }else{
                CDenunciamaltrato obj = new CDenunciamaltrato();
                obj.setDescripcion(txtDescripcion.getText().toString());
                obj.setUbicacion(txtUbicacion.getText().toString());


                ServicioTaskDenunciasmaltrato ServicioTaskDenunciasmaltrato = new ServicioTaskDenunciasmaltrato
                        (this, "http://peluditos.online/rest/index.php/denunciamaltrato/crear/", obj);
                ServicioTaskDenunciasmaltrato.execute();

                txtDescripcion.setText("");
                txtUbicacion.setText("");


                Toast.makeText(getBaseContext(), "Guardado con Exito", Toast.LENGTH_LONG).show();

                Intent intent = new Intent(getBaseContext(), Main2Activity_Menu.class );
                startActivity(intent);
            }

        }
        catch (Exception e){
            e.printStackTrace();
            //Toast.makeText(getBaseContext(), "Error "+ e.getMessage(), Toast.LENGTH_LONG).show();
        }



    }


}

