package com.lista_de_compras.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.lista_de_compras.R;
import com.lista_de_compras.dao.ProdutoDAO;
import com.lista_de_compras.model.CategoriaDeProduto;
import com.lista_de_compras.model.Produto;

public class ProdutoCadastroActivity extends AppCompatActivity {

    private EditText editTextDescricao;
    private EditText editTextValor;
    private Spinner spinnerCategoria;
    private Produto produto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produto_cadastro);
        carregarViewComponents();
        configurarSupportActionBar();

        //Verifica se foi passado algum produto (edição)
        Intent intent = getIntent();
        produto = (Produto) intent.getSerializableExtra("produto");

        if (produto != null) {
            carregarProdutoNoFormulario();
        } else {
            produto = new Produto();
        }
    }

    private void carregarProdutoNoFormulario() {
        editTextDescricao.setText(produto.getDescricao());
        editTextValor.setText(String.valueOf(produto.getValor()));
        // TODO
        spinnerCategoria.setSelection(1);
    }

    private void pegarProdutoDoFormulario() {
        produto.setDescricao(editTextDescricao.getText().toString());
        produto.setValor(Double.parseDouble(editTextValor.getText().toString()));
        // TODO
        //produto.setCategoria((CategoriaDeProduto) spinnerCategoria.getSelectedItem());
        CategoriaDeProduto categoriaDeProduto = new CategoriaDeProduto();
        categoriaDeProduto.setCodigo(1);
        categoriaDeProduto.setNome("Batata");
        produto.setCategoria(categoriaDeProduto);
    }

    private void salvar() {
        ProdutoDAO produtoDAO = new ProdutoDAO(this);
        pegarProdutoDoFormulario();
        //Se o produto ja tiver um código (diferente de zero) ele está sendo editado, caso contrário é um produto novo
        if(produto.getCodigo() != null) {
            produtoDAO.editar(produto);
        } else {
            produtoDAO.adicionar(produto);
        }

        Toast.makeText(this, "Produto salvo com sucesso!", Toast.LENGTH_SHORT).show();

        finish();
    }

    private void carregarViewComponents() {
        editTextDescricao = (EditText) findViewById(R.id.editText_descricao);
        editTextValor = (EditText) findViewById(R.id.editText_valor);
        spinnerCategoria = (Spinner) findViewById(R.id.spinner_categoria);
        //TODO
        String categorias[] = {"Bebidas", "Frios", "Potato"};
        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categorias);
        stringArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategoria.setAdapter(stringArrayAdapter);
    }

    private void configurarSupportActionBar() {

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.activity_produto_cadastro_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
                break;
            case R.id.produto_cadastro_menu:
                salvar();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


}
