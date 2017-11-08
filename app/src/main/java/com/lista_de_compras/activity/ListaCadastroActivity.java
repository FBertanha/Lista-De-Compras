package com.lista_de_compras.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

import com.lista_de_compras.R;
import com.lista_de_compras.model.Lista;

public class ListaCadastroActivity extends AppCompatActivity {

    private Lista lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_cadastro);
        carregarViewComponents();
        configurarSupportActionBar();
        configurarListView();
        lista = new Lista();
    }

    private void configurarSupportActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    private void carregarViewComponents() {
        listViewProdutos = (ListView) findViewById(R.id.listView_produtos);
    }

    private void configurarListView() {
        registerForContextMenu(listViewProdutos);
    }
}
