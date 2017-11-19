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
import android.widget.ListView;

import com.lista_de_compras.R;
import com.lista_de_compras.adapter.ProdutoAdapter;
import com.lista_de_compras.dao.ProdutoDAO;
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

        MenuItem menuEditar = menu.add(R.string.editar);
        menuEditar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Intent intent = new Intent(ProdutoActivity.this, ProdutoCadastroActivity.class);
                intent.putExtra("produto", produto);
                startActivity(intent);
                return true;
            }
        });

        MenuItem menuExcluir = menu.add(R.string.excluir);
        menuExcluir.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                new AlertDialog.Builder(ProdutoActivity.this)
                        .setTitle(R.string.confirmar_exclusao)
                        .setMessage(R.string.menu_activity_produto_excluir_mensagem)
                        .setPositiveButton(R.string.confirmar, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                int result = new ProdutoDAO(ProdutoActivity.this).excluir(produto);
                                if (result == ProdutoDAO.ERR_PRODUTO_EM_USO) {
                                    montarDialogAviso(getResources().getString(R.string.err_produto_em_uso), getResources().getString(R.string.err_produto_em_uso_mensagem));
                                }
                                carregarProdutosNoListView();
                            }
                        })
                        .setNegativeButton(R.string.cancelar, null)
                        .show();
                return true;
            }
        });

    }

    private void montarDialogAviso(String titulo, String mensagem) {
        new AlertDialog.Builder(ProdutoActivity.this)
                .setTitle(titulo)
                .setMessage(mensagem)
                //.setIcon(android.R.drawable.ic_dialog_alert)
//                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        new CategoriaDeProdutoDAO(CategoriaDeProdutoActivity.this).excluir(categoriaDeProduto);
//                        carregarCategoriasNoListView();
//                    }
//                })
                .setNegativeButton(android.R.string.ok, null)
                .show();
    }

    private void carregarProdutosNoListView() {
        ProdutoDAO produtoDAO = new ProdutoDAO(this);
        List<Produto> todosProdutos = produtoDAO.todos();

        ProdutoAdapter produtoAdapter = new ProdutoAdapter(this);
        String ultimaCategoria = "";

        for (Produto produto : todosProdutos) {
            if (!produto.getCategoria().getNome().equals(ultimaCategoria)) {
                produtoAdapter.addSectionHeaderItem(produto);
                ultimaCategoria = produto.getCategoria().getNome();
            }
            produtoAdapter.addItem(produto);
        }

        listViewProdutos.setAdapter(produtoAdapter);
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
