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

    private String opciones [] = {"Instituciones","Mascotas","Adoptante", "Adopta","Ejemplo"};
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
                else if(opcion == "Ejemplo"){
                    //Toast.makeText(MainActivity.this, opcion, Toast.LENGTH_SHORT).show();
                    Intent in = new Intent(MainActivity.this, adopta.class);
                    startActivity(in);
                }
            }
        });
    }
}
