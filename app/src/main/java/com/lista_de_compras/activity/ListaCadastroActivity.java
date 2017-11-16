package com.lista_de_compras.activity;

import android.content.Intent;
import android.os.Bundle;
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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.lista_de_compras.R;
import com.lista_de_compras.adapter.ProdutoAdapter;
import com.lista_de_compras.dao.CategoriaDeListaDAO;
import com.lista_de_compras.dao.CategoriaDeProdutoDAO;
import com.lista_de_compras.dao.ListaDAO;
import com.lista_de_compras.dao.ProdutoDAO;
import com.lista_de_compras.model.CategoriaDeLista;
import com.lista_de_compras.model.Lista;
import com.lista_de_compras.model.Produto;

import java.util.ArrayList;
import java.util.List;

public class ListaCadastroActivity extends AppCompatActivity {

    private static final String TAG = "ListaCadastroActivity";

    private CheckBox checkBoxListaProdutoSelecionado;
    private EditText editTextListaNome;
    private Spinner spinnerListaCategoria;
    private AutoCompleteTextView autoCompleteTextViewListaProduto;
    private ListView listViewListaProdutos;


    private Lista lista;
    private Produto produto;
    private List<Produto> listaDeProdutos = new ArrayList<>();


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
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        carregarTodosProdutos();

    }

    private void carregarTodosProdutos() {
        //Traz todos produtos
        //listaDeProdutos = new ListaProdutoDAO(ListaCadastroActivity.this, lista).pegarProdutos();
    }

    private void carregarListaNoFormulario() {
        editTextListaNome.setText(lista.getNome());
        spinnerListaCategoria.setSelection(((ArrayAdapter) spinnerListaCategoria.getAdapter()).getPosition(lista.getCategoria().getNome()));
        lista.setProdutos(lista.getProdutos());

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

    private void salvar() {
        ListaDAO listaDAO = new ListaDAO(this);
        pegarListaDoFormulario();
        //Se o produto ja tiver um código (diferente de zero) ele está sendo editado, caso contrário é um produto novo
        if (lista.getCodigo() != null) {
            listaDAO.editar(lista);
        } else {
            listaDAO.adicionar(lista);
        }

        Toast.makeText(this, "Lista salva com sucesso!", Toast.LENGTH_SHORT).show();

        finish();

    }

    private void pegarListaDoFormulario() {
        lista.setNome(editTextListaNome.getText().toString());
        lista.setCategoria((CategoriaDeLista) spinnerListaCategoria.getSelectedItem());
        lista.setProdutos(listaDeProdutos);

    }

    private void adicionarProdutoNaLista() {
        listaDeProdutos.add(produto);
        //produto = new Produto();
        autoCompleteTextViewListaProduto.setText("");
        carregarProdutosNoListView();
    }

    private void carregarProdutosNoListView() {


        ProdutoAdapter produtoAdapter = new ProdutoAdapter(this, listaDeProdutos);
        //ArrayAdapter<Produto> produtoAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_2, todosProdutos);


        listViewListaProdutos.setAdapter(produtoAdapter);

    }


    private void configurarSupportActionBar() {
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

//    private void carregarListasNoListView() {
//        ProdutoDAO produtoDAO = new ProdutoDAO(this);
//        List<Produto> todosProdutos = produtoDAO.todos();
//
//        ProdutoAdapter produtoAdapter = new ProdutoAdapter(this, todosProdutos);
//        //ArrayAdapter<Produto> produtoAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_2, todosProdutos);
//
//
//        listViewListaProdutos.setAdapter(produtoAdapter);
//    }

    private void carregarViewComponents() {
        checkBoxListaProdutoSelecionado = (CheckBox) findViewById(R.id.checkbox_lista_produto_selecionado);
        editTextListaNome = (EditText) findViewById(R.id.editText_lista_nome);
        spinnerListaCategoria = (Spinner) findViewById(R.id.spinner_lista_categoria);
        autoCompleteTextViewListaProduto = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView_lista_produto);
        listViewListaProdutos = (ListView) findViewById(R.id.listView_lista_produtos);

        configurarSpinner();
        configurarAutoCompleteTextView();
    }

//    private void configurarCheckbox() {
//        listViewListaProdutos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                CheckBox chk = (CheckBox) view.findViewById(R.id.checkbox_lista_produto_selecionado);
//                chk.setChecked(!chk.isChecked());
//
//                Produto produto = (Produto) chk.getTag();
//                produto.setSelecionado(chk.isChecked());
//
//                ListaProdutoDAO listaProdutoDAO = new ListaProdutoDAO(ListaCadastroActivity.this, lista);
//                listaProdutoDAO.editar(produto);
//            }
//        });
//    }

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
                produto = (Produto) adapter.getItem(position);
                adicionarProdutoNaLista();
            }
        });

        autoCompleteTextViewListaProduto.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View arg0, int keyCode, KeyEvent event) {
                //This is the filter
                if (event.getAction() != KeyEvent.ACTION_DOWN) {
                    return false;
                }
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    boolean flagGravar = true;
                    ProdutoDAO produtoDAO = new ProdutoDAO(ListaCadastroActivity.this);
                    Editable descricao = autoCompleteTextViewListaProduto.getText();

                    for (Produto p :
                            listaDeProdutos) {
                        if (p.getDescricao().equals(descricao)) {
                            flagGravar = false;
                            break;
                        }
                    }
                    if (flagGravar) {
                        produto = new Produto(null, descricao.toString(), new CategoriaDeProdutoDAO(ListaCadastroActivity.this).pegarPorCodigo(1), 0, false);
                        produto.setCodigo(Integer.parseInt(String.valueOf(produtoDAO.adicionar(produto))));

                    }
                    adicionarProdutoNaLista();
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
        MenuItem menuExcluir = menu.add(R.string.excluir);
        menuExcluir.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                //Monta uma dialogo de confirmação, sim/não
                listaDeProdutos.remove(produto);
                carregarProdutosNoListView();
                return true;
            }
        });

    }
}
