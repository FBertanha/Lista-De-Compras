package com.lista_de_compras.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lista_de_compras.R;
import com.lista_de_compras.model.Produto;

import org.w3c.dom.Text;

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

        //Infla o layout
        LayoutInflater inflater = LayoutInflater.from(context);

        if (view == null) {
            view = inflater.inflate(R.layout.produto_list_item, parent, false);
        }

        //ImageView imageViewFoto = (ImageView) view.findViewById(R.id.imageView_foto);
        TextView textViewDescricao = (TextView) view.findViewById(R.id.textView_descricao);
        TextView textViewCategoria = (TextView) view.findViewById(R.id.textView_categoria);
        TextView textViewValor = (TextView) view.findViewById(R.id.textView_valor);

        textViewDescricao.setText(produto.getDescricao());
        //textViewCategoria.setText(produto.getCategoria().getNome());
        textViewValor.setText(String.valueOf(produto.getValor()));

        return view;
    }
}
