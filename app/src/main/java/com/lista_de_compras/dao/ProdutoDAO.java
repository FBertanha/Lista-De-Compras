package com.lista_de_compras.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.lista_de_compras.model.CategoriaDeProduto;
import com.lista_de_compras.model.Produto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Felipe on 13/09/2017.
 */

public class ProdutoDAO extends DAO {
    private Context context;

    public ProdutoDAO(Context context) {
        super(context);
        this.context = context;
    }

    public void adicionar(Produto produto) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues dadosProduto = getDadosProduto(produto);

        db.insert("produtos", null, dadosProduto);
    }

    public void editar(Produto produto) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues dadosProduto = getDadosProduto(produto);
        String[] whereArgs = new String[]{String.valueOf(produto.getCodigo())};

        db.update("produtos", dadosProduto, "codigo = ?", whereArgs);
    }

    public void excluir(int codigo) {
        SQLiteDatabase db = getWritableDatabase();

        String[] whereArgs = new String[]{String.valueOf(codigo)};

        db.delete("produtos", "codigo = ?", whereArgs);
    }

    public List<Produto> todos() {
        List<Produto> produtos;

        String sql = "SELECT * FROM produtos";

        SQLiteDatabase db = getWritableDatabase();

        Cursor cursor = db.rawQuery(sql, null);

        produtos = getProdutosDoCursor(cursor);

        return produtos;
    }

    private List<Produto> getProdutosDoCursor(Cursor cursor) {
        List<Produto> produtos = new ArrayList<>();

        while (cursor.moveToNext()) {
            Produto produto = new Produto();

            produto.setCodigo(cursor.getInt(cursor.getColumnIndex("codigo")));
            produto.setDescricao(cursor.getString(cursor.getColumnIndex("descricao")));
            //TODO chave estrangeira CategoriaDeProduto
//            CategoriaDeProduto categoriaDeProduto = new CategoriaDeProduto();
//            categoriaDeProduto.setCodigo(1);
//            categoriaDeProduto.setNome("Batata");
            //CategoriaDeProdutoDAO categoriaDeProdutoDAO = new CategoriaDeProdutoDAO(context).pegarPorCodigo(produto.getCategoria());
            //produto.setCategoria(categoriaDeProduto);
            produto.setValor(cursor.getDouble(cursor.getColumnIndex("valor")));

            //Adiciona produto no ArrayList
            produtos.add(produto);
        }
        return produtos;
    }


    private ContentValues getDadosProduto(Produto produto) {
        ContentValues dados = new ContentValues();
        dados.put("codigo", produto.getCodigo());
        dados.put("descricao", produto.getDescricao());
        dados.put("categoria", produto.getCategoria().getCodigo());
        dados.put("valor", produto.getValor());
        return dados;
    }
}
