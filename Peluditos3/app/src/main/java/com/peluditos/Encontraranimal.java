package com.peluditos;

        import android.os.Bundle;
        import android.support.v7.app.AppCompatActivity;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.Toast;
        import com.entidades.CEncontraranimal;
        import com.servicios.ServicioTaskEncontrarAnimal;

public class Encontraranimal extends AppCompatActivity {


    private static final String TAG = "PELUDITOS";
    EditText txtRaza, txtUbicacion, txtNmascota,txtCaracteristicas;
    private Button btnGuardar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encontraranimal);


        txtRaza = (EditText) findViewById(R.id.txtRaza);
        txtNmascota = (EditText) findViewById(R.id.txtNmascota);
        txtUbicacion = (EditText) findViewById(R.id.txtUbicacion);
        txtCaracteristicas = (EditText) findViewById(R.id.txtCaracteristicas);
        btnGuardar = findViewById(R.id.Btnaddperdida);

        String estado = getIntent().getStringExtra("actualizar");
        if(estado.equals("1")){
            String id = getIntent().getStringExtra("id");
            String raza = getIntent().getStringExtra("raza");
            String nombremascota = getIntent().getStringExtra("nombre");
            String ubicacion = getIntent().getStringExtra("ubicacion");
            String caracteristicas = getIntent().getStringExtra("caracteristicas");

            txtRaza.setEnabled(false);
            txtNmascota.setEnabled(false);
            txtUbicacion.setEnabled(false);
            txtCaracteristicas.setEnabled(false);

            txtRaza.setText(raza);
            txtNmascota.setText(nombremascota);
            txtUbicacion.setText(ubicacion);
            txtCaracteristicas.setText(caracteristicas);

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
            if(txtRaza.getText().toString().trim().isEmpty()
                    || txtNmascota.getText().toString().trim().isEmpty()
                    || txtCaracteristicas.getText().toString().trim().isEmpty()
                    || txtUbicacion.getText().toString().trim().isEmpty()){

                Toast.makeText(getBaseContext(), "un campo esta vacio, y no se pudo guardar El Reporte", Toast.LENGTH_LONG).show();

            }else{
                CEncontraranimal obj = new CEncontraranimal();
                obj.setRaza(txtRaza.getText().toString());
                obj.setNombremascota(txtNmascota.getText().toString());
                obj.setCaracteristicas(txtCaracteristicas.getText().toString());
                obj.setUbicacion(txtUbicacion.getText().toString());


                ServicioTaskEncontrarAnimal ServicioTaskEncontrarAnimal = new ServicioTaskEncontrarAnimal
                        (this, "http://peluditos.online/rest/index.php/encontraranimal/crear/", obj);
                ServicioTaskEncontrarAnimal.execute();

                txtRaza.setText("");
                txtNmascota.setText("");
                txtCaracteristicas.setText("");
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

