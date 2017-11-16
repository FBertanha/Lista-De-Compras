package com.lista_de_compras.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.lista_de_compras.model.CategoriaDeProduto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by evely on 27/09/2017.
 */

public class CategoriaDeProdutoDAO extends DAO {

    public static final int ERR_CATEGORIA_EM_USO = 1; //"A categoria não pode ser excluída pois há produtos vinculados à ela";
    public static final int ERR_CATEGORIA_PADRAO = 2;

    private Context context;

    public CategoriaDeProdutoDAO(Context context){
        super(context);
        this.context = context;
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

    public int excluir(CategoriaDeProduto categoriaDeProduto) {

        if (categoriaDeProduto.getCodigo().equals(1)) {
            return ERR_CATEGORIA_PADRAO;
        }

        if (verificarCategoriaEmUso(categoriaDeProduto)) {
            return ERR_CATEGORIA_EM_USO;
        }
        SQLiteDatabase db = getWritableDatabase();
        String[] whereArgs = new String[]{String.valueOf(categoriaDeProduto.getCodigo())};

        db.delete("categoriaDeProduto","codigo = ?", whereArgs);
        return 0;

    }

    private boolean verificarCategoriaEmUso(CategoriaDeProduto categoriaDeProduto) {
        boolean ret;
        String sql = "SELECT count(*) FROM produtos WHERE categoria = ?";
        SQLiteDatabase db = getWritableDatabase();

        String[] selectionArgs = new String[]{String.valueOf(categoriaDeProduto.getCodigo())};

        Cursor cursor = db.rawQuery(sql, selectionArgs);
        cursor.moveToNext();
        ret = cursor.getInt(0) > 0;
        cursor.close();
        return ret;


    }

    public List<CategoriaDeProduto> todos(){
        List<CategoriaDeProduto> categorias;
        String sql = "SELECT * FROM categoriaDeProduto order by codigo";
        SQLiteDatabase db = getWritableDatabase();

        Cursor cursor = db.rawQuery(sql, null);

        categorias = getCategoriaDeProdutosDoCursor(cursor);
        cursor.close();
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

    public CategoriaDeProduto pegarPorCodigo(int codigo) {
        String sql = "SELECT * FROM categoriaDeProduto WHERE codigo = ?";
        SQLiteDatabase db = getWritableDatabase();

        String[] selectionArgs = new String[]{String.valueOf(codigo)};

        Cursor cursor = db.rawQuery(sql, selectionArgs);
        if (cursor.moveToNext()) {
            CategoriaDeProduto categoriaDeProduto = new CategoriaDeProduto();
            categoriaDeProduto.setCodigo(cursor.getInt(cursor.getColumnIndex("codigo")));
            categoriaDeProduto.setNome(cursor.getString(cursor.getColumnIndex("nome")));
            return categoriaDeProduto;
        }
        cursor.close();
        return null;
    }
}
