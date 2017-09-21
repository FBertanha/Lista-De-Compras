package com.lista_de_compras.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.lista_de_compras.R;
import com.lista_de_compras.dao.ProdutoDao;
import com.lista_de_compras.model.Produto;

import java.util.List;

public class ProdutoActivity extends AppCompatActivity {

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produto);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        carregarViewComponents();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProdutoActivity.this, ProdutoCadastroActivity.class);
                startActivity(intent);
            }
        });

        carregarProdutos();
    }

    private void carregarProdutos() {
        ProdutoDao produtoDao = new ProdutoDao(this);
        List<Produto> todosProdutos = produtoDao.todos();
        ArrayAdapter<Produto> produtoArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, todosProdutos);

        listView.setAdapter(produtoArrayAdapter);
    }

    private void carregarViewComponents() {
        listView = (ListView) findViewById(R.id.listView_produtos);
    }

}
