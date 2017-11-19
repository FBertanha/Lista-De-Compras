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

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

/**
 * Created by Felipe on 25/09/2017.
 */

public class ListaProdutoAdapter extends BaseAdapter {
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_HEADER = 1;
    private final String TAG = getClass().getSimpleName();
    private Context context;
    private List<Produto> produtos;
    private TreeSet<Integer> sectionHeader = new TreeSet<Integer>();

    public ListaProdutoAdapter(Context context, List<Produto> produtos) {
        this.context = context;
        this.produtos = produtos;
    }

    public ListaProdutoAdapter(Context context) {
        this.context = context;
        this.produtos = new ArrayList<>();
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
        int rowType = getItemViewType(position);
        Produto produto = produtos.get(position);
        View produtoListView = null;

        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(context);

            switch (rowType) {
                case TYPE_ITEM:
                    produtoListView = inflater.inflate(R.layout.lista_produto_list_item, parent, false);
                    break;

                case TYPE_HEADER:
                    produtoListView = inflater.inflate(R.layout.produto_list_item_header, parent, false);
                    break;
            }
        } else {
            produtoListView = view;
        }

        if (rowType == TYPE_ITEM) {
            CheckBox selecionado = produtoListView.findViewById(R.id.checkbox_lista_produto_selecionado);
            TextView preco = produtoListView.findViewById(R.id.textView_lista_produto_valor);

            selecionado.setChecked(produto.isSelecionado() == null || !produto.isSelecionado() ? false : true);
            selecionado.setText(produto.getDescricao());
            selecionado.setTag(produto);

            preco.setText(String.valueOf(produto.getValor()));
        } else if (rowType == TYPE_HEADER) {
            TextView separator = produtoListView.findViewById(R.id.textSeparator);
            separator.setText(produto.getCategoria().getNome());
        }

        return produtoListView;
    }

    public void addItem(final Produto produto) {
        produtos.add(produto);
        notifyDataSetChanged();
    }

    public void addSectionHeaderItem(final Produto produto) {
        produtos.add(produto);
        sectionHeader.add(produtos.size() - 1);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return sectionHeader.contains(position) ? TYPE_HEADER : TYPE_ITEM;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }
}
