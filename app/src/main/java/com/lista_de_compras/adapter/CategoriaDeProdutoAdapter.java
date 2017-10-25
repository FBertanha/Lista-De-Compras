package com.lista_de_compras.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lista_de_compras.R;
import com.lista_de_compras.model.CategoriaDeProduto;

import java.util.List;

/**
 * Created by evely on 18/10/2017.
 */

public class CategoriaDeProdutoAdapter extends BaseAdapter{

    private final Context context;
    private final List<CategoriaDeProduto> categorias;

    public CategoriaDeProdutoAdapter(Context context, List<CategoriaDeProduto> categorias) {
        this.context = context;
        this.categorias = categorias;
    }

    @Override
    public int getCount() {
        return categorias.size();
    }

    @Override
    public Object getItem(int position) {
        return categorias.get(position);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        CategoriaDeProduto categoriaDeProduto = categorias.get(position);
        LayoutInflater inflater = LayoutInflater.from(context);

        if(view == null){
            view = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
        }

        TextView textViewNome = (TextView) view.findViewById(android.R.id.text1);
        textViewNome.setText(categoriaDeProduto.getNome());

        return view;
    }
}
