package com.lista_de_compras.model;

import java.io.Serializable;

/**
 * Created by evely on 30/08/2017.
 */

public class Produto implements Serializable {
    private Integer codigo;
    private String descricao;
    private CategoriaDeProduto categoria;
    private double valor;
    private Boolean selecionado;

    public Produto() {
    }

    public Produto(Integer codigo, String descricao, CategoriaDeProduto categoria, double valor, Boolean selecionado) {
        this.codigo = codigo;
        this.descricao = descricao;
        this.categoria = categoria;
        this.valor = valor;
        this.selecionado = selecionado;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public CategoriaDeProduto getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaDeProduto categoria) {
        this.categoria = categoria;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    @Override
    public String toString() {
        return descricao;
    }

    public Boolean isSelecionado() {
        return selecionado;
    }

    public void setSelecionado(Boolean selecionado) {
        this.selecionado = selecionado;
    }
}
