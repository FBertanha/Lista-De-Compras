package com.lista_de_compras.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.lista_de_compras.R;
import com.lista_de_compras.model.Produto;

import java.util.List;

/**
 * Created by Felipe on 25/09/2017.
 */

public class ListaProdutoAdapter extends BaseAdapter {
    private Context context;
    private List<Produto> produtos;

    public ListaProdutoAdapter(Context context, List<Produto> produtos) {
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
        View produtoListView;

        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            produtoListView = inflater.inflate(R.layout.lista_produto_list_item, parent, false);
        } else {
            produtoListView = view;
        }

        CheckBox selecionado = produtoListView.findViewById(R.id.checkbox_lista_produto_selecionado);
        //TextView nome = produtoListView.findViewById(R.id.textView_lista_produto_descricao);
        TextView preco = produtoListView.findViewById(R.id.textView_lista_produto_valor);

        selecionado.setChecked(produto.isSelecionado());
        selecionado.setText(produto.getDescricao());
        selecionado.setTag(produto);
        //nome.setText(produto.getDescricao());
        preco.setText(String.valueOf(produto.getValor()));

        return produtoListView;
    }
}
