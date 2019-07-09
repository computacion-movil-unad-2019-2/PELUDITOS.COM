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
        txtSeguimiento= (EditText) findViewById(R.id.txtSeguimiento);
        seguimiento=(TextView)  findViewById(R.id.TxtVseguimiento);

        btnGuardar=(Button) findViewById(R.id.denunciarBtn);
        btnActualizar=(Button) findViewById(R.id.BtnActualizar);
        btnEliminar = (Button) findViewById(R.id.BtnEliminar);
        BtnAddSeguimiento = (Button) findViewById(R.id.BtnAddSeguimiento);

        String estado = getIntent().getStringExtra("actualizar");
        if(estado.equals("1")){
            String id = getIntent().getStringExtra("id");
            String ubicacion = getIntent().getStringExtra("ubicacion");
            String descripcion = getIntent().getStringExtra("nombre");
            txtUbicacion.setEnabled(true);
            txtDescripcion.setEnabled(true);

            txtUbicacion.setText(ubicacion);
            txtDescripcion.setText(descripcion);
            btnGuardar.setVisibility(View.INVISIBLE);

            btnEliminar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    eliminarServicio();

                }
            });

            BtnAddSeguimiento.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ConsumirServicio2();

                }
            });

            btnActualizar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    consumirServicio();

                }
            });



        } else {
            btnEliminar.setVisibility(View.INVISIBLE);
            btnActualizar.setVisibility(View.INVISIBLE);
            BtnAddSeguimiento.setVisibility(View.INVISIBLE);
            txtSeguimiento.setVisibility(View.INVISIBLE);
            seguimiento.setVisibility(View.INVISIBLE);


            btnGuardar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    consumirServicio();

                }
            });



        }




    }

    public void eliminarServicio(){
        // ahora ejecutamos el hilo creado
        try {
                CDenunciamaltrato obj = new CDenunciamaltrato();
                obj.setDescripcion(txtDescripcion.getText().toString());
                obj.setUbicacion(txtUbicacion.getText().toString());


                ServicioTaskDenunciasmaltrato ServicioTaskEncontrarAnimal = new ServicioTaskDenunciasmaltrato
                        (this, "http://peluditos.online/rest/index.php/denunciamaltrato/eliminar/", obj);
                ServicioTaskEncontrarAnimal.execute();

                txtDescripcion.setText("");
                txtUbicacion.setText("");

                Toast.makeText(getBaseContext(), "Eliminado con Exito", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(getBaseContext(), Main2Activity_Menu.class );
            startActivity(intent);


        }
        catch (Exception e){
            e.printStackTrace();
            //Toast.makeText(getBaseContext(), "Error "+ e.getMessage(), Toast.LENGTH_LONG).show();
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


                ServicioTaskDenunciasmaltrato ServicioTaskEncontrarAnimal = new ServicioTaskDenunciasmaltrato
                        (this, "http://peluditos.online/rest/index.php/denunciamaltrato/crear/", obj);
                ServicioTaskEncontrarAnimal.execute();

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

    public void ConsumirServicio2(){
        try {
            if(        txtSeguimiento.getText().toString().trim().isEmpty()

            ){

                Toast.makeText(getBaseContext(), "un campo esta vacio, y no se pudo guardar la Denuncia",
                        Toast.LENGTH_LONG).show();

            }else{
                CDenunciamaltrato obj = new CDenunciamaltrato();
                obj.setSeguimiento(txtSeguimiento.getText().toString());



                ServicioTaskDenunciasmaltrato ServicioTaskEncontrarAnimal = new ServicioTaskDenunciasmaltrato
                        (this, "http://peluditos.online/rest/index.php/denunciamaltrato/crearseguimiento/", obj);
                ServicioTaskEncontrarAnimal.execute();

             //   txtDescripcion.setText("");
               // txtUbicacion.setText("");


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

