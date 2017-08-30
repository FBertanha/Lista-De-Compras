package com.lista_de_compras.model;


import java.time.LocalDate;
import java.util.Date;
import java.util.List;

/**
 * Created by evely on 30/08/2017.
 */

public class Lista {
    int codigo;
    CategoriaDeLista categoria;
    List<Produto> produtos;
    String nome;
    Date dataCriacao;
    Date dataCompra;
}
