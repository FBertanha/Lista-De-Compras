package com.lista_de_compras.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.lista_de_compras.R;
import com.lista_de_compras.dao.ProdutoDao;
import com.lista_de_compras.model.Produto;

import java.util.List;

public class ProdutoActivity extends AppCompatActivity {

    private ListView listViewProdutos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produto);
        carregarViewComponents();
        configurarSupportActionBar();
        configurarListView();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProdutoActivity.this, ProdutoCadastroActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregarProdutosNoListView();
    }

    //Obrigatório para onCreateContextMenu
    private void configurarListView() {
        registerForContextMenu(listViewProdutos);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        //super.onCreateContextMenu(menu, v, menuInfo);
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;

        //Pega produto selecionado
        final Produto produto = (Produto) listViewProdutos.getItemAtPosition(info.position);

        //Monta menu de contexto
        MenuItem menuExcluir = menu.add(R.string.menu_activity_produto_excluir);

        menuExcluir.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                //Monta uma dialogo de confirmação, sim/não
                new AlertDialog.Builder(ProdutoActivity.this)
                        .setTitle(R.string.menu_activity_produto_excluir)
                        .setMessage(R.string.menu_activity_produto_excluir_mensagem)
                        //.setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                new ProdutoDao(ProdutoActivity.this).excluir(produto.getCodigo());
                                carregarProdutosNoListView();
                            }
                        })
                        .setNegativeButton(android.R.string.no, null)
                        .show();
                return true;
            }
        });

    }

    private void carregarProdutosNoListView() {
        ProdutoDao produtoDao = new ProdutoDao(this);
        List<Produto> todosProdutos = produtoDao.todos();
        ArrayAdapter<Produto> produtoArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, todosProdutos);

        listViewProdutos.setAdapter(produtoArrayAdapter);
    }

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

    private void carregarViewComponents() {
        listViewProdutos = (ListView) findViewById(R.id.listView_produtos);
    }

}
