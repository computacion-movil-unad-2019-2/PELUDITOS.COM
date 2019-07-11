package com.peluditos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private ListView lv1;

    private String opciones [] = {"Instituciones","Mascotas","Adoptante", "Adopta","Calificar Servicio","Guarderias","Eventos","Donacion en especie",
            "Clubes de mascotas","Comedores Comunitarios","Experiencias","Donaciones en Dinero"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv1 = (ListView)findViewById(R.id.lv1);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_item_opciones, opciones);
        lv1.setAdapter(adapter);

        lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String opcion = opciones[position];
                if(opcion == "Instituciones"){
                    //Toast.makeText(MainActivity.this, opcion, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, listInstituciones.class );
                    startActivity(intent);
                }else if(opcion == "Mascotas"){
                    //Toast.makeText(MainActivity.this, opcion, Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(MainActivity.this, listMascota.class);
                    //Intent i = new Intent(MainActivity.this, mascotas.class);
                    startActivity(i);
                }else if(opcion == "Adoptante"){
                    //Toast.makeText(MainActivity.this, opcion, Toast.LENGTH_SHORT).show();
                    Intent in = new Intent(MainActivity.this, lstAdoptante.class);
                    startActivity(in);
                }
                else if(opcion == "Adopta"){
                    //Toast.makeText(MainActivity.this, opcion, Toast.LENGTH_SHORT).show();
                    Intent in = new Intent(MainActivity.this, adopta.class);
                    startActivity(in);
                }
                else if(opcion == "Calificar Servicio"){
                    //Toast.makeText(MainActivity.this, opcion, Toast.LENGTH_SHORT).show();
                    Intent in = new Intent(MainActivity.this, calificarServicio.class);
                    startActivity(in);
                }
                else if(opcion == "Guarderias"){
                    //Toast.makeText(MainActivity.this, opcion, Toast.LENGTH_SHORT).show();
                    Intent in = new Intent(MainActivity.this, listGuarderias.class);
                    startActivity(in);
                }
                else if(opcion == "Eventos"){
                    //Toast.makeText(MainActivity.this, opcion, Toast.LENGTH_SHORT).show();
                    Intent in = new Intent(MainActivity.this, listEvento.class);
                    startActivity(in);
                }else if(opcion == "Donacion en especie"){
                    //Toast.makeText(MainActivity.this, opcion, Toast.LENGTH_SHORT).show();
                    Intent in = new Intent(MainActivity.this, listDonacionEspecie.class);
                    startActivity(in);
                }
                else if(opcion == "Clubes de mascotas"){
                    //Toast.makeText(MainActivity.this, opcion, Toast.LENGTH_SHORT).show();
                    Intent in = new Intent(MainActivity.this, listClubes.class);
                    startActivity(in);
                }else if(opcion == "Comedores Comunitarios"){
                    //Toast.makeText(MainActivity.this, opcion, Toast.LENGTH_SHORT).show();
                    Intent in = new Intent(MainActivity.this, listComedores.class);
                    startActivity(in);
                }
                else if(opcion == "Experiencias"){
                    //Toast.makeText(MainActivity.this, opcion, Toast.LENGTH_SHORT).show();
                    Intent in = new Intent(MainActivity.this, listExperiencias.class);
                    startActivity(in);
                }else if(opcion == "Donaciones en Dinero"){
                    //Toast.makeText(MainActivity.this, opcion, Toast.LENGTH_SHORT).show();
                    Intent in = new Intent(MainActivity.this, listDonacionDinero.class);
                    startActivity(in);
                }
            }
        });
    }
}
