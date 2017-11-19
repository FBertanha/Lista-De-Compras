package com.lista_de_compras.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.lista_de_compras.model.Lista;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by EduardoV on 20/09/2017.
 */

public class ListaDAO extends DAO {

    private final Context context;

    public ListaDAO(Context context) {
        super(context);
        this.context = context;
    }


    public Long adicionar(Lista lista) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues dadosLista = getDadosLista(lista);

        Long codigoLista = db.insert("listas", null, dadosLista);

        lista.setCodigo(codigoLista.intValue());

        new ListaProdutoDAO(context, lista).adicionarProdutos();
        return codigoLista;

    }

    public void editar(Lista lista) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues dadosLista = getDadosLista(lista);
        String[] whereArgs = new String[]{String.valueOf(lista.getCodigo())};

        db.update("listas", dadosLista, "codigo = ?", whereArgs);

        new ListaProdutoDAO(context, lista).editarProdutos();
    }

    public void excluir(Lista lista) {
        SQLiteDatabase db = getWritableDatabase();

        String[] whereArgs = new String[]{String.valueOf(lista.getCodigo())};

        db.delete("listas", "codigo = ?", whereArgs);

        new ListaProdutoDAO(context, lista).excluirProdutos();
    }

    public List<Lista> todos() {
        List<Lista> listas;

        String sql = "SELECT * FROM listas order by codigo";

        SQLiteDatabase db = getWritableDatabase();

        Cursor cursor = db.rawQuery(sql, null);

        listas = getListasDoCursor(cursor);

        return listas;

    }

    private List<Lista> getListasDoCursor(Cursor cursor) {
        List<Lista> listas = new ArrayList<>();

        while (cursor.moveToNext()) {
            Lista lista = new Lista();

            lista.setCodigo(cursor.getInt(cursor.getColumnIndex("codigo")));
            lista.setNome(cursor.getString(cursor.getColumnIndex("nome")));
            lista.setCategoria(new CategoriaDeListaDAO(context).pegarPorCodigo(cursor.getInt(cursor.getColumnIndex("categoria"))));
            lista.setProdutos(new ListaProdutoDAO(context, lista).pegarProdutos());
            lista.setDataCriacao(new Date());
            lista.setDataCompra(new Date());

            listas.add(lista);
        }
        return listas;
    }

    private ContentValues getDadosLista(Lista lista) {
        ContentValues dados = new ContentValues();

        dados.put("codigo", lista.getCodigo());
        if (lista.getCategoria() != null)
            dados.put("categoria", lista.getCategoria().getCodigo());
        else
            dados.put("categoria", 1);
        dados.put("nome", lista.getNome());

        return dados;

    }

}
