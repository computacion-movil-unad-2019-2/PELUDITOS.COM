package com.peluditos;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.entidades.CDenunciamaltrato;
import com.entidades.CUsuario;
import com.servicios.ServicioTaskDenunciasmaltrato;
import com.servicios.ServicioTaskUsuario;

public class Main2Activity_registro extends AppCompatActivity {
    EditText txtUser, txtPwd, txtNames, txtIdentificacion;
    Button btnSesion, btnGuardar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2_registro);
        txtUser = (EditText) findViewById(R.id.txtUser);
        txtNames = (EditText) findViewById(R.id.txtNames);
        txtIdentificacion = (EditText) findViewById(R.id.txtIdentificacion);
        txtPwd = (EditText) findViewById(R.id.txtPwd);
        btnGuardar=(Button) findViewById(R.id.btnregistrar);

        String estado = getIntent().getStringExtra("actualizar");
        if(estado.equals("1")){
            String id = getIntent().getStringExtra("id");
            String user = getIntent().getStringExtra("user");
            String nombres = getIntent().getStringExtra("nombres");
            String documento = getIntent().getStringExtra("documento");
            String pwd = getIntent().getStringExtra("pwd");

            txtUser.setEnabled(false);
            txtNames.setEnabled(false);
            txtIdentificacion.setEnabled(false);
            txtPwd.setEnabled(false);

            txtUser.setText(user);
            txtNames.setText(nombres);
            txtIdentificacion.setText(documento);
            txtPwd.setText(pwd);
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
            if(        txtUser.getText().toString().trim().isEmpty()
                    || txtNames.getText().toString().trim().isEmpty()
                    || txtIdentificacion.getText().toString().trim().isEmpty()
                    || txtPwd.getText().toString().trim().isEmpty()
            ){

                Toast.makeText(getBaseContext(), "un campo esta vacio, y no se pudo guardar el Usuario",
                        Toast.LENGTH_LONG).show();

            }else{
                CUsuario obj = new CUsuario();
                obj.setNombres(txtNames.getText().toString());
                obj.setDocumento(txtIdentificacion.getText().toString());
                obj.setPwd(txtPwd.getText().toString());
                obj.setUser(txtUser.getText().toString());


                ServicioTaskUsuario ServicioTaskUsuario = new ServicioTaskUsuario
                        (this, "http://peluditos.online/rest/index.php/usuario/crear/", obj);
                ServicioTaskUsuario.execute();

                txtNames.setText("");
                txtIdentificacion.setText("");
                txtPwd.setText("");
                txtUser.setText("");

                Toast.makeText(getBaseContext(), "Guardado con Exito", Toast.LENGTH_LONG).show();
            }

        }
        catch (Exception e){
            e.printStackTrace();
            //Toast.makeText(getBaseContext(), "Error "+ e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}

