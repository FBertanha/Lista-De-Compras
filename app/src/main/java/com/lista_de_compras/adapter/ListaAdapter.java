package com.lista_de_compras.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.TwoLineListItem;

import com.lista_de_compras.model.Lista;

import java.util.List;

/**
 * Created by berta on 11/7/2017.
 */

public class ListaAdapter extends BaseAdapter {
    private final Context context;
    private final List<Lista> listas;

    public ListaAdapter(Context context, List<Lista> listas) {
        this.context = context;
        this.listas = listas;
    }

    @Override
    public int getCount() {
        return listas.size();
    }

    @Override
    public Object getItem(int position) {
        return listas.get(position);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        Lista lista = listas.get(position);
        TwoLineListItem twoLineListItem;

        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            twoLineListItem = (TwoLineListItem) inflater.inflate(
                    android.R.layout.simple_list_item_2, null);
        } else {
            twoLineListItem = (TwoLineListItem) view;
        }

        TextView text1 = twoLineListItem.getText1();
        TextView text2 = twoLineListItem.getText2();

        text1.setText(lista.getNome());
        text2.setText(lista.getNome());
        // TODO total dos itens

        return twoLineListItem;
    }
}
