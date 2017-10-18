package com.lista_de_compras.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.lista_de_compras.R;
import com.lista_de_compras.adapter.CategoriaDeProdutoAdapter;
import com.lista_de_compras.dao.CategoriaDeProdutoDAO;
import com.lista_de_compras.model.CategoriaDeProduto;

import java.util.List;

public class CategoriaDeProdutoActivity extends AppCompatActivity {
    ListView listViewCategorias;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categoria_de_produto);
        carregarViewComponents();
        configurarSupportActionBar();


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CategoriaDeProdutoActivity.this, CategoriaDeProdutoCadastroActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregarCategoriasNoListView();
    }

    public void carregarCategoriasNoListView(){
        CategoriaDeProdutoDAO categoriaDeProdutoDAO = new CategoriaDeProdutoDAO(this);
        List<CategoriaDeProduto> todasCategorias = categoriaDeProdutoDAO.todos();
        CategoriaDeProdutoAdapter categoriaDeProdutoAdapter = new CategoriaDeProdutoAdapter(this, todasCategorias);

        listViewCategorias.setAdapter(categoriaDeProdutoAdapter);

    }

    //fazer o ciclo de vida onResume - quando cria uma activity e volta pra ela
    //Obrigatório para onCreateContextMenu
        //monta menu de contexto
    //carregar produtos no listview
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
                break;

        }
        return super.onOptionsItemSelected(item);

    }

    private void configurarSupportActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }


    private void carregarViewComponents(){
        listViewCategorias = (ListView) findViewById(R.id.listView_categoria);
    }
}
