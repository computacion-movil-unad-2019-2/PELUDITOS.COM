package com.peluditos;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

public class adapterTipoInstitucion extends BaseAdapter {

    private Context context;
    private ArrayList<adapterTipoInstitucion> arrayList;

    public adapterTipoInstitucion(Context context, ArrayList<adapterTipoInstitucion> arrayList){
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
