package com.lista_de_compras.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.lista_de_compras.model.Produto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Felipe on 13/09/2017.
 */

public class ProdutoDAO extends DAO {
    public static final int ERR_PRODUTO_EM_USO = 1;
    private Context context;

    public ProdutoDAO(Context context) {
        super(context);
        this.context = context;
    }

    public Long adicionar(Produto produto) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues dadosProduto = getDadosProduto(produto);

        return db.insert("produtos", null, dadosProduto);
    }

    public void editar(Produto produto) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues dadosProduto = getDadosProduto(produto);
        String[] whereArgs = new String[]{String.valueOf(produto.getCodigo())};

        db.update("produtos", dadosProduto, "codigo = ?", whereArgs);
    }

    public int excluir(Produto produto) {

        if (verificarProdutoEmUso(produto)) {
            return ERR_PRODUTO_EM_USO;
        }

        SQLiteDatabase db = getWritableDatabase();

        String[] whereArgs = new String[]{String.valueOf(produto.getCodigo())};

        db.delete("produtos", "codigo = ?", whereArgs);

        return 0;
    }

    private boolean verificarProdutoEmUso(Produto produto) {
        boolean ret;
        String sql = "SELECT count(*) FROM listaProdutos WHERE produto = ?";
        SQLiteDatabase db = getWritableDatabase();

        String[] selectionArgs = new String[]{String.valueOf(produto.getCodigo())};

        Cursor cursor = db.rawQuery(sql, selectionArgs);
        cursor.moveToNext();
        ret = cursor.getInt(0) > 0;
        cursor.close();
        return ret;
    }

    public List<Produto> todos() {
        List<Produto> produtos;

        String sql = "SELECT * FROM produtos order by categoria, descricao";

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
            produto.setCategoria(new CategoriaDeProdutoDAO(context).pegarPorCodigo(cursor.getInt(cursor.getColumnIndex("categoria"))));
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
        if (produto.getCategoria() != null)
            dados.put("categoria", produto.getCategoria().getCodigo());
        else
            dados.put("categoria", 1);
        dados.put("valor", produto.getValor());
        return dados;
    }


//    public Produto pegarPorDescricao(String descricao) {
//        //TODO
//        String[] whereArgs = new String[]{descricao};
//        String sql = "SELECT count(*) FROM produtos where descricao = ?";
//
//        SQLiteDatabase db = getWritableDatabase();
//
//        Cursor cursor = db.rawQuery(sql, null);
//
//        produtos = getProdutosDoCursor(cursor);
//
//        return produtos;
//    }
}
