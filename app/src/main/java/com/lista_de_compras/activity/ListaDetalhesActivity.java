package com.lista_de_compras.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;

import com.lista_de_compras.R;
import com.lista_de_compras.adapter.ListaProdutoAdapter;
import com.lista_de_compras.dao.ListaProdutoDAO;
import com.lista_de_compras.model.CategoriaDeProduto;
import com.lista_de_compras.model.Lista;
import com.lista_de_compras.model.Produto;

import java.util.Comparator;
import java.util.List;

public class ListaDetalhesActivity extends AppCompatActivity {
    private CheckBox checkBoxListaProdutoSelecionado;

    private Lista lista;
    private ListView listViewListaProdutosDetalhes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_detalhes);
        configurarSupportActionBar();
        carregarViewComponents();
        configurarListView();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        Intent intent = getIntent();
        lista = (Lista) intent.getSerializableExtra("lista");

        if (lista != null) {
            this.setTitle(lista.getNome() + " - " + lista.getCategoria().getNome());
            carregarProdutosNoListView();
        } else {
            lista = new Lista();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregarProdutosNoListView();
    }

    //Obrigatório para onCreateContextMenu
    private void configurarListView() {
        registerForContextMenu(listViewListaProdutosDetalhes);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
//        //super.onCreateContextMenu(menu, v, menuInfo);
//        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
//
//        //Pega produto selecionado
//        final Produto produto = (Produto) listViewListaProdutosDetalhes.getItemAtPosition(info.position);
//
//        MenuItem menuEditar = menu.add(R.string.menu_activity_produto_editar);
//        menuEditar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem menuItem) {
//                Intent intent = new Intent(ListaDetalhesActivity.this, ProdutoCadastroActivity.class);
//                intent.putExtra("produto", produto);
//                startActivity(intent);
//                return true;
//            }
//        });
//
//        //Monta menu de contexto
//        MenuItem menuExcluir = menu.add(R.string.excluir);
//        menuExcluir.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem menuItem) {
//                //Monta uma dialogo de confirmação, sim/não
//
//                new AlertDialog.Builder(ListaDetalhesActivity.this)
//                        .setTitle(R.string.excluir)
//                        .setMessage(R.string.menu_activity_produto_excluir_mensagem)
//                        //.setIcon(android.R.drawable.ic_dialog_alert)
//                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                                new ProdutoDAO(ListaDetalhesActivity.this).excluir(produto);
//                                carregarProdutosNoListView();
//                            }
//                        })
//                        .setNegativeButton(android.R.string.no, null)
//                        .show();
//                return true;
//            }
//        });

    }

    private void carregarProdutosNoListView() {
        ListaProdutoDAO listaProdutoDAO = new ListaProdutoDAO(this, lista);
        List<Produto> todosProdutos = listaProdutoDAO.pegarProdutos();

        ListaProdutoAdapter produtoAdapter = new ListaProdutoAdapter(this);

        todosProdutos.sort(new Comparator<Produto>() {
            @Override
            public int compare(Produto o1, Produto o2) {
                return o1.isSelecionado().compareTo(o2.isSelecionado());
            }
        });

        boolean flag = false;
        for (Produto produto : todosProdutos) {
            if (produto.isSelecionado() && !flag) {
                produtoAdapter.addSectionHeaderItem(new Produto(null, null, new CategoriaDeProduto(null, "Selecionados"), 0, false));
                flag = true;
            }
            produtoAdapter.addItem(produto);
        }

        listViewListaProdutosDetalhes.setAdapter(produtoAdapter);
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

    private void configurarCheckbox() {
        listViewListaProdutosDetalhes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CheckBox chk = (CheckBox) view.findViewById(R.id.checkbox_lista_produto_selecionado);
                if (chk == null) return;
                chk.setChecked(!chk.isChecked());

                Produto produto = (Produto) chk.getTag();
                produto.setSelecionado(chk.isChecked());

                ListaProdutoDAO listaProdutoDAO = new ListaProdutoDAO(ListaDetalhesActivity.this, lista);
                listaProdutoDAO.editar(produto);
                carregarProdutosNoListView();
            }
        });
    }

    private void carregarViewComponents() {
        checkBoxListaProdutoSelecionado = (CheckBox) findViewById(R.id.checkbox_lista_produto_selecionado);
        listViewListaProdutosDetalhes = (ListView) findViewById(R.id.listView_lista_produtos_detalhes);

        configurarCheckbox();
    }

}
