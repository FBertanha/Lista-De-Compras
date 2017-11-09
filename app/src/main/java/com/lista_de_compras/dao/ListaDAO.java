package com.lista_de_compras.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.lista_de_compras.model.CategoriaDeLista;
import com.lista_de_compras.model.Lista;
import com.lista_de_compras.model.Produto;

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

        db.insert("listas", null, dadosLista);
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

        //TODO array temporário pra teste

        listas = new ArrayList<>();
        Lista lista = new Lista();
        lista.setCategoria(new CategoriaDeLista());
        lista.setCodigo(1);
        lista.setNome("Churrasco");

        List<Produto> produtos = new ArrayList<>();
        produtos.add(new Produto(1, "Batata", null, 10));
        produtos.add(new Produto(1, "Batata", null, 10));
        produtos.add(new Produto(1, "Batata", null, 10));
        produtos.add(new Produto(1, "Batata", null, 10));
        produtos.add(new Produto(1, "Batata", null, 10));

        lista.setProdutos(produtos);

        listas.add(lista);
        listas.add(lista);
        listas.add(lista);
        listas.add(lista);


//        String sql = "SELECT * FROM listas";
//
//        SQLiteDatabase db = getWritableDatabase();
//
//        Cursor cursor = db.rawQuery(sql, null);
//
//        listas = getListasDoCursor(cursor);


        return listas;
    }

    private List<Lista> getListasDoCursor(Cursor cursor) {
        List<Lista> listas = new ArrayList<>();

        while (cursor.moveToNext()) {
            Lista lista = new Lista();

            lista.setCodigo(cursor.getInt(cursor.getColumnIndex("codigo")));
            //TODA chave estrangeira CategoriaDeLista
            // TODO Categoria De lista
            lista.setCategoria(new CategoriaDeLista());
            // TODO
            lista.setProdutos((List<Produto>) new Produto());
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
        dados.put("categoria", String.valueOf(lista.getCategoria()));
        //TODO
        dados.put("produtos", String.valueOf(lista.getProdutos()));
        dados.put("nome", lista.getNome());
        dados.put("dataCriacao", String.valueOf(lista.getDataCriacao()));
        dados.put("dataCompra", String.valueOf(lista.getDataCompra()));


        return dados;

    }

}
