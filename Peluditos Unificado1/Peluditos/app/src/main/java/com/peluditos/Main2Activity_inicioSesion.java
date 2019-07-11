package com.peluditos;

import android.content.Intent;
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

import static java.security.AccessController.getContext;

public class Main2Activity_inicioSesion extends AppCompatActivity {
    EditText  user, pwd;
    Button btnGuardar, BtnRegistrar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_sesion);

        user= (EditText) findViewById(R.id.usuario);
        pwd= (EditText) findViewById(R.id.pwd);
        btnGuardar=(Button) findViewById(R.id.BtnSesion);

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

                obj.setPwd(pwd.getText().toString());
                obj.setUser(user.getText().toString());


                ServicioTaskUsuario ServicioTaskUsuario = new ServicioTaskUsuario
                        (this, "http://peluditos.online/rest/index.php/usuario/sesion/", obj);
                ServicioTaskUsuario.execute();

                pwd.setText("");
                user.setText("");
                Toast.makeText(getBaseContext(), "Inicio de Sesion Exitoso", Toast.LENGTH_LONG).show();

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

