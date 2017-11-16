package com.lista_de_compras.activity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.lista_de_compras.R;
import com.lista_de_compras.adapter.CategoriaDeProdutoAdapter;
import com.lista_de_compras.dao.CategoriaDeProdutoDAO;
import com.lista_de_compras.model.CategoriaDeProduto;

import java.util.List;

public class CategoriaDeProdutoActivity extends AppCompatActivity {
    private final String TAG = getClass().getSimpleName();
    ListView listViewCategorias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categoria_de_produto);
        carregarViewComponents();
        configurarSupportActionBar();
        configurarListView();


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                novaCategoriaDeProduto();
            }
        });
    }

    //Obrigatório para onCreateContextMenu
    private void configurarListView() {
        registerForContextMenu(listViewCategorias);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        //super.onCreateContextMenu(menu, v, menuInfo);
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;

        //Pega categoria selecionada
        final CategoriaDeProduto categoriaDeProduto = (CategoriaDeProduto) listViewCategorias.getItemAtPosition(info.position);

        MenuItem menuEditar = menu.add(R.string.menu_activity_produto_editar);
        menuEditar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                editarCategoriaDeProduto(categoriaDeProduto);
                return true;
            }
        });

        //Monta menu de contexto
        MenuItem menuExcluir = menu.add(R.string.excluir);
        menuExcluir.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                //Monta uma dialogo de confirmação, sim/não

                new AlertDialog.Builder(CategoriaDeProdutoActivity.this)
                        .setTitle(R.string.confirmar_exclusao)
                        .setMessage(R.string.confirmar_exclusao_categoria_mensagem)
                        //.setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(R.string.confirmar, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                int result = new CategoriaDeProdutoDAO(CategoriaDeProdutoActivity.this).excluir(categoriaDeProduto);
                                if (result == CategoriaDeProdutoDAO.ERR_CATEGORIA_EM_USO) {
                                    montarDialogAviso(getResources().getString(R.string.err_categoria_em_uso), getResources().getString(R.string.err_categoria_em_uso_mensagem));
                                } else if (result == CategoriaDeProdutoDAO.ERR_CATEGORIA_PADRAO) {
                                    montarDialogAviso(getResources().getString(R.string.err_categoria_padrao), getResources().getString(R.string.err_categoria_padrao_mensagem));
                                }
                                carregarCategoriasNoListView();
                            }
                        })
                        .setNegativeButton(R.string.cancelar, null)
                        .show();
                return true;
            }
        });

    }

    private void montarDialogAviso(String titulo, String mensagem) {
        new AlertDialog.Builder(CategoriaDeProdutoActivity.this)
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

    private void novaCategoriaDeProduto() {
        CategoriaDeProduto categoriaDeProduto = new CategoriaDeProduto();
        montarDialogCategoria(categoriaDeProduto);
    }

    private void editarCategoriaDeProduto(CategoriaDeProduto categoriaDeProduto) {
        montarDialogCategoria(categoriaDeProduto);
    }

    @SuppressLint("NewApi")
    private void montarDialogCategoria(CategoriaDeProduto categoriaDeProduto) {

        LayoutInflater layoutInflater = getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.dialog_categoria, null);
        EditText editTextCategoriaNome = (EditText) view.findViewById(R.id.dialog_categoria_nome);//.getText().toString())

        editTextCategoriaNome.setText(categoriaDeProduto.getNome());

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Categoria de Produto");
        builder.setView(view);
        builder.setPositiveButton(R.string.salvar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                CategoriaDeProdutoDAO categoriaDeProdutoDAO = new CategoriaDeProdutoDAO(CategoriaDeProdutoActivity.this);
                categoriaDeProduto.setNome(editTextCategoriaNome.getText().toString());

                if (categoriaDeProduto.getCodigo() == null) {
                    categoriaDeProdutoDAO.adicionar(categoriaDeProduto);
                } else {
                    categoriaDeProdutoDAO.editar(categoriaDeProduto);
                }
                carregarCategoriasNoListView();

                categoriaDeProdutoDAO.close();
            }
        });
        builder.setNegativeButton(R.string.cancelar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.create();

        builder.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregarCategoriasNoListView();
    }

    public void carregarCategoriasNoListView(){
        CategoriaDeProdutoDAO categoriaDeProdutoDAO = new CategoriaDeProdutoDAO(this);
        List<CategoriaDeProduto> todasCategorias = categoriaDeProdutoDAO.todos();
        CategoriaDeProdutoAdapter categoriaAdapter = new CategoriaDeProdutoAdapter(this, todasCategorias);

        listViewCategorias.setAdapter(categoriaAdapter);

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

    private void carregarViewComponents(){
        listViewCategorias = (ListView) findViewById(R.id.listView_categoria);
    }
}
