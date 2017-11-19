package com.lista_de_compras.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.lista_de_compras.R;
import com.lista_de_compras.adapter.ProdutoAdapter;
import com.lista_de_compras.dao.CategoriaDeListaDAO;
import com.lista_de_compras.dao.ListaDAO;
import com.lista_de_compras.dao.ProdutoDAO;
import com.lista_de_compras.model.CategoriaDeLista;
import com.lista_de_compras.model.Lista;
import com.lista_de_compras.model.Produto;

import java.util.ArrayList;
import java.util.List;

public class ListaCadastroActivity extends AppCompatActivity {

    public static final int PRODUTO_SALVO = 1;
    private final String TAG = getClass().getSimpleName();

    private EditText editTextListaNome;
    private Spinner spinnerListaCategoria;
    private AutoCompleteTextView autoCompleteTextViewListaProduto;
    private ListView listViewListaProdutos;

    private Lista lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_cadastro);
        carregarViewComponents();
        configurarSupportActionBar();
        configurarListView();

        //Verifica se foi passado algum produto (edição)
        Intent intent = getIntent();
        lista = (Lista) intent.getSerializableExtra("lista");


        if (lista != null) {
            carregarListaNoFormulario();
        } else {
            lista = new Lista();
            //lista.setCategoria(new CategoriaDeLista());
            lista.setProdutos(new ArrayList<Produto>());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregarProdutosDaListaNoListView();
    }


    private void carregarListaNoFormulario() {
        editTextListaNome.setText(lista.getNome());
        spinnerListaCategoria.setSelection(((ArrayAdapter) spinnerListaCategoria.getAdapter()).getPosition(lista.getCategoria()));
    }

    private void pegarListaDoFormulario() {
        lista.setNome(editTextListaNome.getText().toString());
        lista.setCategoria((CategoriaDeLista) spinnerListaCategoria.getSelectedItem());
    }

    private boolean validarCampos() {
        if (lista.getNome().isEmpty()) {
            montarDialogAviso(getString(R.string.campos_em_branco), "Preencha a descricao da lista!");
            return false;
        }
        if (lista.getProdutos().isEmpty()) {
            montarDialogAviso(getString(R.string.campos_em_branco), "A lista não tem produtos!");
            return false;
        }
        return true;
    }

    private void salvar() {
        pegarListaDoFormulario();
        if (!validarCampos()) return;

        ListaDAO listaDAO = new ListaDAO(this);
        //Se o produto ja tiver um código (diferente de zero) ele está sendo editado, caso contrário é um produto novo
        if (lista.getCodigo() != null) {
            listaDAO.editar(lista);
        } else {
            listaDAO.adicionar(lista);
        }

        Toast.makeText(this, "Lista salva com sucesso!", Toast.LENGTH_SHORT).show();

        finish();
    }

    private void adicionarProdutoNaLista(Produto produto) {
        lista.getProdutos().add(produto);
        carregarProdutosDaListaNoListView();
    }

    private void removerProdutoDaLista(Produto produto) {
        lista.getProdutos().remove(produto);
        carregarProdutosDaListaNoListView();
    }

    private void carregarProdutosDaListaNoListView() {
        List<Produto> produtosDaLista = lista.getProdutos();

        ProdutoAdapter produtoAdapter = new ProdutoAdapter(this, produtosDaLista);
        listViewListaProdutos.setAdapter(produtoAdapter);
    }


    private void configurarSupportActionBar() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    private void carregarViewComponents() {
        editTextListaNome = (EditText) findViewById(R.id.editText_lista_nome);
        spinnerListaCategoria = (Spinner) findViewById(R.id.spinner_lista_categoria);
        autoCompleteTextViewListaProduto = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView_lista_produto);
        listViewListaProdutos = (ListView) findViewById(R.id.listView_lista_produtos);

        configurarSpinner();
        configurarAutoCompleteTextView();
    }

    private void montarDialogAviso(String titulo, String mensagem) {
        new AlertDialog.Builder(ListaCadastroActivity.this)
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

    private void configurarSpinner() {
        List<CategoriaDeLista> categoriasDeLista = new CategoriaDeListaDAO(this).todos();

        final ArrayAdapter<CategoriaDeLista> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categoriasDeLista);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerListaCategoria.setAdapter(adapter);

        spinnerListaCategoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                CategoriaDeLista categoriaDeListaSelecionada = (CategoriaDeLista) adapterView.getItemAtPosition(position);
                lista.setCategoria(categoriaDeListaSelecionada);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    private void configurarAutoCompleteTextView() {
        List<Produto> todosProdutos = new ProdutoDAO(this).todos();

        final ArrayAdapter<Produto> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, todosProdutos);
        autoCompleteTextViewListaProduto.setAdapter(adapter);

        autoCompleteTextViewListaProduto.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Produto produto = (Produto) adapter.getItem(position);
                adicionarProdutoNaLista(produto);

                autoCompleteTextViewListaProduto.setText("");
            }
        });

        autoCompleteTextViewListaProduto.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View arg0, int keyCode, KeyEvent event) {
                if (event.getAction() != KeyEvent.ACTION_DOWN) {
                    return false;
                }
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    Editable descricao = autoCompleteTextViewListaProduto.getText();

                    Produto novoProduto = new Produto(null, descricao.toString(), null, 0, false);

                    adicionarProdutoNaLista(novoProduto);

                    autoCompleteTextViewListaProduto.setText("");

                    return true;
                }
                return false;
            }
        });
    }

    private void configurarListView() {
        registerForContextMenu(listViewListaProdutos);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        //super.onCreateContextMenu(menu, v, menuInfo);
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;

        //Pega produto selecionado
        final Produto produto = (Produto) listViewListaProdutos.getItemAtPosition(info.position);

        //Monta menu de contexto
        MenuItem menuExcluir = menu.add(R.string.remover);
        menuExcluir.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                removerProdutoDaLista(produto);
                return true;
            }
        });

        //Monta menu de contexto
        MenuItem menuPreço = menu.add("Editar");
        menuPreço.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Intent intent = new Intent(ListaCadastroActivity.this, ProdutoCadastroActivity.class);
                intent.putExtra("produto", produto);
                startActivityForResult(intent, PRODUTO_SALVO);
                lista.getProdutos().remove(produto);
                return true;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == PRODUTO_SALVO) {
            if (resultCode == RESULT_OK) {
                Produto produto = (Produto) data.getSerializableExtra("produto");
                //Log.e(TAG, "onActivityResult: " + lista.getProdutos().contains(produto));
                adicionarProdutoNaLista(produto);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.activity_cadastro_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
                break;
            case R.id.salvar_cadastro_menu:
                salvar();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
