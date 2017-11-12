package com.lista_de_compras.model;

import java.io.Serializable;

/**
 * Created by berta on 11/9/2017.
 */

public class ListaProduto implements Serializable {
    private Integer codigo;
    private Produto produto;
    private Lista lista;
    private Boolean selecionado;

    public ListaProduto(Integer codigo, Produto produto, Lista lista, Boolean selecionado) {

        this.codigo = codigo;
        this.produto = produto;
        this.lista = lista;
        this.selecionado = selecionado;
    }

    public ListaProduto() {

    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Lista getLista() {
        return lista;
    }

    public void setLista(Lista lista) {
        this.lista = lista;
    }

    public Boolean getSelecionado() {
        return selecionado;
    }

    public void setSelecionado(Boolean selecionado) {
        this.selecionado = selecionado;
    }


}
