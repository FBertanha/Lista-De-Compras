package com.lista_de_compras.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.lista_de_compras.R;
import com.lista_de_compras.dao.CategoriaDeProdutoDAO;
import com.lista_de_compras.dao.ProdutoDAO;
import com.lista_de_compras.model.CategoriaDeProduto;
import com.lista_de_compras.model.Produto;

import java.util.List;

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
        spinnerCategoria.setSelection(((ArrayAdapter)spinnerCategoria.getAdapter()).getPosition(produto.getCategoria().getNome()));
    }

    private void pegarProdutoDoFormulario() {
        produto.setDescricao(editTextDescricao.getText().toString());
        produto.setValor(Double.parseDouble(editTextValor.getText().toString()));
        produto.setCategoria((CategoriaDeProduto) spinnerCategoria.getSelectedItem());
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
        List<CategoriaDeProduto> categoriasDeProdutos = new CategoriaDeProdutoDAO(this).todos();
        ArrayAdapter<CategoriaDeProduto> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categoriasDeProdutos);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategoria.setAdapter(adapter);

        spinnerCategoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                CategoriaDeProduto categoriaSelecionada = (CategoriaDeProduto) adapterView.getItemAtPosition(position);
                produto.setCategoria(categoriaSelecionada);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
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
