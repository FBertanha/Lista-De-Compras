package com.lista_de_compras.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.lista_de_compras.model.CategoriaDeLista;
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


    public void adicionar(Lista lista) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues dadosLista = getDadosLista(lista);

        long codigoLista = db.insert("listas", null, dadosLista);

        lista.setCodigo(Integer.parseInt(String.valueOf(codigoLista)));
        new ListaProdutoDAO(context, lista).adicionar();

    }

    public void editar(Lista lista) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues dadosLista = getDadosLista(lista);
        String[] whereArgs = new String[]{String.valueOf(lista.getCodigo())};

        db.update("listas", dadosLista, "codigo = ?", whereArgs);
    }

    public void excluir(int codigo) {
        SQLiteDatabase db = getWritableDatabase();

        String[] whereArgs = new String[]{String.valueOf(codigo)};

        db.delete("listas", "id = ?", whereArgs);
    }

    public List<Lista> todos() {
        List<Lista> listas;

        String sql = "SELECT * FROM listas";

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
            //TODA chave estrangeira CategoriaDeLista
            // TODO Categoria De lista
            //lista.setCategoria(new CategoriaDeListaDAO(context).pegarPorCodigo(cursor.getInt(cursor.getColumnIndex("categoria"))));
            lista.setCategoria(new CategoriaDeLista(1, "Festa"));
            // TODO
            lista.setProdutos(new ListaProdutoDAO(context, lista).pegarProdutos());
            lista.setNome(cursor.getString(cursor.getColumnIndex("nome")));
            // TODO
            lista.setDataCriacao(new Date());
            lista.setDataCompra(new Date());
            //Adiciona produto no ArrayList
            listas.add(lista);
        }
        return listas;
    }

    private ContentValues getDadosLista(Lista lista) {
        ContentValues dados = new ContentValues();

        dados.put("codigo", lista.getCodigo());
        //TODO
        //dados.put("categoria", String.valueOf(lista.getCategoria().getCodigo()));
        dados.put("categoria", 1);
        //TODO
        //dados.put("produtos", String.valueOf(lista.getProdutos()));
        dados.put("nome", lista.getNome());
        //TODO
        //dados.put("dataCriacao", String.valueOf(lista.getDataCriacao()));
        //dados.put("dataCompra", String.valueOf(lista.getDataCompra()));


        return dados;

    }

}
