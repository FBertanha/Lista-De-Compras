package com.lista_de_compras.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by evely on 18/10/2017.
 */

public class DAO extends SQLiteOpenHelper {

    private final Context context;

    public DAO(Context context) {
        super(context, "lista_de_compras", null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql;
        //Produtos
        sql = "CREATE TABLE produtos (" +
                "codigo INTEGER PRIMARY KEY AUTOINCREMENT," +
                "descricao VARCHAR(255) NOT NULL," +
                "categoria INT NOT NULL," +
                "valor DOUBLE);";
        db.execSQL(sql);

        //Categoria de Produto
        sql = "CREATE TABLE categoriaDeProduto(" +
                "codigo INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nome VARCHAR (255) NOT NULL)";
        db.execSQL(sql);


        //Listas
        sql = "CREATE TABLE listas (" +
                "codigo INTEGER PRIMARY KEY AUTOINCREMENT," +
                "categoria INT NOT NULL," +
                "produtos INT  NOT NULL," +
                "nome VARCHAR(255) NOT NULL," +
                "dataCriacao DATE ," +
                "dataCompra DATE );";
        db.execSQL(sql);

        //Categoria de Lista
        sql = "CREATE TABLE CategoriaDeLista(" +
                "codigo INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nome VARCHAR(255) NOT NULL)";

        db.execSQL(sql);

        //Lista-Produtos
        sql = "CREATE TABLE listaProdutos (" +
                "codigo INTEGER PRIMARY KEY AUTOINCREMENT," +
                "produto INT NOT NULL," +
                "categoria INT NOT NULL," +
                "selecionado INT DEFAULT 0);";
        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
