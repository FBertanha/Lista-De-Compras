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
import com.lista_de_compras.adapter.CategoriaDeListaAdapter;
import com.lista_de_compras.dao.CategoriaDeListaDAO;
import com.lista_de_compras.model.CategoriaDeLista;

import java.util.List;

public class CategoriaDeListaActivity extends AppCompatActivity {
    private final String TAG = getClass().getSimpleName();
    ListView listViewCategorias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categoria_de_lista);
        carregarViewComponents();
        configurarSupportActionBar();
        configurarListView();


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                novaCategoriaDeLista();
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
        final CategoriaDeLista categoriaDeLista = (CategoriaDeLista) listViewCategorias.getItemAtPosition(info.position);

        MenuItem menuEditar = menu.add(R.string.menu_activity_produto_editar);
        menuEditar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                editarCategoriaDeLista(categoriaDeLista);
                return true;
            }
        });

        //Monta menu de contexto
        MenuItem menuExcluir = menu.add(R.string.excluir);
        menuExcluir.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                //Monta uma dialogo de confirmação, sim/não

                new AlertDialog.Builder(CategoriaDeListaActivity.this)
                        .setTitle(R.string.confirmar_exclusao)
                        .setMessage(R.string.confirmar_exclusao_categoria_mensagem)
                        //.setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(R.string.confirmar, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                int result = new CategoriaDeListaDAO(CategoriaDeListaActivity.this).excluir(categoriaDeLista);
                                if (result == CategoriaDeListaDAO.ERR_CATEGORIA_EM_USO) {
                                    montarDialogAviso(getResources().getString(R.string.err_categoria_em_uso), getResources().getString(R.string.err_categoria_em_uso_mensagem));
                                } else if (result == CategoriaDeListaDAO.ERR_CATEGORIA_PADRAO) {
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
        new AlertDialog.Builder(CategoriaDeListaActivity.this)
                .setTitle(titulo)
                .setMessage(mensagem)
                //.setIcon(android.R.drawable.ic_dialog_alert)
//                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        new CategoriaDeListaDAO(CategoriaDeListaActivity.this).excluir(categoriaDeLista);
//                        carregarCategoriasNoListView();
//                    }
//                })
                .setNegativeButton(android.R.string.ok, null)
                .show();
    }

    private void novaCategoriaDeLista() {
        CategoriaDeLista categoriaDeLista = new CategoriaDeLista();
        montarDialogCategoria(categoriaDeLista);
    }

    private void editarCategoriaDeLista(CategoriaDeLista categoriaDeLista) {
        montarDialogCategoria(categoriaDeLista);
    }

    @SuppressLint("NewApi")
    private void montarDialogCategoria(CategoriaDeLista categoriaDeLista) {

        LayoutInflater layoutInflater = getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.dialog_categoria, null);
        EditText editTextCategoriaNome = (EditText) view.findViewById(R.id.dialog_categoria_nome);//.getText().toString())

        editTextCategoriaNome.setText(categoriaDeLista.getNome());

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Categoria de Lista");
        builder.setView(view);
        builder.setPositiveButton(R.string.salvar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                CategoriaDeListaDAO categoriaDeListaDAO = new CategoriaDeListaDAO(CategoriaDeListaActivity.this);
                categoriaDeLista.setNome(editTextCategoriaNome.getText().toString());

                if (categoriaDeLista.getCodigo() == null) {
                    categoriaDeListaDAO.adicionar(categoriaDeLista);
                } else {
                    categoriaDeListaDAO.editar(categoriaDeLista);
                }
                carregarCategoriasNoListView();

                categoriaDeListaDAO.close();
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

    public void carregarCategoriasNoListView() {
        CategoriaDeListaDAO categoriaDeListaDAO = new CategoriaDeListaDAO(this);
        List<CategoriaDeLista> todasCategorias = categoriaDeListaDAO.todos();
        CategoriaDeListaAdapter categoriaDeListaAdapter = new CategoriaDeListaAdapter(this, todasCategorias);

        listViewCategorias.setAdapter(categoriaDeListaAdapter);

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
        listViewCategorias = (ListView) findViewById(R.id.listView_categoria);
    }
}
