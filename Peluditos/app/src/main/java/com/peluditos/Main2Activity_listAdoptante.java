package com.peluditos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Main2Activity_listAdoptante extends AppCompatActivity {

    private Button btnNuevo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2_list_adoptante);

        btnNuevo = findViewById(R.id.btnNuevo);
        btnNuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), Main2Activity_Adoptante.class );
                intent.putExtra("actualizar","0");
                startActivity(intent);
            }
        });
    }
}
