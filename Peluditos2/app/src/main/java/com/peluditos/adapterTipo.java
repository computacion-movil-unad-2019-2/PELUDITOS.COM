package com.peluditos;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.entidades.tipo;

import java.util.ArrayList;

public class adapterTipo extends BaseAdapter {

    private Context context;
    private ArrayList<tipo> arrayList;

    public adapterTipo(Context context, ArrayList<tipo> arrayList){
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
