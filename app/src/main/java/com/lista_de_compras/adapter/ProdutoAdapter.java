package com.lista_de_compras.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.TwoLineListItem;

import com.lista_de_compras.model.Produto;

import java.util.List;

/**
 * Created by Felipe on 25/09/2017.
 */

public class ProdutoAdapter extends BaseAdapter {
    private Context context;
    private List<Produto> produtos;

    public ProdutoAdapter(Context context, List<Produto> produtos) {
        this.context = context;
        this.produtos = produtos;
    }

    @Override
    public int getCount() {
        return produtos.size();
    }

    @Override
    public Object getItem(int position) {
        return produtos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        Produto produto = produtos.get(position);
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

        text1.setText(produto.getDescricao());
        text2.setText(String.valueOf(produto.getValor()));

        return twoLineListItem;

    }
}
