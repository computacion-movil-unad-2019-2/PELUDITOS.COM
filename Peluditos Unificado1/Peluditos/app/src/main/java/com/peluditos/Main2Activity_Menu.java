package com.peluditos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Main2Activity_Menu extends AppCompatActivity {


        private ListView lv1;

        private String opciones [] = {"Instituciones","Mascotas","Adoptante", "Adopta",
                "Animales Perdidos", "Denuncias Maltrato"};
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_menu);

            lv1 = (ListView) findViewById(R.id.lv1);

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_item_opciones, opciones);
            lv1.setAdapter(adapter);

            lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String opcion = opciones[position];
                    if(opcion == "Instituciones"){
                        //Toast.makeText(MainActivity.this, opcion, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Main2Activity_Menu.this, listInstituciones.class );
                        startActivity(intent);
                    }else if(opcion == "Mascotas"){
                        //Toast.makeText(MainActivity.this, opcion, Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(Main2Activity_Menu.this, listMascota.class);
                        //Intent i = new Intent(Main2Activity_Menu.this, mascotas.class);
                        startActivity(i);
                    }else if(opcion == "Adoptante"){
                        //Toast.makeText(MainActivity.this, opcion, Toast.LENGTH_SHORT).show();
                        Intent in = new Intent(Main2Activity_Menu.this, lstAdoptante.class);
                        startActivity(in);
                    }
                    else if(opcion == "Adopta"){
                        //Toast.makeText(MainActivity.this, opcion, Toast.LENGTH_SHORT).show();
                        Intent in = new Intent(Main2Activity_Menu.this, adopta.class);
                        startActivity(in);
                    }
                    else if(opcion == "Calificar Servicio"){
                        //Toast.makeText(MainActivity.this, opcion, Toast.LENGTH_SHORT).show();
                        Intent in = new Intent(Main2Activity_Menu.this, calificarServicio.class);
                        startActivity(in);
                    }
                    else if(opcion == "Guarderias"){
                        //Toast.makeText(MainActivity.this, opcion, Toast.LENGTH_SHORT).show();
                        Intent in = new Intent(Main2Activity_Menu.this, listGuarderias.class);
                        startActivity(in);
                    }
                    else if(opcion == "Eventos"){
                        //Toast.makeText(MainActivity.this, opcion, Toast.LENGTH_SHORT).show();
                        Intent in = new Intent(Main2Activity_Menu.this, listEvento.class);
                        startActivity(in);
                    }else if(opcion == "Donacion en especie"){
                        //Toast.makeText(MainActivity.this, opcion, Toast.LENGTH_SHORT).show();
                        Intent in = new Intent(Main2Activity_Menu.this, listDonacionEspecie.class);
                        startActivity(in);
                    }else if (opcion == "Animales Perdidos") {
                        //Toast.makeText(MainActivity.this, opcion, Toast.LENGTH_SHORT).show();
                        Intent in = new Intent(Main2Activity_Menu.this, Main2Activity_list_encontraranimal.class);
                        startActivity(in);
                    } else if (opcion == "Denuncias Maltrato") {
                        //Toast.makeText(MainActivity.this, opcion, Toast.LENGTH_SHORT).show();
                        Intent in = new Intent(Main2Activity_Menu.this, Main2Activity_denunciaslist.class);
                        startActivity(in);
                    }
                    else if (opcion == "Seguimientos Denuncias Maltrato") {
                        //Toast.makeText(MainActivity.this, opcion, Toast.LENGTH_SHORT).show();
                        Intent in = new Intent(Main2Activity_Menu.this, listseguimientodenuncia.class);
                        startActivity(in);
                    }


                }
            });
        }

}