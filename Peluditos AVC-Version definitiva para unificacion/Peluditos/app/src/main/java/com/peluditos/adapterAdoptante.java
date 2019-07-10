package com.peluditos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.entidades.adoptante;

import java.util.ArrayList;

public class adapterAdoptante extends BaseAdapter {

    private Context context;
    private ArrayList<adoptante> arrayList;

    public adapterAdoptante(Context context, ArrayList<adoptante> arrayList){
        this.context = context;
        this.arrayList = arrayList;
    }
    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.iteminst,null);
            //TextView id = (TextView)convertView.findViewById(R.id.id);
            TextView nombre = (TextView)convertView.findViewById(R.id.nombre);

            String strNombre = arrayList.get(position).getNombres()+" "+arrayList.get(position).getApellidos();
            //id.setText(arrayList.get(position).getNit());
            nombre.setText(strNombre);
        }
        return convertView;
    }
}
