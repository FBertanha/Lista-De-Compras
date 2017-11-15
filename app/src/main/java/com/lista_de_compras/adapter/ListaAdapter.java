package com.lista_de_compras.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lista_de_compras.R;
import com.lista_de_compras.model.Lista;
import com.lista_de_compras.model.Produto;

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
        View listaListView;

        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            listaListView = inflater.inflate(R.layout.lista_list_item, parent, false);
        } else {
            listaListView = view;
        }

        TextView nome = listaListView.findViewById(R.id.textView_lista_nome);
        TextView categoria = listaListView.findViewById(R.id.textView_lista_categoria);
        TextView preco = listaListView.findViewById(R.id.textView_lista_total);

        nome.setText(lista.getNome());
        categoria.setText(lista.getCategoria().getNome());
        preco.setText(String.valueOf(lista.getProdutos().stream().mapToDouble(Produto::getValor).sum()));
        // TODO total dos itens

        return listaListView;
    }
}
