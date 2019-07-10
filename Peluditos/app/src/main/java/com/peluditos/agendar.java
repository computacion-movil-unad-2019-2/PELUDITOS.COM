package com.peluditos;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.converter.gson.GsonConverterFactory;

import retrofit2.Retrofit;

public class agendar extends AppCompatActivity {

    private EditText txtFecha;
    private EditText txtComentarioAgenda;
    private EditText txtUbicacion;
    private EditText txtHora;
    private Button btnAgendar;
    private Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agendar);

        txtFecha = (EditText) findViewById(R.id.txtFechaAgenda);
        txtHora = (EditText) findViewById(R.id.txtHoraAgenda);
        txtUbicacion = (EditText) findViewById(R.id.txtUbicacionAgenda);
        txtComentarioAgenda = (EditText) findViewById(R.id.txtComentarioAgenda);

        txtFecha.setText("");
        txtHora.setText("");
        txtUbicacion.setText("");
        txtComentarioAgenda.setText("");

        retrofit = new Retrofit.Builder()
                .baseUrl("http://peluditos.online/rest/index.php/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        btnAgendar = findViewById(R.id.btnAgendar);
        btnAgendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    String strFecha = txtFecha.getText().toString();
                    String strHora = txtHora.getText().toString();
                    String strUbicacion = txtUbicacion.getText().toString();
                    String strComentario = txtComentarioAgenda.getText().toString();

                    if(strFecha.toString().trim().isEmpty()
                            || strHora.toString().trim().isEmpty()
                            || strUbicacion.toString().trim().isEmpty()
                            || strComentario.toString().trim().isEmpty()){

                        Toast.makeText(getBaseContext(), "un campo esta vacio, y no se pudo Agendar la Cita", Toast.LENGTH_LONG).show();

                    }else{
                        sendPost(strFecha,strHora,strUbicacion,strComentario);
                    }
                }catch(Exception e){
                    e.printStackTrace();
                    Toast.makeText(getBaseContext(), "Error "+ e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void sendPost(String strFecha,String strHora,String strUbicacion,String strComentario){
        try{

        }catch(Exception e){
            e.printStackTrace();
            Toast.makeText(getBaseContext(), "Error "+ e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
