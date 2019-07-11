package com.peluditos;

import android.app.Application;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.entidades.CInstituciones;
import com.entidades.CSeguimientodenunciamaltrato;

import java.util.ArrayList;

public class AdapterSeguimientodenuncia {

    private Context context;
    private ArrayList<CSeguimientodenunciamaltrato> arrayList;

    public AdapterSeguimientodenuncia(Application application, ArrayList<CSeguimientodenunciamaltrato> lstInst) {
    }


    public int getCount() {
        return arrayList.size();
    }


    public Object getItem(int position) {
        return arrayList.get(position);
    }


    public long getItemId(int position) {
        return position;
    }


    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.iteminst,null);
            //TextView id = (TextView)convertView.findViewById(R.id.id);
            TextView nombre = (TextView)convertView.findViewById(R.id.nombre);

            //id.setText(arrayList.get(position).getId());
            String nombreInstitucion = arrayList.get(position).getSeguimiento();
            nombre.setText(nombreInstitucion);
        }
        return convertView;
    }
}


