package com.lista_de_compras.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.lista_de_compras.R;
import com.lista_de_compras.dao.CategoriaDeProdutoDAO;
import com.lista_de_compras.model.CategoriaDeProduto;

public class CategoriaDeProdutoCadastroActivity extends AppCompatActivity {
    private EditText editText_nome;
    private CategoriaDeProduto categoriaDeProduto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categoria_de_produto_cadastro);
        configurarSupportActionBar();
        carregarViewComponents();

        categoriaDeProduto = new CategoriaDeProduto();

    }

    private void carregarViewComponents() {
        editText_nome = (EditText) findViewById(R.id.editText_lista_nome);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.activity_cadastro_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void configurarSupportActionBar() {

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
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
        CategoriaDeProdutoDAO categoriaDeProdutoDAO = new CategoriaDeProdutoDAO(this);
        pegarCategoriaDeProdutoDoFormulario();
        categoriaDeProdutoDAO.adicionar(categoriaDeProduto);

        Toast.makeText(this, "Categoria salva com sucesso!", Toast.LENGTH_SHORT).show();
        finish();

    }

    private void pegarCategoriaDeProdutoDoFormulario() {
        categoriaDeProduto.setNome(editText_nome.getText().toString());
    }


}
