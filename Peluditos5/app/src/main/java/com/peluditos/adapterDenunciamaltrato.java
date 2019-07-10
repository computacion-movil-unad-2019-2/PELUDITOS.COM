package com.peluditos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.entidades.CDenunciamaltrato;
import com.entidades.CEncontraranimal;

import java.util.ArrayList;

public class adapterDenunciamaltrato extends BaseAdapter {
    private Context context;
    private ArrayList<CDenunciamaltrato> arrayList;

    public adapterDenunciamaltrato(Context context, ArrayList<CDenunciamaltrato> arrayList){
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
            TextView id = (TextView)convertView.findViewById(R.id.id);
             TextView nombre = (TextView)convertView.findViewById(R.id.nombre);

            //id.setText(arrayList.get(position).getNit());
            id.setText(arrayList.get(position).getId());
            nombre.setText(arrayList.get(position).getDescripcion());
        }
        return convertView;
    }
}
