package com.lista_de_compras.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.lista_de_compras.model.CategoriaDeLista;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DIogo on 20/09/2017.
 */

public class CategoriaDeListaDAO extends SQLiteOpenHelper {

    public CategoriaDeListaDAO(Context context){super(context, "lista_de_compras", null, 1);}

    public void onCreate(SQLiteDatabase db){


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void adicionar(CategoriaDeLista categoria){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues dadosCategoriaDeLista = getDadosCategoriaDeLista(categoria);

        db.insert("CategoriaDeLista",null,dadosCategoriaDeLista);

    }

    public void editar(CategoriaDeLista categoria){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues dadosCategoriaDeLista = getDadosCategoriaDeLista(categoria);
        String[] whereArgs = new String[]{String.valueOf(categoria.getCodigo())};

        db.update("CategoriaDeLista", dadosCategoriaDeLista,"codigo = ?", whereArgs);

    }


    public void excluir(int codigo) {
        SQLiteDatabase db = getWritableDatabase();

        String[] whereArgs = new String[]{String.valueOf(codigo)};

        db.delete("CategoriaDeLista", "id = ?", whereArgs);
    }

    public List<CategoriaDeLista> todos(){
        List<CategoriaDeLista> categoriaDeListas;

        //TODO
        categoriaDeListas = new ArrayList<>();
        categoriaDeListas.add(new CategoriaDeLista(1, "Festa"));
        categoriaDeListas.add(new CategoriaDeLista(1, "Teste"));
        categoriaDeListas.add(new CategoriaDeLista(1, "Teste2"));
//        String sql = "SELECT * FROM CategoriaDeLista";
//        SQLiteDatabase db = getWritableDatabase();
//
//        Cursor cursor = db.rawQuery(sql,null);
//
//        categoriaDeListas = getCategoriaDoCursor(cursor);
        return categoriaDeListas;
    }


    public List<CategoriaDeLista> getCategoriaDoCursor(Cursor cursor){
        List<CategoriaDeLista> categorias = new ArrayList<>();

        while (cursor.moveToNext()){
            CategoriaDeLista categoria = new CategoriaDeLista();
            categoria.setCodigo(cursor.getInt(cursor.getColumnIndex("codigo")));
            categoria.setNome(cursor.getString(cursor.getColumnIndex("nome")));
            categorias.add(categoria);
        }
        return categorias;
    }


    private ContentValues getDadosCategoriaDeLista(CategoriaDeLista categoria){
        ContentValues dados = new ContentValues();

        dados.put("codigo", categoria.getCodigo());
        dados.put("nome",categoria.getNome());

        return dados;
    }


    public CategoriaDeLista pegarPorCodigo(int codigo) {
        String sql = "SELECT * FROM categoriaDeLista WHERE codigo = ?";
        SQLiteDatabase db = getWritableDatabase();

        String[] selectionArgs = new String[]{String.valueOf(codigo)};

        Cursor cursor = db.rawQuery(sql, selectionArgs);
        if (cursor.moveToNext()) {
            CategoriaDeLista categoriaDeProduto = new CategoriaDeLista();
            categoriaDeProduto.setCodigo(cursor.getInt(cursor.getColumnIndex("codigo")));
            categoriaDeProduto.setNome(cursor.getString(cursor.getColumnIndex("nome")));
            return categoriaDeProduto;
        }
        cursor.close();
        return null;
    }
}
