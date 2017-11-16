package com.lista_de_compras.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.lista_de_compras.model.CategoriaDeLista;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DIogo on 20/09/2017.
 */

public class CategoriaDeListaDAO extends DAO {

    public static final int ERR_CATEGORIA_EM_USO = 1; //"A categoria não pode ser excluída pois há produtos vinculados à ela";
    public static final int ERR_CATEGORIA_PADRAO = 2;

    private Context context;

    public CategoriaDeListaDAO(Context context) {
        super(context);
        this.context = context;
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


    public int excluir(CategoriaDeLista categoriaDeLista) {

        if (categoriaDeLista.getCodigo().equals(1)) {
            return ERR_CATEGORIA_PADRAO;
        }

        if (verificarCategoriaEmUso(categoriaDeLista)) {
            return ERR_CATEGORIA_EM_USO;
        }
        SQLiteDatabase db = getWritableDatabase();

        String[] whereArgs = new String[]{String.valueOf(categoriaDeLista.getCodigo())};

        db.delete("CategoriaDeLista", "codigo = ?", whereArgs);
        return 0;
    }

    private boolean verificarCategoriaEmUso(CategoriaDeLista categoriaDeLista) {
        boolean ret;
        String sql = "SELECT count(*) FROM listas WHERE categoria = ?";
        SQLiteDatabase db = getWritableDatabase();

        String[] selectionArgs = new String[]{String.valueOf(categoriaDeLista.getCodigo())};

        Cursor cursor = db.rawQuery(sql, selectionArgs);
        cursor.moveToNext();
        ret = cursor.getInt(0) > 0;
        cursor.close();
        return ret;
    }

    public List<CategoriaDeLista> todos(){
        List<CategoriaDeLista> categorias;
        String sql = "SELECT * FROM CategoriaDeLista order by codigo";
        SQLiteDatabase db = getWritableDatabase();

        Cursor cursor = db.rawQuery(sql, null);

        categorias = getCategoriaDoCursor(cursor);
        cursor.close();
        return categorias;
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
