package com.lista_de_compras.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.ContentObservable;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.lista_de_compras.model.CategoriaDeProduto;

import java.util.ArrayList;
import java.util.List;

import static android.R.id.list;

/**
 * Created by evely on 27/09/2017.
 */

public class CategoriaDeProdutoDAO extends SQLiteOpenHelper {

    public CategoriaDeProdutoDAO(Context context){
        super(context, "lista_de_compras", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE categoriaDeProduto(" +
                "codigo INT PRIMARY KEY AUTOINCREMENT," +
                "nome VARCHAR (255) NOT NULL)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    //métodos CRUD

    public void adicionar(CategoriaDeProduto categoriaDeProduto){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues dadosCategoriaDeProduto = getDadosCategoriaDeProdutos(categoriaDeProduto);
        db.insert("categoriaDeProduto", null, dadosCategoriaDeProduto);
    }

    public void editar(CategoriaDeProduto categoriaDeProduto){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues dadosCategoriaDeProduto = getDadosCategoriaDeProdutos(categoriaDeProduto);
        String[] whereArgs = new String[]{String.valueOf(categoriaDeProduto.getCodigo())};

        db.update("categoriaDeProduto", dadosCategoriaDeProduto, "codigo = ?", whereArgs);
    }

    public void excluir(CategoriaDeProduto categoriaDeProduto){
        SQLiteDatabase db = getWritableDatabase();
        String[] whereArgs = new String[]{String.valueOf(categoriaDeProduto.getCodigo())};

        db.delete("categoriaDeProduto","codigo = ?", whereArgs);
    }

    public List<CategoriaDeProduto> todos(){
        List<CategoriaDeProduto> categorias;
        String sql = "SELECT * FROM categoriaDeProduto";
        SQLiteDatabase db = getWritableDatabase();

        Cursor cursor = db.rawQuery(sql, null);

        categorias = getCategoriaDeProdutosDoCursor(cursor);

        return categorias;
    }

    private List<CategoriaDeProduto> getCategoriaDeProdutosDoCursor(Cursor cursor) {
        List<CategoriaDeProduto> categorias = new ArrayList<>();
        while(cursor.moveToNext()){
            CategoriaDeProduto categoriaDeProduto = new CategoriaDeProduto();
            categoriaDeProduto.setCodigo(cursor.getInt(cursor.getColumnIndex("codigo")));
            categoriaDeProduto.setNome(cursor.getString(cursor.getColumnIndex("nome")));
            categorias.add(categoriaDeProduto);
        }

        return categorias;
    }

    private ContentValues getDadosCategoriaDeProdutos(CategoriaDeProduto categoriaDeProduto) {
        ContentValues dados = new ContentValues();

        dados.put("codigo", categoriaDeProduto.getCodigo());
        dados.put("nome", categoriaDeProduto.getNome());

        return dados;

    }
}
